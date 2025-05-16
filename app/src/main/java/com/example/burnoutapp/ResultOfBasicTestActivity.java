package com.example.burnoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultOfBasicTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_r1_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtResult = findViewById(R.id.txt_result); //ข้อความแสดงผล
        Button btnNext = findViewById(R.id.btn_next);   //ปุ่มไปหน้า ถัดไป

        int yesCount = getIntent().getIntExtra("yesCount", 0);
//กำหนดค่าสำหรับข้อความแสดงผล
        if (yesCount >= 5) {
            txtResult.setText("สูง");
            btnNext.setText("ทำแบบทดสอบที่ 2");
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //เปิดหน้า advance assessment
                    Intent intent2 = new Intent(getApplicationContext(), AdvanceTestActivity.class);
                    startActivity(intent2);
                }
            });
        } else {
            txtResult.setText("ต่ำ");
            btnNext.setText("ถัดไป");
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Save burnoutLevel = 1 to Firestore
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users")
                                .document(user.getUid())
                                .update("burnoutLevel", 1)
                                .addOnSuccessListener(aVoid -> {
                                    // open tips screen
                                    Intent intent2 = new Intent(getApplicationContext(), TipsActivity.class);
                                    startActivity(intent2);
                                });
                    }
                }
            });
        }
    }
}