package com.example.burnoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextBirthday, editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    private RadioGroup radioGroupGender, radioGroupSleepTime, radioGroupExercise;
    private RatingBar ratingBarStress;
    private Button buttonCreateAccount;
    private TextView textViewAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.editTextName);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupSleepTime = findViewById(R.id.radioGroupSleepTime);
        radioGroupExercise = findViewById(R.id.radioGroupExercise);
        ratingBarStress = findViewById(R.id.ratingBarStress);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewAlreadyAccount = findViewById(R.id.textViewAlreadyAccount);

        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this, R.style.CustomDatePickerDialog,
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
                //Pull data from View
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                String gender = "";
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedGenderId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedGenderId);
                    gender = selectedRadioButton.getText().toString();
                }

                String sleepTime = "";
                int selectedSleepTimeId = radioGroupSleepTime.getCheckedRadioButtonId();
                if (selectedSleepTimeId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedSleepTimeId);
                    sleepTime = selectedRadioButton.getText().toString();
                }

                String exerciseFrequency = "";
                int selectedExerciseId = radioGroupExercise.getCheckedRadioButtonId();
                if (selectedExerciseId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedExerciseId);
                    exerciseFrequency = selectedRadioButton.getText().toString();
                }

                float stressLevel = ratingBarStress.getRating();

                // Check if email and password are not empty
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "กรุณากรอกอีเมลและรหัสผ่าน", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution if email or password is empty
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editTextBirthday.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "กรุณาเลือกวันเกิด", Toast.LENGTH_SHORT).show();
                    return;
                }

                String finalGender = gender;
                String finalSleepTime = sleepTime;
                String finalExerciseFrequency = exerciseFrequency;
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("RegisterActivity", "createUserWithEmail:success");
                                    // Save data to Shared Preferences [maybe rewrite!!!!!]
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", name);
                                    editor.putString("birthday", editTextBirthday.getText().toString().trim());
                                    editor.putString("gender", finalGender);
                                    editor.putString("sleepTime", finalSleepTime);
                                    editor.putString("exerciseFrequency", finalExerciseFrequency);
                                    editor.putFloat("stressLevel", stressLevel);
                                    editor.apply(); // Use apply() for asynchronous saving
                                    Toast.makeText(RegisterActivity.this, "บัญชีผู้ใช้ถูกสร้างเรียบร้อย", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "การสร้างบัญชีผู้ใช้ล้มเหลว.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //Set Listener to TextView "Already have account? Log in"
        textViewAlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}