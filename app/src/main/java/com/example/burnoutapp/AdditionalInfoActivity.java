package com.example.burnoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdditionalInfoActivity extends AppCompatActivity {

    private EditText originOfProblemEditText;
    private EditText mainProblemEditText;
    private EditText environmentEditText;
    private Button submitButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        originOfProblemEditText = findViewById(R.id.originOfProblemEditText);
        mainProblemEditText = findViewById(R.id.mainProblemEditText);
        environmentEditText = findViewById(R.id.environmentEditText);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(originOfProblemEditText.getText())) {
                    originOfProblemEditText.setError("โปรดระบุจุดกำเนิดปัญหา");
                    return;
                }

                if (TextUtils.isEmpty(mainProblemEditText.getText())) {
                    mainProblemEditText.setError("โปรดระบุปัญหาหลัก");
                    return;
                }

                if (TextUtils.isEmpty(environmentEditText.getText())) {
                    environmentEditText.setError("โปรดระบุสิ่งแวดล้อม");
                    return;
                }

                // If all fields are filled, you can proceed with your data submission logic here
                String origin = originOfProblemEditText.getText().toString();
                String mainProblem = mainProblemEditText.getText().toString();
                String environment = environmentEditText.getText().toString();

                Toast.makeText(AdditionalInfoActivity.this, "ข้อมูลถูกส่งแล้ว!", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement your back button functionality here
                Toast.makeText(AdditionalInfoActivity.this, "ย้อนกลับ", Toast.LENGTH_SHORT).show(); // Go back
            }
        });
    }
}