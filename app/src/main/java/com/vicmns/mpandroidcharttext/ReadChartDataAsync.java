package com.vicmns.mpandroidcharttext;

import android.content.Context;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicmns on 3/31/16.
 */
public class ReadChartDataAsync extends AsyncTask<Void, Void, Void> {
    Context mContext;
    List<IBarDataSet> mDataSetsList;
    List<String> mLabelsList;
    Gson mGson;
    ReadChartDataAsyncListeners mReadChartDataAsyncListeners;

    public ReadChartDataAsync(Context context, ReadChartDataAsyncListeners readChartDataAsyncListeners) {
        mContext = context;
        mReadChartDataAsyncListeners = readChartDataAsyncListeners;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLabelsList = new ArrayList<>();
        mGson = new Gson();
    }

    @Override
    protected Void doInBackground(Void... params) {
        InputStreamReader isr = new InputStreamReader(mContext.getResources().openRawResource(R.raw.chart_dataset));
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDataSetsList = mGson.fromJson(sb.toString(), new TypeToken<List<BarDataSet>>(){}.getType());

        isr = new InputStreamReader(mContext.getResources().openRawResource(R.raw.chart_labels));
        bufferedReader = new BufferedReader(isr);
        sb = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mLabelsList = mGson.fromJson(sb.toString(), new TypeToken<List<String>>(){}.getType());

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mReadChartDataAsyncListeners.onReadingCompleted(mDataSetsList, mLabelsList);
    }

    public interface  ReadChartDataAsyncListeners {
        void onReadingCompleted(List<IBarDataSet> dataSetList, List<String> labelsList);
    }
}
