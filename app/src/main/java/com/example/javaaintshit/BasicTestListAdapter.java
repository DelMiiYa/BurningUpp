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

public class BasicTestListAdapter extends RecyclerView.Adapter<BasicTestListAdapter.assmentViewHolder>  {
    private List<BasicTestData> questionList;

    public BasicTestListAdapter(List<BasicTestData> questionList) {
        this.questionList = questionList;
    }

    public static class assmentViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewQuestion;
        public RadioGroup radioGroupAnswer;
        public RadioButton radioButtonYes;
        public RadioButton radioButtonNo;

        public assmentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.assmenttext);
            radioGroupAnswer = itemView.findViewById(R.id.yesnobtn);
            radioButtonYes = itemView.findViewById(R.id.btnyes);
            radioButtonNo = itemView.findViewById(R.id.btnno);
        }
    }

    @NonNull
    @Override
    public assmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.templateforassessment1, parent, false);
        return new assmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull assmentViewHolder holder, int position) {
        BasicTestData currentItem = questionList.get(position);
        holder.textViewQuestion.setText(currentItem.getQuestionText());

        holder.radioGroupAnswer.clearCheck();
        Integer answer = currentItem.getAnswer();
        if (answer != null) {
            if (answer == 1) {
                holder.radioButtonYes.setChecked(true);
            } else if (answer == 0){
                holder.radioButtonNo.setChecked(true);
            }
        }

        holder.radioGroupAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btnyes) {
                    currentItem.setAnswer(1);
                } else if (checkedId == R.id.btnno) {
                    currentItem.setAnswer(0);
                }
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
