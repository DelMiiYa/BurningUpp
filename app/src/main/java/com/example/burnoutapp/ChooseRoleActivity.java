package com.example.burnoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseRoleActivity extends AppCompatActivity {

    Button btnTeamManager, btnTeamMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        btnTeamManager = findViewById(R.id.btn_team_manager);
        btnTeamMember = findViewById(R.id.btn_team_member);

        btnTeamManager.setOnClickListener(v -> {
            // Intent to open Team Manager registration activity
            Intent intent = new Intent(ChooseRoleActivity.this, TeamManagerRegisterActivity.class);
            startActivity(intent);
        });

        btnTeamMember.setOnClickListener(v -> {
            // Intent to open Team Member registration activity
            Intent intent = new Intent(ChooseRoleActivity.this, TeamMemberRegisterActivity.class);
            startActivity(intent);
        });
    }
}
