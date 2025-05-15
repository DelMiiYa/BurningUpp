package com.example.burnoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipsActivity extends AppCompatActivity {

    //private TextView textViewTips;
    private Button buttonBack;
    private Button buttonFinish;
////======================= test mang ===================//
//    private boolean isBurnout = false;
    ////====================================================//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        //textViewTips = findViewById(R.id.textViewTips);
        buttonBack = findViewById(R.id.buttonBack);
        buttonFinish = findViewById(R.id.buttonFinish);

        // out put result from EvaluationActivity
        boolean isBurnout = getIntent().getBooleanExtra("isBurnout", false);
////======================= test mang ===================//
//        isBurnout = getIntent().getBooleanExtra("isBurnout", isBurnout);
////====================================================//

        // you is or not burnout
        if (isBurnout) {
            // go go DetailTestActivity
            //Intent intent = new Intent(TipsActivity.this, DetailTestActivity.class);
            //startActivity(intent);
            finish();
        } else {
            displayBurnoutPreventionTips();
        }

        // back to result
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // set btn finish Listener
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go go menu
                //Intent intent = new Intent(TipsActivity.this, MenuActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // clear back stack
                //startActivity(intent);
                finish();
            }
        });
    }
    private void displayBurnoutPreventionTips() {
        StringBuilder tips = new StringBuilder();
        tips.append("กลยุทธ์ในการต่อสู้กับภาวะหมดไฟ:\n");
        tips.append("สำรวจตนเอง: ตระหนักว่ากำลังมีภาวะเหนื่อยหน่ายหรือไม่\n");
        tips.append("นอนให้มากขึ้น: พักผ่อนให้เพียงพอเพื่อฟื้นฟูร่างกาย\n");
        tips.append("ออกกำลังกายสม่ำเสมอ: โดยเฉพาะการออกกำลังกายแบบหัวใจและหลอดเลือด\n");
        tips.append("ทำสมาธิ: ฝึกสติเพื่อรับมือกับปัญหา\n");
        tips.append("ฝึกหายใจ: เทคนิคการหายใจอย่างมีสติเพื่อผ่อนคลาย\n");
        tips.append("\nข้อควรระวัง: ภาวะหมดไฟไม่ใช่โรคซึมเศร้า แต่หากมีอาการเศร้า หดหู่ เบื่อหน่าย หรือคิดทำร้ายตนเอง ควรรีบปรึกษาแพทย์หรือผู้เชี่ยวชาญ");
        System.out.println(tips);
    }
}