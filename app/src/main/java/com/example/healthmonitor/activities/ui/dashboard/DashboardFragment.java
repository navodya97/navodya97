package com.example.healthmonitor.activities.ui.dashboard;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthmonitor.databinding.FragmentDashboardBinding;
import com.example.healthmonitor.models.ApiResponse;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";
    private FragmentDashboardBinding fragmentDashboardBinding;
    private LineChart lineChart,lineChart2,lineChart3;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = fragmentDashboardBinding.getRoot();
        return root;

    }





    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lineChart = fragmentDashboardBinding.tempChart;
        lineChart2 = fragmentDashboardBinding.heartChart;
        lineChart3 = fragmentDashboardBinding.ppgChart;
        String deviceID = getActivity().getIntent().getStringExtra("deviceID");




        Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performGraphTempData(deviceID);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if (response.code() == 200) {
                    Log.e(TAG, "onResponse:Body "+response.body() );

                    ArrayList<ApiResponse.data_graphtemp> data_Temp =response.body().getTemp_data();


                    ArrayList<Entry> yVal=new ArrayList<>();
                    ArrayList<String> xAxisValues = new ArrayList<>();
try {

    for (int i = 0; i < data_Temp.size(); i++) {

        ApiResponse.data_graphtemp data11 = data_Temp.get(i);

        String tempVal = data11.getGraph_Temp();
        String date = data11.getTime_s();
        float temp = Float.valueOf(tempVal);
        yVal.add(new Entry(i,temp));
        xAxisValues.add(date);
        Log.e(TAG, "onResponse: Temp " + data11.getGraph_Temp());

    }
}catch (Exception e){

}


                    LineDataSet set1 =new LineDataSet(yVal,"Body Temperature");

                    set1.setLineWidth(2f);
                    set1.setColor(Color.BLUE);
                    set1.setHighLightColor(Color.WHITE);
                    set1.setDrawValues(true);
                    set1.setValueTextSize(10f);
                    set1.setValueTextColors(Collections.singletonList(0xE60022FF));



                    set1.setDrawCircles(true);
                    set1.setCircleColor(Color.BLUE);
                    set1.setCircleRadius(4f);
                    set1.setCircleHoleColor(Color.WHITE);
                    set1.setCircleHoleRadius(2f);
                    set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


                    XAxis xAxis;
                    xAxis= lineChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawLimitLinesBehindData(true);
                    xAxis.setGridColor(Color.BLACK);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMaximum(data_Temp.size());
                    xAxis.setLabelRotationAngle(90f);
                    lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));


                    LimitLine line1 = new LimitLine(38.5f, "Upper Temp");
                    line1.setLineWidth(1.5f);
                    line1.setLineColor(Color.RED);
                    line1.enableDashedLine(10f, 5f, 0f);
                    line1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    line1.setTextSize(10f);


                    LimitLine line2 = new LimitLine(35f, "Lower Temp");
                    line2.setLineWidth(1.5f);
                    line2.setLineColor(Color.GREEN);
                    line2.enableDashedLine(10f, 5f, 0f);
                    line2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    line2.setTextSize(10f);


                    YAxis yAxis;
                    yAxis= lineChart.getAxisLeft();
                    yAxis.setAxisMaximum(40f);
                    yAxis.setAxisMinimum(30f);
                    yAxis.removeAllLimitLines();
                    yAxis.addLimitLine(line1);
                    yAxis.addLimitLine(line2);
                    yAxis.setGridColor(Color.BLACK);
                    lineChart.getAxisRight().setEnabled(false);

                    LineData data =new LineData(set1);


                   lineChart.getDescription().setEnabled(false);
                   lineChart.setDrawGridBackground(true);
                   lineChart.setGridBackgroundColor(Color.WHITE);
                   lineChart.setTouchEnabled(true);
                   lineChart.setDragEnabled(true);
                   lineChart.setScaleEnabled(true);
                   lineChart.setPinchZoom(true);
                   lineChart.setActivated(true);
                   lineChart.setData(data);

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });





        Call<ApiResponse> call1 = ApiClient.getApiClient().create(ApiInterface.class).performGraphHeartRateData(deviceID);
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if (response.code() == 200) {
                    Log.e(TAG, "onResponse:Body "+response.body() );

                    ArrayList<ApiResponse.data_graph_hear_trate> data_Heart =response.body().getData_heart_rate();


                    ArrayList<Entry> yVal=new ArrayList<>();
                    ArrayList<String> xAxisValues = new ArrayList<>();
                    try {

                        for (int i = 0; i < data_Heart.size(); i++) {

                            ApiResponse.data_graph_hear_trate data12 = data_Heart.get(i);

                            String heartVal = data12.getHeart_rate();
                            String date = data12.getTime_s();
                            float heart_rate = Float.valueOf(heartVal);
                            yVal.add(new Entry(i,heart_rate));
                            xAxisValues.add(date);
                            Log.e(TAG, "onResponse: Temp " + data12.getHeart_rate());

                        }
                    }catch (Exception e){

                    }


                    LineDataSet set1 =new LineDataSet(yVal,"Heart Rate");

                    set1.setLineWidth(2f);
                    set1.setColor(Color.BLUE);
                    set1.setHighLightColor(Color.WHITE);
                    set1.setDrawValues(true);
                    set1.setValueTextSize(10f);
                    set1.setValueTextColors(Collections.singletonList(0xE60022FF));



                    set1.setDrawCircles(true);
                    set1.setCircleColor(Color.BLUE);
                    set1.setCircleRadius(4f);
                    set1.setCircleHoleColor(Color.WHITE);
                    set1.setCircleHoleRadius(2f);
                    set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


                    XAxis xAxis;
                    xAxis= lineChart2.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawLimitLinesBehindData(true);
                    xAxis.setGridColor(Color.BLACK);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMaximum(data_Heart.size());
                    xAxis.setLabelRotationAngle(90f);
                    lineChart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));


                    LimitLine line1 = new LimitLine(100f, "Upper Heart Rate");
                    line1.setLineWidth(1.5f);
                    line1.setLineColor(Color.RED);
                    line1.enableDashedLine(10f, 5f, 0f);
                    line1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    line1.setTextSize(10f);


                    LimitLine line2 = new LimitLine(60f, "Lower Heart Rate");
                    line2.setLineWidth(1.5f);
                    line2.setLineColor(Color.GREEN);
                    line2.enableDashedLine(10f, 5f, 0f);
                    line2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    line2.setTextSize(10f);


                    YAxis yAxis;
                    yAxis= lineChart2.getAxisLeft();
                    yAxis.setAxisMaximum(110f);
                    yAxis.setAxisMinimum(40f);
                    yAxis.removeAllLimitLines();
                    yAxis.addLimitLine(line1);
                    yAxis.addLimitLine(line2);
                    yAxis.setGridColor(Color.BLACK);
                    lineChart2.getAxisRight().setEnabled(false);

                    LineData data =new LineData(set1);


                    lineChart2.getDescription().setEnabled(false);
                    lineChart2.setDrawGridBackground(true);
                    lineChart2.setGridBackgroundColor(Color.WHITE);
                    lineChart2.setTouchEnabled(true);
                    lineChart2.setDragEnabled(true);
                    lineChart2.setScaleEnabled(true);
                    lineChart2.setPinchZoom(true);
                    lineChart2.setActivated(true);
                    lineChart2.setData(data);

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });






        Call<ApiResponse> call2 = ApiClient.getApiClient().create(ApiInterface.class).performGraphPpgData(deviceID);
        call2.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if (response.code() == 200) {
                    Log.e(TAG, "onResponse:Body "+response.body() );

                    ArrayList<ApiResponse.data_graph_ppg> data_ppg =response.body().getData_ppg();


                    ArrayList<Entry> yVal=new ArrayList<>();
                    ArrayList<String> xAxisValues = new ArrayList<>();
                    try {

                        for (int i = 0; i < data_ppg.size(); i++) {

                            ApiResponse.data_graph_ppg data13 = data_ppg.get(i);

                            String ppgVal = data13.getPpg();
                            String date = data13.getTime_s();
                            float ppg = Float.valueOf(ppgVal);
                            yVal.add(new Entry(i,ppg));
                            xAxisValues.add(date);
                            Log.e(TAG, "onResponse: Temp " + data13.getPpg());

                        }
                    }catch (Exception e){

                    }


                    LineDataSet set1 =new LineDataSet(yVal,"Blood Oxygen Level");

                    set1.setLineWidth(2f);
                    set1.setColor(Color.BLUE);
                    set1.setHighLightColor(Color.WHITE);
                    set1.setDrawValues(true);
                    set1.setValueTextSize(10f);
                    set1.setValueTextColors(Collections.singletonList(0xE60022FF));



                    set1.setDrawCircles(true);
                    set1.setCircleColor(Color.BLUE);
                    set1.setCircleRadius(4f);
                    set1.setCircleHoleColor(Color.WHITE);
                    set1.setCircleHoleRadius(2f);
                    set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


                    XAxis xAxis;
                    xAxis= lineChart3.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawLimitLinesBehindData(true);
                    xAxis.setGridColor(Color.BLACK);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAxisMaximum(data_ppg.size());
                    xAxis.setLabelRotationAngle(90f);
                    lineChart3.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));


                    LimitLine line1 = new LimitLine(100f, "Upper Oxygen Level");
                    line1.setLineWidth(1.5f);
                    line1.setLineColor(Color.RED);
                    line1.enableDashedLine(10f, 5f, 0f);
                    line1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    line1.setTextSize(10f);


                    LimitLine line2 = new LimitLine(90f, "Lower Oxygen Level");
                    line2.setLineWidth(1.5f);
                    line2.setLineColor(Color.GREEN);
                    line2.enableDashedLine(10f, 5f, 0f);
                    line2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    line2.setTextSize(10f);


                    YAxis yAxis;
                    yAxis= lineChart3.getAxisLeft();
                    yAxis.setAxisMaximum(110f);
                    yAxis.setAxisMinimum(50f);
                    yAxis.removeAllLimitLines();
                    yAxis.addLimitLine(line1);
                    yAxis.addLimitLine(line2);
                    yAxis.setGridColor(Color.BLACK);
                    lineChart3.getAxisRight().setEnabled(false);

                    LineData data =new LineData(set1);


                    lineChart3.getDescription().setEnabled(false);
                    lineChart3.setDrawGridBackground(true);
                    lineChart3.setGridBackgroundColor(Color.WHITE);
                    lineChart3.setTouchEnabled(true);
                    lineChart3.setDragEnabled(true);
                    lineChart3.setScaleEnabled(true);
                    lineChart3.setPinchZoom(true);
                    lineChart3.setActivated(true);
                    lineChart3.setData(data);

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });



    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentDashboardBinding = null;
    }
}