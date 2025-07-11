package com.example.burnoutapp;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.FILL_HORIZONTAL;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BasicTestActivity extends AppCompatActivity {

    String[] questions = {
            "ข้อ 1 : ฉันเริ่มรู้สึกว่าหน้าที่การงานที่เคยทำด้วยความกระตือรือร้น กลายเป็นเรื่องน่าเบื่อหน่าย",
            "ข้อ 2 : ฉันรู้สึกว่าตัวเองไม่มีคุณค่า หรือไม่มีความหมายกับงานที่ทำ",
            "ข้อ 3 : ฉันรู้สึกเหนื่อยล้า แม้ว่าจะได้พักผ่อนเพียงพอแล้วก็ตาม",
            "ข้อ 4 : ฉันรู้สึกเครียดหรือวิตกกังวลเกี่ยวกับงานตลอดเวลา แม้ในช่วงเวลาที่ควรพักผ่อน",
            "ข้อ 5 : ฉันมีปัญหาในการจดจ่อหรือทำงานที่ต้องใช้สมาธิอย่างต่อเนื่อง",
            "ข้อ 6 : ฉันเริ่มหลีกเลี่ยงหรือลดการติดต่อกับเพื่อนร่วมงานหรือลูกค้า",
            "ข้อ 7 : ฉันรู้สึกหมดกำลังใจเมื่อคิดถึงการทำงานในแต่ละวัน",
            "ข้อ 8 : ฉันรู้สึกว่าไม่มีพลังหรือแรงบันดาลใจที่จะพัฒนาตนเองในหน้าที่การงาน",
            "ข้อ 9 : ฉันเริ่มมีอาการทางกาย เช่น ปวดหัว ปวดท้อง หรือปวดเมื่อยเรื้อรังโดยไม่ทราบสาเหตุ",
            "ข้อ 10 : ฉันเคยคิดจะลาออกหรือเปลี่ยนงานเพราะรู้สึกว่าตนเองไม่สามารถทนต่อสภาพแวดล้อมการทำงานได้อีกต่อไป"
    };

    Integer[] answers = new Integer[questions.length]; // ค่าเริ่มต้นจะเป็น null

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_new_basic_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout questionList = findViewById(R.id.question_list);
        Button btnSubmit = findViewById(R.id.btn_submit);
        Button btnBackIcon = findViewById(R.id.btnbacktomenuicon);

        btnBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //กลับไปเปิดหน้า เลือกแบบสอบถาม
                finish();
//                Intent intent2 = new Intent(getApplicationContext(), BurnoutTestActivity.class);
//                startActivity(intent2);
            }
        });

        for (int i = 0; i < questions.length; i++) {
            int index = i;
            TextView questionText = new TextView(this);
            questionText.setTextSize(24);
            questionText.setTypeface(null, Typeface.BOLD);
            questionText.setTextColor(Color.BLACK);
            questionText.setText(questions[i]);
            questionText.setPadding(0, 35, 0, 0);
            questionList.addView(questionText);

            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
            radioGroup.setPadding(20, 5, 0, 20);
            RadioButton yes = new RadioButton(this);
            yes.setText("ใช่");
            yes.setTextSize(20);
            yes.setTextColor(Color.BLACK);
            yes.setPadding(0,0,30,0);

            RadioButton no = new RadioButton(this);
            no.setText("ไม่ใช่");
            no.setTextSize(20);
            no.setTextColor(Color.BLACK);
            radioGroup.addView(yes);
            radioGroup.addView(no);
            questionList.addView(radioGroup);
            questionList.setPadding(0, 0, 0, 20);

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId == yes.getId()) {
                    answers[index] = 1;
                } else {
                    answers[index] = 0;
                }
                if (checkAllAnswered()) btnSubmit.setVisibility(View.VISIBLE);
            });
        }

        btnSubmit.setOnClickListener(v -> {
            int yesCount = 0;
            for (Integer answer : answers) if (answer==1) yesCount++;
            Intent intent = new Intent(getApplicationContext(), ResultOfBasicTestActivity.class);
            intent.putExtra("yesCount", yesCount);
            startActivity(intent);
            finish();
        });
    }

    private boolean checkAllAnswered() {
        for (Integer answer : answers) if (answer == null) return false;
        return true;
    }
}