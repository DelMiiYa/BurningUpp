package com.example.javaaintshit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultOfAdvanceTestActivity extends AppCompatActivity {

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

        TextView resultofresult = findViewById(R.id.resultofresult); //ข้อความแสดงผลรวม
        TextView txtResult1 = findViewById(R.id.txt_result_21);      //ข้อความแสดงผลคะแนนการอ่อนล้าทางอารมณ์
        TextView txtResult2 = findViewById(R.id.txt_result_22);      //ข้อความแสดงผลคะแนนการลดความเป็นบุคคล
        TextView txtResult3 = findViewById(R.id.txt_result_23);      //ข้อความแสดงผลคะแนนความสำเร็จส่วนบุคคล
        TextView markresult1 = findViewById(R.id.markresult_21);     //สี * แสดงระดับคะแนนการอ่อนล้าทางอารมณ์
        TextView markresult2 = findViewById(R.id.markresult_22);     //สี * แสดงระดับคะแนนการลดความเป็นบุคคล
        TextView markresult3 = findViewById(R.id.markresult_23);     //สี * แสดงระดับคะแนนความสำเร็จส่วนบุคคล

        Button btnNext = findViewById(R.id.btn_next_2); //ปุ่มไปหน้า ถัดไป

        int emoExhaust = getIntent().getIntExtra("emoExhaust", 0); //รับค่าจากหน้าก่อนหน้า
        int dePerson = getIntent().getIntExtra("dePerson", 0);
        int personalAch = getIntent().getIntExtra("personalAch", 0);

        String result; //เก็บค่าสำหรับข้อความแสดงผลรวม
        int resultcolor; //เก็บค่าสีสำหรับข้อความแสดงผลรวม
        int resultbyExhaust; //เก็บค่าสีสำหรับข้อความแสดงผลคะแนนการอ่อนล้าทางอารมณ์
        int resultbyDePerson;  //เก็บค่าสีสำหรับข้อความแสดงผลคะแนนการลดความเป็นบุคคล
        int resultbyPersonalAch; //เก็บค่าสีสำหรับข้อความแสดงผลคะแนนความสำเร็จส่วนบุคคล

//กำหนดเกณฑ์การแสดงผลย่อย
        boolean highExhaust = emoExhaust >= 27;
        boolean mediumExhaust = emoExhaust < 27 && emoExhaust >= 17;

        boolean highDePerson = dePerson >= 13;
        boolean mediumDePerson = dePerson < 13 && dePerson >= 7;

        boolean lowPersonalAch = personalAch <= 31;
        boolean mediumPersonalAch = personalAch > 31 && personalAch <= 38;

//กำหนดสีสำหรับข้อความแสดงผลย่อย
        if(highExhaust){
            resultbyExhaust = Color.RED;
        }else if(mediumExhaust){
            resultbyExhaust = Color.YELLOW;
        }else{
            resultbyExhaust = Color.GREEN ;
        }

        if(highDePerson){
            resultbyDePerson = Color.RED;
        }else if(mediumDePerson){
            resultbyDePerson = Color.YELLOW;
        }else{
            resultbyDePerson = Color.GREEN;
        }
        if(lowPersonalAch){
            resultbyPersonalAch = Color.RED;
        }else if(mediumPersonalAch){
            resultbyPersonalAch = Color.YELLOW;
        }else{
            resultbyPersonalAch = Color.GREEN;
        }

//กำหนดค่าสำหรับข้อความแสดงผลรวม
        if (highExhaust || highDePerson || lowPersonalAch) {
            result = "สูง";
            resultcolor = Color.RED;
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //แก้เป็น เปิดหน้า ส่งหมอ แต่ตอนนี้ยังไม่มีหน้าส่งหมอ
                    Intent intent2 = new Intent(getApplicationContext(), TipsActivity.class);
                    startActivity(intent2);
                }
            });
        } else {
            result = "ต่ำ";
            resultcolor = Color.GREEN;
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //เปิดหน้า แสดงคำแนะนำ
                    Intent intent2 = new Intent(getApplicationContext(), TipsActivity.class);
                    startActivity(intent2);
                }
            });
        }
//กำหนดค่าสำหรับข้อความแสดงผลคะแนน
        String detailbyExhaust = "คะแนนความอ่อนล้าทางอารมณ์ : " + emoExhaust;
        String detailbyDePerson = "คะแนนการลดความเป็นบุคคล : " + dePerson;
        String detailbyPersonalAch = "คะแนนความสำเร็จส่วนบุคคล : " + personalAch;

//กำหนดค่าสำหรับข้อความแสดงผล
        resultofresult.setText(result); //ผลรวม
        resultofresult.setTextColor(resultcolor);

        markresult1.setTextColor(resultbyExhaust);//คะแนนการอ่อนล้าทางอารมณ์
        txtResult1.setText(detailbyExhaust);

        markresult2.setTextColor(resultbyDePerson);//คะแนนการลดความเป็นบุคคล
        txtResult2.setText(detailbyDePerson);

        markresult3.setTextColor(resultbyPersonalAch);//คะแนนความสำเร็จส่วนบุคคล
        txtResult3.setText(detailbyPersonalAch);

    }
}