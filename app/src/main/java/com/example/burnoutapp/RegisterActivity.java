package com.example.burnoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextEmail, editTextPassword;
    private RadioGroup radioGroupGender, radioGroupSleepTime, radioGroupExercise;
    private RatingBar ratingBarStress;
    private Button buttonCreateAccount;
    private TextView textViewAlreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupSleepTime = findViewById(R.id.radioGroupSleepTime);
        radioGroupExercise = findViewById(R.id.radioGroupExercise);
        ratingBarStress = findViewById(R.id.ratingBarStress);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewAlreadyAccount = findViewById(R.id.textViewAlreadyAccount);

        //Assign a Listener to "Create Account"
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pull data from View
                String name = editTextName.getText().toString().trim();
                String age = editTextAge.getText().toString().trim();
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

                // Save data to Shared Preferences [maybe rewrite!!!!!]
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("age", age);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putString("gender", gender);
                editor.putString("sleepTime", sleepTime);
                editor.putString("exerciseFrequency", exerciseFrequency);
                editor.putFloat("stressLevel", stressLevel);
                editor.apply(); // Use apply() for asynchronous saving

                Toast.makeText(RegisterActivity.this, "บันทึกข้อมูลผู้ใช้เรียบร้อย", Toast.LENGTH_SHORT).show();

                // **TODO:** You might want to navigate to another screen after successful registration
                // Intent intent = new Intent(RegisterActivity.this, NextActivity.class);
                // startActivity(intent);
                // finish();
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