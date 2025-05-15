package com.example.burnoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
//            //======================= test mang ===================//
//            public void onClick(View v) {
//                // add Intent for open tipsActivity
//                Intent intent = new Intent(MainActivity.this, TipsActivity.class);
//                startActivity(intent);
//            }
//            //====================================================//
        });
    }
}