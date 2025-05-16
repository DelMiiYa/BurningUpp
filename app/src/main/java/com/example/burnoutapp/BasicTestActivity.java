package com.example.burnoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    String[] questions = { //สร้างและกำหนดคำถาม
            "ข้อ 1: ฉันเริ่มรู้สึกว่าหน้าที่การงานที่เคยทำด้วยความกระตือรือร้น กลายเป็นเรื่องน่าเบื่อหน่าย",
            "ข้อ 2: ฉันรู้สึกว่าตัวเองไม่มีคุณค่า หรือไม่มีความหมายกับงานที่ทำ",
            "ข้อ 3: ฉันรู้สึกเหนื่อยล้า แม้ว่าจะได้พักผ่อนเพียงพอแล้วก็ตาม",
            "ข้อ 4: ฉันรู้สึกเครียดหรือวิตกกังวลเกี่ยวกับงานตลอดเวลา แม้ในช่วงเวลาที่ควรพักผ่อน",
            "ข้อ 5: ฉันมีปัญหาในการจดจ่อหรือทำงานที่ต้องใช้สมาธิอย่างต่อเนื่อง",
            "ข้อ 6: ฉันเริ่มหลีกเลี่ยงหรือลดการติดต่อกับเพื่อนร่วมงานหรือลูกค้า",
            "ข้อ 7: ฉันรู้สึกหมดกำลังใจเมื่อคิดถึงการทำงานในแต่ละวัน",
            "ข้อ 8: ฉันรู้สึกว่าไม่มีพลังหรือแรงบันดาลใจที่จะพัฒนาตนเองในหน้าที่การงาน",
            "ข้อ 9: ฉันเริ่มมีอาการทางกาย เช่น ปวดหัว ปวดท้อง หรือปวดเมื่อยเรื้อรังโดยไม่ทราบสาเหตุ",
            "ข้อ 10: ฉันเคยคิดจะลาออกหรือเปลี่ยนงานเพราะรู้สึกว่าตนเองไม่สามารถทนต่อสภาพแวดล้อมการทำงานได้อีกต่อไป"
    };

    Integer[] answers = new Integer[questions.length]; //ที่เก็บคำตอบของแต่ะข้อ

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

        LinearLayout questionList = findViewById(R.id.question_list); //ช่องสำหรับสร้างข้อความคำถามและตัวเลือกแต่ละข้อ
        Button btnSubmit = findViewById(R.id.btn_submit);//ปุ่มกดส่งแบบสอบถาม
        Button btnBackIcon = findViewById(R.id.btnbacktomenuicon);

        btnBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //กลับไปเปิดหน้า เลือกแบบสอบถาม
                finish();
            }
        });

        for (int i = 0; i < questions.length; i++) { //สร้างช่องคำถามและ คำตอบ(Loop ย่อย)แต่ละข้อใน Layout
            int index = i;
            TextView questionText = new TextView(this);
            questionText.setText(questions[i]);
            questionList.addView(questionText);

            RadioGroup radioGroup = new RadioGroup(this); //สร้างกลุ่มตัวเลือก Radio สำหรับคำตอบ
            RadioButton yes = new RadioButton(this); //สร้างตัวเลือกคำตอบ ใช่
            yes.setText("ใช่");
            RadioButton no = new RadioButton(this); //สร้างตัวเลือกคำตอบ ไม่ใช่
            no.setText("ไม่ใช่");
            radioGroup.addView(yes);
            radioGroup.addView(no);
            questionList.addView(radioGroup);

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> { //กำหนดค่าของตัวเลือก และสร้างการตรวจสอบการตอบคำถาม เพื่อให้ทำครบทุกข้อ
                if (checkedId == yes.getId()) {
                    answers[index] = 1;
                } else {
                    answers[index] = 0;
                }
                if (checkAllAnswered()) btnSubmit.setVisibility(View.VISIBLE);
            });
        }

        btnSubmit.setOnClickListener(v -> {//เมื่อกดส่งแบบทดสอบ นับจำนวนข้อที่ตอบ ใช่ และส่งข้อมูลพร้อมเปิดหน้าผลลัพธ์
            int yesCount = 0;
            for (Integer answer : answers) if (answer==1) yesCount++;
            Intent intent = new Intent(getApplicationContext(), ResultOfBasicTestActivity.class);
            intent.putExtra("yesCount", yesCount);
            startActivity(intent);
            finish();
        });
    }

    private boolean checkAllAnswered() { //เช็คว่าตอบทุกข้อหรือไม่มีผลกับการแสดงปุ่มส่งแบบสอบถาม
        for (Integer answer : answers) if (answer == null) return false;
        return true;
    }
}