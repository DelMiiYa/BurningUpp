package com.example.burnoutapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public final TextView dayOfMonth;
    public final ImageView moodImageView;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, final CalendarAdapter.OnItemListener onItemListener)
    {
        super(itemView);
        this.onItemListener = onItemListener;
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        moodImageView = itemView.findViewById(R.id.moodImageView); // ⬅️ This connects the mood icon
        itemView.setOnClickListener(this); // Use the implemented interface
    }

    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
