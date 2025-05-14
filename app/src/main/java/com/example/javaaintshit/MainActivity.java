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

public class MainActivity extends AppCompatActivity {
    private Button btn_topre_assment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.preassmentpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.btn_topre_assment = findViewById(R.id.btntoass);

        this.btn_topre_assment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //เปิดหน้า pre assesment
                Intent intent1 = new Intent(getApplicationContext(), pre_assessment1.class);
                startActivity(intent1);
                //เปิดหน้า pre assesment
            }
        });
    }
}