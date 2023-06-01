package com.example.healthmonitor.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.healthmonitor.R;
import com.example.healthmonitor.apputil.AppConfig;
import com.example.healthmonitor.databinding.ActivityEcgBinding;
import com.example.healthmonitor.models.ApiResponse;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EcgActivity extends AppCompatActivity {

    private static final String TAG = "EcgActivity";
    private ActivityEcgBinding activityEcgBinding;
    private LineChart lineChart5;
    AppConfig appConfig = new AppConfig();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg);

  this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        activityEcgBinding= DataBindingUtil.setContentView(this,R.layout.activity_ecg);

        AppConfig.init(this);
        String DeviceID=appConfig.getDeviceID();

        lineChart5 = activityEcgBinding.ecgChart;

        Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performGraphECGData(DeviceID+"_ecg");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if (response.code() == 200) {
                    Log.e(TAG, "onResponse:Body "+response.body() );

                    ArrayList<ApiResponse.data_graph_ecg> data_ecg =response.body().getData_ecg();


                    ArrayList<Entry> yVal=new ArrayList<>();

                    try {

                        for (int i = 0; i < data_ecg.size(); i++) {

                            ApiResponse.data_graph_ecg data11 = data_ecg.get(i);

                            String ecgVal = data11.getecg();
                            float ecg = Float.valueOf(ecgVal);
                            yVal.add(new Entry(i,ecg));

                            Log.e(TAG, "onResponse: Temp " + data11.getecg());

                        }
                    }catch (Exception e){

                    }


                    LineDataSet set1 =new LineDataSet(yVal,"ECG");

                    set1.setLineWidth(1f);
                    set1.setColor(Color.BLUE);
                    set1.setHighLightColor(Color.WHITE);
                    set1.setDrawValues(false);
                    set1.setValueTextColors(Collections.singletonList(0xE60022FF));



                    set1.setDrawCircles(true);
                    set1.setDrawCircles(false);
                    set1.setMode(LineDataSet.Mode.LINEAR);



                    XAxis xAxis;
                    xAxis= lineChart5.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGridColor(Color.BLACK);



                    YAxis yAxis;
                    yAxis= lineChart5.getAxisLeft();
                    yAxis.removeAllLimitLines();
                    yAxis.setGridColor(Color.BLACK);
                    lineChart5.getAxisRight().setEnabled(false);
                    LineData data =new LineData(set1);


                    lineChart5.getDescription().setEnabled(false);
                    lineChart5.setDrawGridBackground(false);
                    lineChart5.setTouchEnabled(true);
                    lineChart5.setDragEnabled(false);
                    lineChart5.setScaleEnabled(true);
                    lineChart5.setPinchZoom(true);
                    lineChart5.setActivated(true);
                    lineChart5.setData(data);



                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });


    }
}