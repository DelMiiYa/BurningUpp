package com.example.javaaintshit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class assessment2 extends AppCompatActivity {

    private RecyclerView recViewAssment;
    private assessment2ListAdapt assessment2ListAdapt;
    private List<assment002> assmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assessment2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recViewAssment = findViewById(R.id.recyviewforass2);
        recViewAssment.setLayoutManager(new LinearLayoutManager(this));
        recViewAssment.setHasFixedSize(true);

        assmentList = new ArrayList<>();
        assmentList.add(new assment002("ฉันเริ่มรู้สึกว่าหน้าที่การงานที่เคยทำด้วยความกระตือรือร้น กลายเป็นเรื่องน่าเบื่อหน่าย"));
        assmentList.add(new assment002("ฉันรู้สึกว่าตัวเองไม่มีคุณค่า หรือไม่มีความหมายกับงานที่ทำ"));
        assmentList.add(new assment002("ฉันรู้สึกเหนื่อยล้า แม้ว่าจะได้พักผ่อนเพียงพอแล้วก็ตาม"));
        assmentList.add(new assment002("ฉันรู้สึกเครียดหรือวิตกกังวลเกี่ยวกับงานตลอดเวลา แม้ในช่วงเวลาที่ควรพักผ่อน"));
        assmentList.add(new assment002("ฉันมีปัญหาในการจดจ่อหรือทำงานที่ต้องใช้สมาธิอย่างต่อเนื่อง"));
        assmentList.add(new assment002("ฉันเริ่มหลีกเลี่ยงหรือลดการติดต่อกับเพื่อนร่วมงานหรือลูกค้า"));
        assmentList.add(new assment002("ฉันรู้สึกหมดกำลังใจเมื่อคิดถึงการทำงานในแต่ละวัน"));
        assmentList.add(new assment002("ฉันรู้สึกว่าไม่มีพลังหรือแรงบันดาลใจที่จะพัฒนาตนเองในหน้าที่การงาน"));
        assmentList.add(new assment002("ฉันเริ่มมีอาการทางกาย เช่น ปวดหัว ปวดท้อง หรือปวดเมื่อยเรื้อรังโดยไม่ทราบสาเหตุ"));
        assmentList.add(new assment002("ฉันเคยคิดจะลาออกหรือเปลี่ยนงานเพราะรู้สึกว่าตนเองไม่สามารถทนต่อสภาพแวดล้อมการทำงานได้อีกต่อไป"));

        assessment2ListAdapt = new assessment2ListAdapt(assmentList);
        recViewAssment.setAdapter(assessment2ListAdapt);
    }

    public List<Boolean> getAnswers() {
        List<Boolean> answers = new ArrayList<>();
        for (assment002 question : assmentList) {
            answers.add(question.getAnswer());
        }
        return answers;


    }
}