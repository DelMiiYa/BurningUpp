package com.example.javaaintshit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdvanceTestListAdapter extends RecyclerView.Adapter<AdvanceTestListAdapter.assmentViewHolder>  {
    private List<AdvanceTestData> questionList;

    public AdvanceTestListAdapter(List<AdvanceTestData> questionList) {
        this.questionList = questionList;
    }

    public static class assmentViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewQuestion;
        public RadioGroup radioGroupAnswer;
        public RadioButton ch1,ch2,ch3,ch4,ch5; //choice radio button

        public assmentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.assmenttext);
            radioGroupAnswer = itemView.findViewById(R.id.choices12345);
            ch1 = itemView.findViewById(R.id.btnch1);
            ch2 = itemView.findViewById(R.id.btnch2);
            ch3 = itemView.findViewById(R.id.btnch3);
            ch4 = itemView.findViewById(R.id.btnch4);
            ch5 = itemView.findViewById(R.id.btnch5);

        }
    }

    @NonNull
    @Override
    public AdvanceTestListAdapter.assmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.templateforassessment2, parent, false);
        return new AdvanceTestListAdapter.assmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvanceTestListAdapter.assmentViewHolder holder, int position) {
        AdvanceTestData currentItem = questionList.get(position);
        holder.textViewQuestion.setText(currentItem.getQuestionText());

        holder.radioGroupAnswer.clearCheck();
//        Integer answer = currentItem.getAnswer();
//        if (answer != 0) {
//            if (answer) {
//                holder.ch1.setChecked(true);
//            } else if (answer) {
//
//            } else {
//                holder.ch5.setChecked(true);
//            }
//        }

        holder.radioGroupAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (checkedId == R.id.btnyes) {
//                    currentItem.setAnswer(true);
//                } else if (checkedId == R.id.btnno) {
//                    currentItem.setAnswer(false);
//                }
//                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
