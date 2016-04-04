package com.vicmns.mpandroidcharttext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicmns on 3/31/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<BarData> mBarDataList;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
        mBarDataList = new ArrayList<>();
    }

    public void addDatSet(BarData barData) {
        mBarDataList.add(barData);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chart_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBarChart.setData(mBarDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBarDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View cardView;
        BarChart mBarChart;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            mBarChart = (BarChart) itemView.findViewById(R.id.barChartView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailActivity = new Intent(mContext, DetailActivity.class);
                    DetailActivity.setBarData(mBarDataList.get(getAdapterPosition()));


                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) cardView.getContext(),
                            Pair.create(cardView, DetailActivity.VIEW_NAME_CHART_CONTAINER)
                    );

                    mContext.startActivity(detailActivity, options.toBundle());
                }
            });


            mBarChart.setDescription("");
            mBarChart.setDrawGridBackground(false);
            mBarChart.setDrawBarShadow(false);
            mBarChart.setDoubleTapToZoomEnabled(false);
            mBarChart.setHighlightPerTapEnabled(false);
            mBarChart.setHighlightPerDragEnabled(false);

            //disable scaling and dragging
            mBarChart.setTouchEnabled(false);
            mBarChart.setScaleEnabled(false);
            mBarChart.setPinchZoom(false);
            mBarChart.setDoubleTapToZoomEnabled(false);

            //Uncomment for testing
            //mBarChart.setHardwareAccelerationEnabled(false);

            XAxis xAxis = mBarChart.getXAxis();
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelRotationAngle(-90);

            YAxis leftAxis = mBarChart.getAxisLeft();
            leftAxis.setAxisMinValue(0);
            leftAxis.setDrawAxisLine(false);
            leftAxis.setGridColor(ContextCompat.getColor(mContext, R.color.charts_grid_line_color));
            leftAxis.setValueFormatter(new LargeValueFormatter());
            leftAxis.setLabelCount(5, false);
            leftAxis.setSpaceTop(20f);

            YAxis rightAxis = mBarChart.getAxisRight();
            rightAxis.setEnabled(false);

            Legend legend = mBarChart.getLegend();
            legend.setEnabled(false);
        }
    }
}
