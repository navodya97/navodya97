package com.example.healthmonitor.activities.ui.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthmonitor.R;
import com.example.healthmonitor.activities.EcgActivity;
import com.example.healthmonitor.activities.MainActivity;
import com.example.healthmonitor.activities.Settings;
import com.example.healthmonitor.apputil.AppConfig;
import com.example.healthmonitor.databinding.FragmentHomeBinding;
import com.example.healthmonitor.models.ApiResponse;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding homebinding;
    AppConfig appConfig = new AppConfig();
    String Temp;
    String Heart_rate;
    String PPG;
    String Battery_level;
    ImageView imageView ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        String fullName = getActivity().getIntent().getStringExtra("fullName");
        String deviceID = getActivity().getIntent().getStringExtra("deviceID");



        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){

                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performProfData(deviceID);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if (response.code() == 200){

                            try {

                            Temp = response.body().getTemp();
                            Heart_rate = response.body().getHeart_rate();
                            PPG = response.body().getPPG();
                            Battery_level= response.body().getBattery_level();




                                final TextView textView1 = homebinding.Temp;
                                textView1.setText(Temp);
                                int temp = Integer.valueOf(Temp);
                                final ProgressBar progressBar1 = homebinding.progressIndicator1;
                                progressBar1.setMax(70);
                                progressBar1.setProgress(temp);

                                final TextView textView3 = homebinding.Ppg;
                                textView3.setText(PPG);
                                int ppg = Integer.valueOf(PPG);
                                final ProgressBar progressBar2 = homebinding.progressIndicator2;
                                progressBar2.setMax(100);
                                progressBar2.setProgress(ppg);

                                final TextView textView2 = homebinding.HeartRate;
                                textView2.setText(Heart_rate);

                                final TextView textView4 = homebinding.textViewBattery;
                                textView4.setText(Battery_level);
                                int battery = Integer.valueOf(Battery_level);
                                final ProgressBar progressBar3 =homebinding.progressBarBattery;
                                progressBar3.setProgress(battery);

                            }
                            catch (Exception e){}


                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                    }
                });



            }

        }, 0, 50000);





        homebinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = homebinding.getRoot();
        final TextView textView = homebinding.texthome;
        textView.setText(fullName);
        return root;

    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppConfig.init(getContext());


        homebinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                appConfig.updateUserLoginStatus(false);

            }
        });

        homebinding.buttonEcg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getActivity(), EcgActivity.class);
                startActivity(intent2);


            }
        });



        homebinding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), Settings.class);
                startActivity(intent1);

            }
        });

        homebinding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String Num=appConfig.getContact_Num();
             String s="tel:"+Num;
             Intent intent=new Intent(Intent.ACTION_CALL);
             intent.setData(Uri.parse(s));
             startActivity(intent);

            }
        });


        imageView = homebinding.imageheart;
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.heart_beat_anim);
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                imageView.startAnimation(animation);
            }
        }, 0, 2000);
        
    }






    @Override
    public void onDestroyView(){
        super.onDestroyView();
        homebinding = null;
    }


}