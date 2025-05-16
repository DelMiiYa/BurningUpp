package com.example.burnoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MoodCalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private Map<LocalDate, Integer> moodMap = new HashMap<>();

    private CalendarAdapter calendarAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_calendar);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
        fetchMoodDataFromFirestore();
    }

    private void fetchMoodDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // or get from FirebaseAuth

        db.collection("moods")
                .whereEqualTo("userId", currentUserId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    moodMap.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String dateStr = doc.getString("date"); // format: "yyyy-MM-dd"
                        int mood = doc.getLong("mood").intValue();
                        LocalDate date = LocalDate.parse(dateStr);
                        moodMap.put(date, mood);
                    }

                    if (calendarAdapter != null) {
                        calendarAdapter.setMoodMap(moodMap);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }


    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        calendarAdapter = new CalendarAdapter(daysInMonth, this, selectedDate, moodMap);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (dayText.isEmpty()) return;

        int day = Integer.parseInt(dayText);
        LocalDate clickedDate = LocalDate.of(
                selectedDate.getYear(),
                selectedDate.getMonth(),
                day
        );

        String dateStr = clickedDate.toString(); // yyyy-MM-dd
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String docId = userId + "_" + dateStr;

        FirebaseFirestore.getInstance()
                .collection("moods")
                .document(docId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        int mood = documentSnapshot.getLong("mood").intValue();
                        String activity = documentSnapshot.getString("activity");
                        String note = documentSnapshot.getString("note");

                        String message = "วันที่: " + dateStr +
                                "\nกิจกรรม: " + activity +
                                "\nบันทึก: " + note;

                        showMoodPopup(clickedDate, mood, activity, note);

                    } else {
                        showMoodPopup("ไม่มีข้อมูล", "ไม่มีบันทึกสำหรับวันที่เลือกไว้");
                    }
                })
                .addOnFailureListener(e -> {
                    showMoodPopup("ข้อผิดพลาด", "ไม่สามารถดึงข้อมูลได้");
                });
    }
    private void showMoodPopup(LocalDate date, int mood, String activity, String note) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_mood_entry, null);

        TextView dateText = dialogView.findViewById(R.id.dateText);
        ImageView moodIcon = dialogView.findViewById(R.id.moodIcon);
        TextView activityText = dialogView.findViewById(R.id.activityText);
        TextView noteText = dialogView.findViewById(R.id.noteText);

        // Set content
        dateText.setText("วันที่: " + date.toString());
        moodIcon.setImageResource(getMoodResId(mood)); // same method from CalendarAdapter
        activityText.setText("กิจกรรม: " + activity);
        noteText.setText("บันทึก: " + note);

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("ปิด", null)
                .show();
    }
    private void showMoodPopup(String title, String message) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ตกลง", null)
                .show();
    }

    private int getMoodResId(int mood) {
        switch (mood) {
            case 0:
                return R.drawable.ic_mood1; // e.g., very sad
            case 1:
                return R.drawable.ic_mood2; // sad
            case 2:
                return R.drawable.ic_mood3; // neutral
            case 3:
                return R.drawable.ic_mood4; // happy
            case 4:
                return R.drawable.ic_mood5; // very happy
            default:
                return 0; // fallback (optional)
        }
    }


}







