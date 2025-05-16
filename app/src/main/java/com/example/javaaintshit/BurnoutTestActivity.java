package com.example.javaaintshit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BurnoutTestActivity extends AppCompatActivity {
    private Button btn_tobasictest,btnback1,btnback2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pre_assessment1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.preassmentpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.btn_tobasictest = findViewById(R.id.btnassment1);
        this.btnback1 = findViewById(R.id.btnbacktomenu);
        this.btnback2 = findViewById(R.id.btnbacktomenuicon);

        this.btn_tobasictest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //เปิดหน้า assesment1
                Intent intent2 = new Intent(getApplicationContext(), BasicTestActivity.class);
                startActivity(intent2);
            }
        });
        this.btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //เปิดหน้า menu
                finish();
            }
        });
        this.btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //เปิดหน้า menu
                finish();
            }
        });

    }
}