package com.example.javaaintshit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BasicTestActivity extends AppCompatActivity {

    private RecyclerView recViewAssment;
    private BasicTestListAdapter BasicTestListAdapter;
    private List<BasicTestData> assmentList;
    private Button btnsubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assessment1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recViewAssment = findViewById(R.id.recyviewforass1);
        recViewAssment.setLayoutManager(new LinearLayoutManager(this));
        recViewAssment.setHasFixedSize(true);

        assmentList = new ArrayList<>();
        assmentList.add(new BasicTestData("ฉันเริ่มรู้สึกว่าหน้าที่การงานที่เคยทำด้วยความกระตือรือร้น กลายเป็นเรื่องน่าเบื่อหน่าย"));
        assmentList.add(new BasicTestData("ฉันรู้สึกว่าตัวเองไม่มีคุณค่า หรือไม่มีความหมายกับงานที่ทำ"));
        assmentList.add(new BasicTestData("ฉันรู้สึกเหนื่อยล้า แม้ว่าจะได้พักผ่อนเพียงพอแล้วก็ตาม"));
        assmentList.add(new BasicTestData("ฉันรู้สึกเครียดหรือวิตกกังวลเกี่ยวกับงานตลอดเวลา แม้ในช่วงเวลาที่ควรพักผ่อน"));
        assmentList.add(new BasicTestData("ฉันมีปัญหาในการจดจ่อหรือทำงานที่ต้องใช้สมาธิอย่างต่อเนื่อง"));
        assmentList.add(new BasicTestData("ฉันเริ่มหลีกเลี่ยงหรือลดการติดต่อกับเพื่อนร่วมงานหรือลูกค้า"));
        assmentList.add(new BasicTestData("ฉันรู้สึกหมดกำลังใจเมื่อคิดถึงการทำงานในแต่ละวัน"));
        assmentList.add(new BasicTestData("ฉันรู้สึกว่าไม่มีพลังหรือแรงบันดาลใจที่จะพัฒนาตนเองในหน้าที่การงาน"));
        assmentList.add(new BasicTestData("ฉันเริ่มมีอาการทางกาย เช่น ปวดหัว ปวดท้อง หรือปวดเมื่อยเรื้อรังโดยไม่ทราบสาเหตุ"));
        assmentList.add(new BasicTestData("ฉันเคยคิดจะลาออกหรือเปลี่ยนงานเพราะรู้สึกว่าตนเองไม่สามารถทนต่อสภาพแวดล้อมการทำงานได้อีกต่อไป"));

        BasicTestListAdapter = new BasicTestListAdapter(assmentList);
        recViewAssment.setAdapter(BasicTestListAdapter);

        btnsubmit = findViewById(R.id.btnsubmit); // เปลี่ยน ID ปุ่ม
        btnsubmit.setVisibility(View.GONE); // ซ่อนปุ่มเริ่มต้น

        checkIfAllAnswered(); // ตรวจสอบครั้งแรกเมื่อ Activity สร้าง
    }
    public List<Integer> getAnswers() {
        List<Integer> answers = new ArrayList<>();
        for (BasicTestData question : assmentList) {
            answers.add(question.getAnswer());
        }
        return answers;

    }
    public void checkIfAllAnswered() {
        boolean allAnswered = true;
        for (BasicTestData question : assmentList) {
            if (question.getAnswer() == null) {
                allAnswered = false;
                break;
            }
        }

        if (btnsubmit != null) {
            if (allAnswered) {
                btnsubmit.setVisibility(View.VISIBLE);
                btnsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Integer> answers = getAnswers();
//                        Intent intent = new Intent(BasicTestActivity.this, NextActivity.class);
//                        intent.putIntegerArrayListExtra("assessment1Answers", (ArrayList<Integer>) answers);
//                        startActivity(intent);
                    }
                });
            } else {
                btnsubmit.setVisibility(View.GONE);
                btnsubmit.setOnClickListener(null); // ป้องกันการทำงานเมื่อยังไม่พร้อม
            }
        }
    }
}