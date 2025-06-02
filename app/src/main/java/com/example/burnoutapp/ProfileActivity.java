package com.example.burnoutapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.*;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameField, birthdayField, genderField;
    private PieChart pieChart;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private static final String[] MOOD_LABELS = {"แย่มาก", "แย่", "ปานกลาง", "ดี", "ดีมาก"};
    private static final int[] MOOD_COLORS = {Color.RED, Color.rgb(255, 128, 0), Color.YELLOW, Color.CYAN, Color.GREEN};

    private static final String USERS_COLLECTION = "users";
    private static final String MOODS_COLLECTION = "moods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeViews();
        initializeFirebase();

        fetchUserProfile();
        fetchMoodData();
//        fetchAndDisplayBurnoutLevel();
    }

    private void initializeViews() {
        nameField = findViewById(R.id.name_field);
        birthdayField = findViewById(R.id.birthdate_field);
        genderField = findViewById(R.id.gender_field);
        pieChart = findViewById(R.id.pie_chart);
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void fetchUserProfile() {
        String uid = getCurrentUserId();
        if (uid == null) return;

        db.collection(USERS_COLLECTION).document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        nameField.setText("ชื่อ: " + doc.getString("name"));
                        birthdayField.setText("วันเกิด: " + doc.getString("birthday"));
                        genderField.setText("เพศ: " + doc.getString("gender"));
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }

    private void fetchMoodData() {
        String uid = getCurrentUserId();
        if (uid == null) return;

        db.collection(MOODS_COLLECTION)
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    int[] moodCounts = new int[MOOD_LABELS.length];

                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        Long mood = doc.getLong("mood");
                        if (mood != null && mood >= 0 && mood < MOOD_LABELS.length) {
                            moodCounts[mood.intValue()]++;
                        }
                    }

                    updatePieChart(moodCounts);
                })
                .addOnFailureListener(Throwable::printStackTrace);
    }

    private void updatePieChart(int[] moodCounts) {
        List<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < moodCounts.length; i++) {
            if (moodCounts[i] > 0) {
                entries.add(new PieEntry(moodCounts[i], MOOD_LABELS[i]));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(MOOD_COLORS);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.invalidate();
    }

//    private void fetchAndDisplayBurnoutLevel() {
//        String uid = getCurrentUserId();
//        if (uid == null) return;
//
//        db.collection(USERS_COLLECTION).document(uid)
//                .get()
//                .addOnSuccessListener(doc -> {
//                    if (doc.exists()) {
//                        Long burnoutLevel = doc.getLong("burnoutLevel");
//                        updateFireIcons(burnoutLevel != null ? burnoutLevel.intValue() : 0);
//                    }
//                })
//                .addOnFailureListener(Throwable::printStackTrace);
//    }

//    private void updateFireIcons(int level) {
//        int[] fireIds = {R.id.fire1, R.id.fire2, R.id.fire3};
//
//        for (int i = 0; i < fireIds.length; i++) {
//            ImageView fire = findViewById(fireIds[i]);
//            fire.setImageResource(level > i ? R.drawable.ic_fire_on : R.drawable.ic_fire_off);
//        }
//    }

    private String getCurrentUserId() {
        if (mAuth.getCurrentUser() == null) {
            return null;
        }
        return mAuth.getCurrentUser().getUid();
    }
}
