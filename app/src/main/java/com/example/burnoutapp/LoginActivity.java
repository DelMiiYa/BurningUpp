package com.example.burnoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button signInButton;
    // private Button signUpButton; // This was declared but not initialized or used
    private TextView textViewAlreadyAccount;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // Add Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        signInButton = findViewById(R.id.buttonSignIn);
        textViewAlreadyAccount = findViewById(R.id.textViewAlreadyAccount);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance(); // Initialize Firestore
        setupLoginLink();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "กรุณากรอกอีเมลและรหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("LoginActivity", "signInWithEmail:success");
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        if (firebaseUser != null) {
                                            checkUserRole(firebaseUser.getUid());
                                        } else {
                                            // Should not happen if sign in was successful
                                            Toast.makeText(LoginActivity.this, "User not found after login.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void checkUserRole(String uid) {
        DocumentReference userRef = db.collection("users").document(uid);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        String role = document.getString("role"); // Assuming "role" is the field name
                        Log.d("LoginActivity", "User role: " + role);

                        // Now you can navigate based on the role
                        if ("manager".equals(role)) {
                            // Navigate to Manager Activity
                            Intent intent = new Intent(LoginActivity.this, ManageActivity.class); // Or ManagerMenuActivity
                            intent.putExtra("USER_ROLE", role); // Optionally pass the role
                            startActivity(intent);
                            finish();
                        } else if ("member".equals(role)) {
                            // Navigate to Member Activity
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class); // Or MemberMenuActivity
                            intent.putExtra("USER_ROLE", role); // Optionally pass the role
                            startActivity(intent);
                            finish();
                        } else {
                            // Handle unknown role or role not set
                            Toast.makeText(LoginActivity.this, "User role not defined.", Toast.LENGTH_SHORT).show();
                            // Optionally, navigate to a default activity or show an error
                            // For now, let's go to MenuActivity as a fallback
                            Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Log.d("LoginActivity", "No such document for user role");
                        Toast.makeText(LoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                        // Handle case where user document doesn't exist in Firestore
                        // (e.g., navigate to a profile setup screen or a default activity)
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class); // Fallback
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.d("LoginActivity", "get failed with ", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed to retrieve user role.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setupLoginLink() {
        String text = "ยังไม่มีบัญชี? ลงชื่อเข้าใช้";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Consider if you want to finish LoginActivity here or not.
                // If the user presses back from ChooseRoleActivity, they might expect to return to Login.
                startActivity(new Intent(LoginActivity.this, ChooseRoleActivity.class));
            }
        };

        ss.setSpan(clickableSpan, text.indexOf("ลงชื่อเข้าใช้"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewAlreadyAccount.setText(ss);
        textViewAlreadyAccount.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAlreadyAccount.setHighlightColor(getResources().getColor(android.R.color.transparent)); // Requires API level check or ContextCompat
    }
}