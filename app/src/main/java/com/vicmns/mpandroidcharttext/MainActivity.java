package com.vicmns.mpandroidcharttext;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ReadChartDataAsync.ReadChartDataAsyncListeners {
    FloatingActionButton mFloatingActionButton;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;
    List<IBarDataSet> mDataSetList;
    List<String> mLabelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerViewAdapter = new RecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mFloatingActionButton.setVisibility(View.INVISIBLE);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadChartDataAsync readChartDataAsync = new ReadChartDataAsync(MainActivity.this,
                        new ReadChartDataAsync.ReadChartDataAsyncListeners() {
                            @Override
                            public void onReadingCompleted(List<IBarDataSet> dataSetList, List<String> labelsList) {
                                mRecyclerViewAdapter.addDatSet(new BarData(labelsList, dataSetList));
                            }
                        });
                readChartDataAsync.execute();
            }
        });

        new ReadChartDataAsync(this, this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReadingCompleted(List<IBarDataSet> dataSetList, List<String> labelsList) {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mDataSetList = dataSetList;
        mLabelsList = labelsList;
        mRecyclerViewAdapter.addDatSet(new BarData(labelsList, dataSetList));
    }
}
