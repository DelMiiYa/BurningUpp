package com.example.javaaintshit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultOfAdvanceTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_of_advance_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtResult = findViewById(R.id.txt_result_2);
        Button btnNext = findViewById(R.id.btn_next_2);

        int emoExhaust = getIntent().getIntExtra("emoExhaust", 0);
        int dePerson = getIntent().getIntExtra("dePerson", 0);
        int personalAch = getIntent().getIntExtra("personalAch", 0);

        String result;

        boolean highExhaust = emoExhaust >= 27;
        boolean highDePerson = dePerson >= 13;
        boolean lowPersonalAch = personalAch <= 31;

        if (highExhaust || highDePerson || lowPersonalAch) {
            result = "คุณยังมีความเสี่ยงสูง";
            btnNext.setOnClickListener(v -> {
                // ไปหน้าอื่น (ที่คุณจะพัฒนาเอง)
            });
        } else {
            result = "คุณมีความเสี่ยงค่อนข้างต่ำ";
            btnNext.setOnClickListener(v -> {
                // ไปหน้าแสดงคำแนะนำ
            });
        }

        String detail = "\nคะแนนความอ่อนล้าทางอารมณ์: " + emoExhaust +
                "\nคะแนนการลดความเป็นบุคคล: " + dePerson +
                "\nคะแนนความสำเร็จส่วนบุคคล: " + personalAch;

        txtResult.setText(result + detail);
    }
}