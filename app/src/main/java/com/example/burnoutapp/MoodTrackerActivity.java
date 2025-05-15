package com.example.burnoutapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoodTrackerActivity extends AppCompatActivity {

    private TextView dateDisplay;
    private EditText noteInput;
    private EditText activityInput; // NEW: user types activity here
    private int selectedMood = -1;

    private ImageView[] moodImages;

    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_tracker);

        // Views
        dateDisplay = findViewById(R.id.date_display);
        noteInput = findViewById(R.id.note_input);
        activityInput = findViewById(R.id.activity_input); // NEW
        Button saveButton = findViewById(R.id.btn_save);

        // Setup Mood Selection
        moodImages = new ImageView[]{
                findViewById(R.id.mood_1),
                findViewById(R.id.mood_2),
                findViewById(R.id.mood_3),
                findViewById(R.id.mood_4),
                findViewById(R.id.mood_5)
        };

        for (int i = 0; i < moodImages.length; i++) {
            int index = i;
            moodImages[i].setOnClickListener(v -> selectMood(index));
        }

        // Set current date
        setDateToToday();

        dateDisplay.setOnClickListener(v -> openDatePicker());

        saveButton.setOnClickListener(v -> saveEntry());
    }

    private void setDateToToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy", new Locale("th", "TH"));
        String date = sdf.format(new Date());
        int buddhistYear = calendar.get(Calendar.YEAR) + 543;
        date = date.replace(String.valueOf(calendar.get(Calendar.YEAR)), String.valueOf(buddhistYear));
        dateDisplay.setText(date);
    }

    private void openDatePicker() {
        DatePickerDialog picker = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy", new Locale("th", "TH"));
                    String formatted = sdf.format(calendar.getTime());
                    int buddhistYear = year + 543;
                    formatted = formatted.replace(String.valueOf(year), String.valueOf(buddhistYear));
                    dateDisplay.setText(formatted);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        picker.getDatePicker().setMaxDate(System.currentTimeMillis()); // prevent future
        picker.show();
    }

    private void selectMood(int index) {
        selectedMood = index;
        for (int i = 0; i < moodImages.length; i++) {
            moodImages[i].setAlpha(i == index ? 1.0f : 0.4f);
        }
    }

    private void saveEntry() {
        if (selectedMood == -1 || activityInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "กรุณาเลือกอารมณ์และระบุกิจกรรม", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = dateDisplay.getText().toString();
        String note = noteInput.getText().toString();
        String activity = activityInput.getText().toString();

        String PREF_NAME = "mood_tracker_data";
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String entryKey = "entry_" + date;
        String data = selectedMood + "|" + activity + "|" + note;

        editor.putString(entryKey, data);
        editor.apply();

        Toast.makeText(this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();

        // Reset UI
        noteInput.setText("");
        activityInput.setText("");
        for (ImageView img : moodImages) img.setAlpha(1.0f);
        selectedMood = -1;
        Intent intent = new Intent(MoodTrackerActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
