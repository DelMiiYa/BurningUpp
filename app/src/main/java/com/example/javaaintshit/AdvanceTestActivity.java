package com.example.javaaintshit;

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

public class AdvanceTestActivity extends AppCompatActivity {
    String[] questions = new String[22]; // ข้อความย่อที่ใช้แทนตัวอย่าง

    Integer[] answers = new Integer[22];

    // ข้อทางลบ
    int[] negativeIndexes = {0, 1, 2, 5, 7, 12, 13, 15, 19, 4, 9, 10, 14, 21};
    // ข้อทางบวก
    int[] positiveIndexes = {3, 6, 8, 11, 16, 17, 18, 20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remake_advance_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        // สร้างคำถามตัวอย่าง
//        for (int i = 0; i < 22; i++) {
//            questions[i] = "ข้อ " + (i + 1);
//        }
        questions[0] = "ข้อ 1 : ฉันรู้สึกห่อเหี่ยวจิตใจกับงานที่ทำอยู่";
        questions[1] = "ข้อ 2 : ฉันจะรู้สึกหมดหวังเมื่อถึงเวลาเลิกงาน";
        questions[2] = "ข้อ 3 : ฉันรู้สึกอ่อนเพลียตอนตื่นนอนและตอนเข้าทำงาน";
        questions[3] = "ข้อ 4 : ฉันสามารถเข้าใจความรู้สึกนึกคิดของผู้อื่นได้โดยง่าย";
        questions[4] = "ข้อ 5 : ฉันปฏิบัติต่อผู้รับบริการราวกับเขาไม่มีชีวิตจิตใจ";
        questions[5] = "ข้อ 6 : การทำงานบริการผู้อื่นตลอดทั้งวันทำให้ฉันรู้สึกเครียด";
        questions[6] = "ข้อ 7 : ฉันรู้สึกว่าตนเองสามารถแก้ไขปัญหาต่าง ๆ ให้ผู้อื่นได้อย่างมีประสิทธิภาพ";
        questions[7] = "ข้อ 8 : ฉันรู้สึกเหนื่อยหน่ายกับงานที่ทำอยู่";
        questions[8] = "ข้อ 9 : ฉันรู้สึกว่าได้ทำให้เกิดการเปลี่ยนแปลงที่ดีขึ้นในชีวิตของผู้รับบริการ จากการทำงานของฉัน";
        questions[9] = "ข้อ 10 : ฉันกลายเป็นคนแข็งกระด้างตั้งแต่เริ่มทำงานนี้";
        questions[10] = "ข้อ 11 : ฉันกังวลใจว่างานที่ทำอยู่ทำให้ฉันเป็นคนเจ้าอารมณ์";
        questions[11] = "ข้อ 12 : ฉันรู้สึกเต็มเปี่ยมไปด้วยพละกำลัง";
        questions[12] = "ข้อ 13 : ฉันรู้สึกคับข้องใจจากการทำงาน";
        questions[13] = "ข้อ 14 : ฉันรู้สึกว่ากำลังทำงานในหน้าที่ทหนักเกินไป";
        questions[14] = "ข้อ 15 : ฉันรู้สึกไม่อยากใส่ใจต่อพฤติกรรมของผู้รับบริการบางคน";
        questions[15] = "ข้อ 16 : การทำงานเกี่ยวข้องกับคนอื่นโดยตรงทำให้ฉันรู้สึกเครียดมากเกินไป";
        questions[16] = "ข้อ 17 : ฉันสามารถสร้างบรรยากาศที่เป็นกันเองกับผู้รับบริการได้ไม่ยาก";
        questions[17] = "ข้อ 18 : ฉันรู้สึกเป็นสุขภายหลังจากให้บริการกับบผู้รับบริการอย่างใกล้ชิด";
        questions[18] = "ข้อ 19 : ฉันรู้สึกว่าได้สร้างสิ่งที่มีคุณค่ามากให้กับงานที่ฉันทำอยู่";
        questions[19] = "ข้อ 20 : ฉันรู้สึกหมดความอดทนกับงานที่ทำอยู่";
        questions[20] = "ข้อ 21 : ในการทำงานฉันสามารถเผชิญปัญหาทางอารมณ์ได้อย่างสงบนิ่ง";
        questions[21] = "ข้อ 22 : ฉันรู้สึกว่าผู้ร่วมงานและผู้รับบริการตำหนิฉันในส่วนที่เป็นปัญหาของเขา";

        LinearLayout questionList = findViewById(R.id.question_list_2);
        Button btnSubmit = findViewById(R.id.btn_submit_2);

        String[] choices = {
                "ไม่เคยรู้สึกเช่นนั้นเลย",
                "ปีละ 2-3 ครั้ง",
                "เดือนละ 1 ครั้ง",
                "เดือนละ 2-3 ครั้ง",
                "สัปดาห์ละ 1 ครั้ง",
                "สัปดาห์ละ 2-3 ครั้ง",
                "ทุกๆ วัน"
        };

        for (int i = 0; i < questions.length; i++) {
            int index = i;
            TextView questionText = new TextView(this);
            questionText.setText(questions[i]);
            questionList.addView(questionText);

            RadioGroup radioGroup = new RadioGroup(this);
            for (int j = 0; j < choices.length; j++) {
                RadioButton rb = new RadioButton(this);
                rb.setText(choices[j]);
                radioGroup.addView(rb);
            }
            questionList.addView(radioGroup);

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int selected = group.indexOfChild(group.findViewById(checkedId));
                answers[index] = selected;
                if (checkAllAnswered()) btnSubmit.setVisibility(View.VISIBLE);
            });
        }

        btnSubmit.setOnClickListener(v -> {
            int emoExhaust = 0;
            int dePerson = 0;
            int personalAch = 0;

            for (int i = 0; i < 22; i++) {
                int score;
                if (isNegative(i)) {
                    score = answers[i]; // 0-6
                } else {
                    score = 6 - answers[i]; // แปลงคะแนนสำหรับข้อทางบวก
                }

                if (isInArray(i, new int[]{0, 1, 2, 5, 7, 12, 13, 15, 19})) {
                    emoExhaust += score;
                } else if (isInArray(i, new int[]{4, 9, 10, 14, 21})) {
                    dePerson += score;
                } else {
                    personalAch += score;
                }
            }

            Intent intent = new Intent(this, AdvanceTestActivity.class);
            intent.putExtra("emoExhaust", emoExhaust);
            intent.putExtra("dePerson", dePerson);
            intent.putExtra("personalAch", personalAch);
            startActivity(intent);
            finish();
        });
    }

    private boolean isNegative(int index) {
        for (int i : negativeIndexes) if (i == index) return true;
        return false;
    }

    private boolean isInArray(int index, int[] array) {
        for (int i : array) if (i == index) return true;
        return false;
    }

    private boolean checkAllAnswered() {
        for (Integer answer : answers) if (answer == null) return false;
        return true;

    }
}