package com.example.burnoutapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import android.graphics.Color;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    PieChart pieChart;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pieChart = findViewById(R.id.pie_chart);
        barChart = findViewById(R.id.bar_chart);

        setupPieChart();
        setupBarChart();
    }

    private void setupPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(62.5f, "happy"));
        entries.add(new PieEntry(25f, "sad"));
        entries.add(new PieEntry(12.5f, "angry"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{Color.CYAN, Color.BLUE, Color.GRAY});
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private void setupBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 8f)); // work performance
        entries.add(new BarEntry(1f, 5f)); // activity level
        entries.add(new BarEntry(2f, 2f)); // mood stability

        BarDataSet dataSet = new BarDataSet(entries, "คะแนนที่ได้");
        dataSet.setColor(Color.CYAN);
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate();
    }
}