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

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeamManagerRegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextTeamName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private TextView textViewAlreadyAccount;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private int codeGenerationAttempts = 0;
    private final int MAX_ATTEMPTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "รหัสผ่านต้องมีอย่างน้อย 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonRegister.setEnabled(false);
        generateUniqueTeamCodeAndRegister(name, teamName, email, password);
    }

    private void generateUniqueTeamCodeAndRegister(String name, String teamName, String email, String password) {
        String newCode = generateTeamCode();
        Log.d("TeamRegistration", "Checking team code: " + newCode);

        db.collection("teams").document(newCode)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.exists()) {
                        codeGenerationAttempts = 0;
                        saveManagerToFirestore(name, teamName, email, password, newCode);
                    } else {
                        if (++codeGenerationAttempts < MAX_ATTEMPTS) {
                            generateUniqueTeamCodeAndRegister(name, teamName, email, password);
                        } else {
                            buttonRegister.setEnabled(true);
                            Toast.makeText(this, "ไม่สามารถสร้างรหัสทีมที่ไม่ซ้ำได้ กรุณาลองใหม่", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    buttonRegister.setEnabled(true);
                    Toast.makeText(this, "เกิดข้อผิดพลาดในการตรวจสอบรหัสทีม", Toast.LENGTH_SHORT).show();
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
        Log.d("TeamRegistration", "Creating Firebase user for: " + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    buttonRegister.setEnabled(true);
                    if (task.isSuccessful()) {
                        // Force reloading the current user to ensure UID is fresh
                        mAuth.getCurrentUser().reload().addOnSuccessListener(unused -> {
                            String uid = mAuth.getCurrentUser().getUid();

                            Map<String, Object> managerMap = new HashMap<>();
                            managerMap.put("name", name);
                            managerMap.put("teamName", teamName);
                            managerMap.put("email", email);
                            managerMap.put("role", "manager");
                            managerMap.put("teamCode", teamCode);

                            db.collection("users").document(uid)
                                    .set(managerMap)
                                    .addOnSuccessListener(unused1 -> {
                                        Map<String, Object> teamMap = new HashMap<>();
                                        teamMap.put("teamCode", teamCode);
                                        teamMap.put("teamName", teamName);
                                        teamMap.put("managerId", uid);
                                        teamMap.put("createdAt", com.google.firebase.Timestamp.now());
                                        teamMap.put("members", new ArrayList<String>());

                                        db.collection("teams").document(teamCode)
                                                .set(teamMap)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(this, "สร้างบัญชีแล้ว!", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(this, ManageActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(this, "ไม่สามารถสร้างทีมได้", Toast.LENGTH_SHORT).show();
                                                    Log.e("FirestoreError", "Team create failed: " + e.getMessage());
                                                });

                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "ไม่สามารถบันทึกข้อมูลผู้จัดการได้", Toast.LENGTH_SHORT).show();
                                    });
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, "ไม่สามารถโหลดข้อมูลผู้ใช้", Toast.LENGTH_SHORT).show();
                        });
                    }else {
                        Toast.makeText(this, "การยืนยันตัวตนล้มเหลว: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
