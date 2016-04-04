package com.vicmns.mpandroidcharttext;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class DetailActivity extends AppCompatActivity {
    public static final String VIEW_NAME_CHART_CONTAINER = "chart:container";
    private ViewGroup mMainLayout, mContentLayout;
    private BarChart mBarChart;
    //Is not the best practice, but since the data is to large for a bundle, I set it as an static
    private static BarData sBarData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This is the working layout
        //setContentView(R.layout.activity_detail);
        //Use this layout yo test the activity hang, sometimes it will hang on activity start
        //Other times it will hang on activity exit
        //The only difference between this two activities is the toolbar...
        setContentView(R.layout.activity_detail_hang);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMainLayout = (ViewGroup) findViewById(R.id.detailMainLayout);
        mContentLayout = (ViewGroup) findViewById(R.id.contentLayout);

        mBarChart = new BarChart(this);

        mBarChart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        if(sBarData != null)
            mBarChart.setData(sBarData);

        mContentLayout.addView(mBarChart);

        ViewCompat.setTransitionName(mContentLayout, VIEW_NAME_CHART_CONTAINER);
    }

    public static void setBarData(BarData barData) {
        sBarData = barData;
    }
}
