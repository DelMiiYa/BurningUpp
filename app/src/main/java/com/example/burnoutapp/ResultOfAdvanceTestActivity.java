package com.example.burnoutapp;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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

        TextView resultofresult = findViewById(R.id.resultofresult);
        TextView txtResult1 = findViewById(R.id.txt_result_21);
        TextView txtResult2 = findViewById(R.id.txt_result_22);
        TextView txtResult3 = findViewById(R.id.txt_result_23);
        TextView markresult1 = findViewById(R.id.markresult_21);
        TextView markresult2 = findViewById(R.id.markresult_22);
        TextView markresult3 = findViewById(R.id.markresult_23);

        Button btnNext = findViewById(R.id.btn_next_2);

        int emoExhaust = getIntent().getIntExtra("emoExhaust", 0);
        int dePerson = getIntent().getIntExtra("dePerson", 0);
        int personalAch = getIntent().getIntExtra("personalAch", 0);

        String result;
        int resultcolor;
        int resultbyExhaust;
        int resultbyDePerson;
        int resultbyPersonalAch;

        boolean highExhaust = emoExhaust >= 27;
        boolean mediumExhaust = emoExhaust < 27 && emoExhaust >= 17;

        boolean highDePerson = dePerson >= 13;
        boolean mediumDePerson = dePerson < 13 && dePerson >= 7;

        boolean lowPersonalAch = personalAch <= 31;
        boolean mediumPersonalAch = personalAch > 31 && personalAch <= 38;


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


        if (highExhaust || highDePerson || lowPersonalAch) {
            result = "สูง";
            resultcolor = Color.RED;
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users")
                                .document(user.getUid())
                                .update("burnoutLevel", 3);

                        // 👇 Save advanced test data
                        Map<String, Object> advancedTestData = new HashMap<>();
                        advancedTestData.put("emoExhaust", emoExhaust);
                        advancedTestData.put("dePerson", dePerson);
                        advancedTestData.put("personalAch", personalAch);
                        advancedTestData.put("result", result);
                        db.collection("users")
                                .document(user.getUid())
                                .update("advancedTest", advancedTestData);

                        Intent intent2 = new Intent(getApplicationContext(), AdditionalInfoActivity.class);
                        startActivity(intent2);
                    }
                }
            });


        } else {
            result = "ต่ำ";
            resultcolor = Color.GREEN;
            btnNext.setOnClickListener(v -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> advancedTestData = new HashMap<>();
                    advancedTestData.put("emoExhaust", emoExhaust);
                    advancedTestData.put("dePerson", dePerson);
                    advancedTestData.put("personalAch", personalAch);
                    advancedTestData.put("result", result); // "สูง" or "ต่ำ"

                    db.collection("users")
                            .document(user.getUid())
                            .update(
                                    "burnoutLevel", 3,
                                    "advancedTest", advancedTestData
                            )
                            .addOnSuccessListener(aVoid -> {
                                Intent intent2 = new Intent(getApplicationContext(), AdditionalInfoActivity.class);
                                startActivity(intent2);
                            });
                }
            });
        }

        String detailbyExhaust = "คะแนนความอ่อนล้าทางอารมณ์ : " + emoExhaust;
        String detailbyDePerson = "คะแนนการลดความเป็นบุคคล : " + dePerson;
        String detailbyPersonalAch = "คะแนนความสำเร็จส่วนบุคคล : " + personalAch;


        resultofresult.setText(result);
        resultofresult.setTextColor(resultcolor);
        markresult1.setTextColor(resultbyExhaust);
        txtResult1.setText(detailbyExhaust);
        markresult2.setTextColor(resultbyDePerson);
        txtResult2.setText(detailbyDePerson);
        markresult3.setTextColor(resultbyPersonalAch);
        txtResult3.setText(detailbyPersonalAch);

    }
}