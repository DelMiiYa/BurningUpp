package com.example.burnoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class AdditionalInfoActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    Button buttonBackToMenu;
    TextView userInfoText, basicTestText, advanceTestText, burnoutLevelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userInfoText = findViewById(R.id.text_user_info);
        advanceTestText = findViewById(R.id.text_advance_test_result);
        burnoutLevelText = findViewById(R.id.text_burnout_level);
        buttonBackToMenu = findViewById(R.id.backMenuButton);

        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdditionalInfoActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        loadUserReport();
    }

    private void loadUserReport() {
        String uid = auth.getCurrentUser().getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        String name = document.getString("name");
                        String birthdate = document.getString("birthdate");
                        String gender = document.getString("gender");
                        Long burnoutLevel = document.getLong("burnoutLevel");

                        Map<String, Object> basicTest = (Map<String, Object>) document.get("basicTest");
                        Map<String, Object> advancedTest = (Map<String, Object>) document.get("advancedTest");

                        // Set user info
                        String userInfo = "ชื่อ: " + name + "\nวันเกิด: " + birthdate + "\nเพศ: " + gender;
                        userInfoText.setText(userInfo);

                        if (basicTest != null) {
                            String basicResult = "ผลแบบทดสอบเบื้องต้น: " + basicTest.get("result") +
                                    " (" + basicTest.get("score") + " คะแนน)";
                            basicTestText.setText(basicResult);
                        }

                        if (advancedTest != null) {
                            String advanceResult = "อารมณ์เหนื่อยล้า: " + advancedTest.get("emoExhaust") +
                                    "\nลดความเป็นบุคคล: " + advancedTest.get("dePerson") +
                                    "\nความสำเร็จส่วนตัว: " + advancedTest.get("personalAch") +
                                    "\nรวม: " + advancedTest.get("result");
                            advanceTestText.setText(advanceResult);
                        }

                        burnoutLevelText.setText("ระดับ Burnout (1-3): " + burnoutLevel);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "เกิดข้อผิดพลาดในการโหลดข้อมูล", Toast.LENGTH_SHORT).show();
                });
    }

}
