package com.example.javaaintshit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    private ImageButton profileButton;
    private Button burnoutTestButton;
    private Button moodTrackerAddButton;
    private Button moodCalendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        profileButton = findViewById(R.id.profile_button);
        burnoutTestButton = findViewById(R.id.burnout_test_button);
        moodTrackerAddButton = findViewById(R.id.mood_tracker_add_button);
        moodCalendarButton = findViewById(R.id.mood_calendar_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open ProfileActivity
                // Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                // startActivity(intent);
            }
        });

        burnoutTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open BurnoutTestActivity
                //Intent intent = new Intent(MenuActivity.this, BurnoutTestActivity.class);
                //startActivity(intent);
            }
        });

        moodTrackerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open MoodTrackerActivity
                //Intent intent = new Intent(MenuActivity.this, MoodTrackerActivity.class);
                //startActivity(intent);
            }
        });

        moodCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open MoodCalendarActivity
                //Intent intent = new Intent(MenuActivity.this, MoodCalendarActivity.class);
                //startActivity(intent);
            }
        });
    }
}
