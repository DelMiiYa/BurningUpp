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

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TeamManagerRegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextTeamName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private TextView textViewAlreadyAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager_register);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.etName);
        editTextTeamName = findViewById(R.id.etTeamName);
        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etPassword);
        buttonRegister = findViewById(R.id.btnRegister);
        textViewAlreadyAccount = findViewById(R.id.textViewAlreadyAccount);

        setupLoginLink();

        buttonRegister.setOnClickListener(view -> registerManager());
    }

    private void setupLoginLink() {
        String text = "มีบัญชีอยู่แล้ว? เข้าสู่ระบบ";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(TeamManagerRegisterActivity.this, LoginActivity.class));
                finish();
            }
        };

        ss.setSpan(clickableSpan, text.indexOf("เข้าสู่ระบบ"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewAlreadyAccount.setText(ss);
        textViewAlreadyAccount.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAlreadyAccount.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }

    private void registerManager() {
        String name = editTextName.getText().toString().trim();
        String teamName = editTextTeamName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(teamName) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Start the process of generating and validating a unique team code
        generateUniqueTeamCodeAndRegister(name, teamName, email, password);
    }

    private void generateUniqueTeamCodeAndRegister(String name, String teamName, String email, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Recursive function to check uniqueness
        String newCode = generateTeamCode();
        db.collection("users")
                .whereEqualTo("teamCode", newCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // Code is unique, proceed with registration
                        saveManagerToFirestore(name, teamName, email, password, newCode);
                    } else {
                        // Code exists, try again
                        generateUniqueTeamCodeAndRegister(name, teamName, email, password);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error checking team code", Toast.LENGTH_SHORT).show();
                });
    }
    private String generateTeamCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
    private void saveManagerToFirestore(String name, String teamName, String email, String password, String teamCode) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        Map<String, Object> managerMap = new HashMap<>();
                        managerMap.put("name", name);
                        managerMap.put("teamName", teamName);
                        managerMap.put("email", email);
                        managerMap.put("role", "manager");
                        managerMap.put("teamCode", teamCode);

                        db.collection("users").document(uid)
                                .set(managerMap)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this,
                                            "Account created!\nTeam Code: " + teamCode,
                                            Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(this, ManageActivity.class);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Failed to save manager data", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
