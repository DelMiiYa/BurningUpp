package com.example.burnoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton profileButton = findViewById(R.id.profile_button);
        Button burnoutTestButton = findViewById(R.id.burnout_test_button);
        Button moodTrackerAddButton = findViewById(R.id.mood_tracker_add_button);
        Button moodCalendarButton = findViewById(R.id.mood_calendar_button);
//        Button viewAdvancedResultsButton = findViewById(R.id.view_advanced_results_button);

//        viewAdvancedResultsButton.setEnabled(false);
//        viewAdvancedResultsButton.setAlpha(0.5f);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

//        if (auth.getCurrentUser() != null) {
//            String uid = auth.getCurrentUser().getUid();
//            db.collection("users").document(uid).get()
//                    .addOnSuccessListener(document -> {
//                        if (document.exists() && document.contains("advancedTest")) {
//                            // Enable button if advanced test exists
//                            viewAdvancedResultsButton.setEnabled(true);
//                            viewAdvancedResultsButton.setAlpha(1.0f);
//
//                            viewAdvancedResultsButton.setOnClickListener(v -> {
//                                Intent intent = new Intent(MenuActivity.this, AdditionalInfoActivity.class);
//                                startActivity(intent);
//                            });
//                        }
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(this, "โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
//                    });
//        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open ProfileActivity
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        burnoutTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open BurnoutTestActivity
                Intent intent = new Intent(MenuActivity.this, BurnoutTestActivity.class);
                startActivity(intent);
            }
        });

        moodTrackerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open MoodTrackerActivity
                Intent intent = new Intent(MenuActivity.this, MoodTrackerActivity.class);
                startActivity(intent);
            }
        });

        moodCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add Intent for open MoodCalendarActivity
                Intent intent = new Intent(MenuActivity.this, MoodCalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
