package com.example.burnoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TeamMemberRegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextBirthday, editTextEmail, editTextPassword, editTextTeamCode;
    private FirebaseAuth mAuth;

    private RadioGroup radioGroupGender, radioGroupSleepTime, radioGroupExercise;
    private RatingBar ratingBarStress;
    private Button buttonCreateAccount;
    private TextView textViewAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member_register);

        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.editTextName);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextTeamCode = findViewById(R.id.editTextTeamCode);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupSleepTime = findViewById(R.id.radioGroupSleepTime);
        radioGroupExercise = findViewById(R.id.radioGroupExercise);
        ratingBarStress = findViewById(R.id.ratingBarStress);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewAlreadyAccount = findViewById(R.id.textViewAlreadyAccount);
        setupLoginLink();
        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TeamMemberRegisterActivity.this, R.style.CustomDatePickerDialog,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                            editTextBirthday.setText(selectedDate);
                        },
                        year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // prevent future dates
                datePickerDialog.show();
            }
        });


        //Assign a Listener to "Create Account"
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pull data from View
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String birthday = editTextBirthday.getText().toString().trim();
                String teamCode = editTextTeamCode.getText().toString().trim();

                String gender = "", sleepTime = "", exerciseFrequency = "";
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedGenderId != -1) gender = ((RadioButton) findViewById(selectedGenderId)).getText().toString();

                int selectedSleepTimeId = radioGroupSleepTime.getCheckedRadioButtonId();
                if (selectedSleepTimeId != -1) sleepTime = ((RadioButton) findViewById(selectedSleepTimeId)).getText().toString();

                int selectedExerciseId = radioGroupExercise.getCheckedRadioButtonId();
                if (selectedExerciseId != -1) exerciseFrequency = ((RadioButton) findViewById(selectedExerciseId)).getText().toString();

                float stressLevel = ratingBarStress.getRating();

                // Input validation
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณากรอกอีเมลและรหัสผ่าน", Toast.LENGTH_SHORT).show(); return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show(); return;
                }
                if (TextUtils.isEmpty(birthday)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณาเลือกวันเกิด", Toast.LENGTH_SHORT).show(); return;
                }
                if (TextUtils.isEmpty(teamCode)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณากรอกรหัสทีม", Toast.LENGTH_SHORT).show(); return;
                }
                if (TextUtils.isEmpty(gender)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณาเลือกเพศ", Toast.LENGTH_SHORT).show(); return;
                }
                if (TextUtils.isEmpty(sleepTime)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณาเลือกช่วงเวลานอน", Toast.LENGTH_SHORT).show(); return;
                }
                if (TextUtils.isEmpty(exerciseFrequency)) {
                    Toast.makeText(TeamMemberRegisterActivity.this, "กรุณาเลือกระดับการออกกำลังกาย", Toast.LENGTH_SHORT).show(); return;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // ✅ Step 1: Validate that the team code exists
                String finalGender = gender;
                String finalSleepTime = sleepTime;
                String finalExerciseFrequency = exerciseFrequency;
                db.collection("teams").document(teamCode).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (!documentSnapshot.exists()) {
                                Toast.makeText(TeamMemberRegisterActivity.this, "ไม่พบรหัสทีมนี้", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String teamName = documentSnapshot.getString("teamName");
                            String managerId = documentSnapshot.getString("managerId");

                            // ✅ Step 2: Create user
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(TeamMemberRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                String uid = mAuth.getCurrentUser().getUid();

                                                Map<String, Object> userMap = new HashMap<>();
                                                userMap.put("name", name);
                                                userMap.put("birthday", birthday);
                                                userMap.put("gender", finalGender);
                                                userMap.put("sleepTime", finalSleepTime);
                                                userMap.put("exerciseFrequency", finalExerciseFrequency);
                                                userMap.put("stressLevel", stressLevel);
                                                userMap.put("role", "member");
                                                userMap.put("teamCode", teamCode);

                                                // ✅ Save user
                                                db.collection("users").document(uid)
                                                        .set(userMap)
                                                        .addOnSuccessListener(aVoid -> {
                                                            // ✅ Add user to team member list
                                                            db.collection("teams").document(teamCode)
                                                                    .update("members", com.google.firebase.firestore.FieldValue.arrayUnion(uid))
                                                                    .addOnSuccessListener(unused -> {
                                                                        // ✅ Optional: Display team name
                                                                        String joinedTeam = teamName != null ? teamName : teamCode;
                                                                        Toast.makeText(TeamMemberRegisterActivity.this, "ลงทะเบียนสำเร็จ! เข้าร่วมทีม: " + joinedTeam, Toast.LENGTH_LONG).show();

                                                                        Log.d("RegisterActivity", "Manager ID: " + managerId);

                                                                        Intent intent = new Intent(TeamMemberRegisterActivity.this, LoginActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    })
                                                                    .addOnFailureListener(e -> {
                                                                        Log.e("RegisterActivity", "Failed to add member to team", e);
                                                                        Toast.makeText(TeamMemberRegisterActivity.this, "บันทึกข้อมูลผู้ใช้สำเร็จ แต่เพิ่มเข้าทีมไม่สำเร็จ", Toast.LENGTH_LONG).show();
                                                                    });
                                                        });
                                            } else {
                                                Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                                                Toast.makeText(TeamMemberRegisterActivity.this, "การลงทะเบียนล้มเหลว: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        })
                        .addOnFailureListener(e -> {
                            Log.e("RegisterActivity", "Team lookup failed", e);
                            Toast.makeText(TeamMemberRegisterActivity.this, "เกิดข้อผิดพลาดในการตรวจสอบทีม", Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }
    private void setupLoginLink() {
        String text = "มีบัญชีอยู่แล้ว? เข้าสู่ระบบ";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(TeamMemberRegisterActivity.this, LoginActivity.class));
                finish();
            }
        };

        ss.setSpan(clickableSpan, text.indexOf("เข้าสู่ระบบ"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewAlreadyAccount.setText(ss);
        textViewAlreadyAccount.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAlreadyAccount.setHighlightColor(getResources().getColor(android.R.color.transparent));
    }
}