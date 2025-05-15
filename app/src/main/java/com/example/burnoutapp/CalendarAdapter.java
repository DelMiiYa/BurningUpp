package com.example.burnoutapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.widget.ImageView;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;


class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private LocalDate selectedDate;
    private Map<LocalDate, Integer> moodMap;


    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener,
                           LocalDate selectedDate, Map<LocalDate, Integer> moodMap)
    {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.selectedDate = selectedDate;
        this.moodMap = moodMap;
    }


    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        String dayText = daysOfMonth.get(position);
        holder.dayOfMonth.setText(dayText);

        // Default: hide mood icon
        holder.moodImageView.setVisibility(View.GONE);

        if (!dayText.equals("")) {
            int day = Integer.parseInt(dayText);
            LocalDate cellDate = LocalDate.of(
                    selectedDate.getYear(),
                    selectedDate.getMonth(),
                    day
            );

            if (moodMap != null && moodMap.containsKey(cellDate)) {
                int mood = moodMap.get(cellDate);
                holder.moodImageView.setVisibility(View.VISIBLE);
                holder.moodImageView.setImageResource(getMoodResId(mood));
            }
        }

    }

    public void setMoodMap(Map<LocalDate, Integer> moodMap) {
        this.moodMap = moodMap;
        notifyDataSetChanged();
    }


    private int getMoodResId(int mood) {
        switch (mood) {
            case 0: return R.drawable.ic_mood1;
            case 1: return R.drawable.ic_mood2;
            case 2: return R.drawable.ic_mood3;
            case 3: return R.drawable.ic_mood4;
            case 4: return R.drawable.ic_mood5;
            default: return 0;
        }
    }


    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}