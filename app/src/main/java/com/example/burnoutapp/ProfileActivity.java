package com.example.burnoutapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.*;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameField, birthdayField, genderField;
    private PieChart pieChart;
    private BarChart barChart;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private final String[] moodLabels = {"แย่มาก", "แย่", "ปานกลาง", "ดี", "ดีมาก"};
    private final int[] moodColors = {Color.RED, Color.YELLOW, Color.GRAY, Color.CYAN, Color.GREEN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameField = findViewById(R.id.name_field);
        birthdayField = findViewById(R.id.birthdate_field);
        genderField = findViewById(R.id.gender_field);
        pieChart = findViewById(R.id.pie_chart);
//        barChart = findViewById(R.id.bar_chart);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fetchUserProfile();
        fetchMoodData();
    }

    private void fetchUserProfile() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("users").document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        nameField.setText("ชื่อ: " + doc.getString("name"));
                        birthdayField.setText("วันเกิด: " + doc.getString("birthday"));
                        genderField.setText("เพศ: " + doc.getString("gender"));
                    }
                });
    }

    private void fetchMoodData() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("moods")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    int[] moodCounts = new int[5]; // For moods 0-4

                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        Long mood = doc.getLong("mood");
                        if (mood != null && mood >= 0 && mood <= 4) {
                            moodCounts[mood.intValue()]++;
                        }
                    }

                    updatePieChart(moodCounts);
                });
    }

    private void updatePieChart(int[] moodCounts) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < moodCounts.length; i++) {
            if (moodCounts[i] > 0) {
                entries.add(new PieEntry(moodCounts[i], moodLabels[i]));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(moodColors);
        dataSet.setValueTextSize(14f);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.invalidate();
    }
}
