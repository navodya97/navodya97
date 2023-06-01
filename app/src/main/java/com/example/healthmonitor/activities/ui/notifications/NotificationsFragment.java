package com.example.healthmonitor.activities.ui.notifications;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthmonitor.databinding.FragmentNotificationsBinding;
import com.example.healthmonitor.models.ApiResponse;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationsFragment extends Fragment {


    private FragmentNotificationsBinding binding;
    String Max_temp,Max_heart,Max_ppg;
    String Max_time_temp,Max_time_heart,Max_time_ppg;
    String Min_temp,Min_heart,Min_ppg;
    String MIn_time_temp,MIn_time_heart,MIn_time_ppg;
    private static final String TAG = "DashboardFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String deviceID = getActivity().getIntent().getStringExtra("deviceID");


     binding.button1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String limit_temp= binding.LimitValue.getText().toString();

             Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performMaxMinTemp(deviceID,limit_temp);
             call.enqueue(new Callback<ApiResponse>() {
                 @Override
                 public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                     if (response.code() == 200){

                         Log.e(TAG, "onResponse:Body " + response.body());

                   Max_temp=response.body().getMax_temp();
                   final TextView textView1 = binding.maxValue;
                   textView1.setText(Max_temp);
                         Log.e(TAG, "onResponse:temp " + response.body().getMax_temp());

                   Max_time_temp=response.body().getMax_time_temp();
                   final TextView textView2=binding.maxDate;
                   textView2.setText(Max_time_temp);
                         Log.e(TAG, "onResponse:date " + response.body().getMax_time_temp());

                   Min_temp=response.body().getMin_temp();
                   final  TextView textView3= binding.minValue;
                   textView3.setText(Min_temp);
                         Log.e(TAG, "onResponse:min_temp " + response.body().getMin_temp());

                   MIn_time_temp=response.body().getMin_time_temp();
                   final TextView textView4=binding.minDate;
                   textView4.setText(MIn_time_temp);
                         Log.e(TAG, "onResponse:date " + response.body().getMin_time_temp());
                     }
                 }

                 @Override
                 public void onFailure(Call<ApiResponse> call, Throwable t) {

                 }
             });



         }
     });



        binding.buttonHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String limit_heart= binding.LimitValueHeart.getText().toString();

                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performMaxMinHeart(deviceID,limit_heart);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if (response.code() == 200){



                            Max_heart=response.body().getMax_heart();
                            final TextView textView1 = binding.maxValueHeart;
                            textView1.setText(Max_heart);


                            Max_time_heart=response.body().getMax_time_heart();
                            final TextView textView2=binding.maxDateHeart;
                            textView2.setText(Max_time_heart);


                            Min_heart=response.body().getMin_heart();
                            final  TextView textView3= binding.minValueHeart;
                            textView3.setText(Min_heart);


                            MIn_time_heart=response.body().getMin_time_heart();
                            final TextView textView4=binding.minDateHeart;
                            textView4.setText(MIn_time_heart);

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                    }
                });



            }
        });


        binding.buttonPpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String limit_ppg= binding.LimitValuePpg.getText().toString();

                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performMaxMinPpg(deviceID,limit_ppg);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if (response.code() == 200){



                            Max_ppg=response.body().getMax_ppg();
                            final TextView textView1 = binding.maxValuePpg;
                            textView1.setText(Max_ppg);

                            Max_time_ppg=response.body().getMax_time_ppg();
                            final TextView textView2=binding.maxDatePpg;
                            textView2.setText(Max_time_ppg);


                            Min_ppg=response.body().getMin_ppg();
                            final  TextView textView3= binding.minValuePpg;
                            textView3.setText(Min_ppg);


                            MIn_time_ppg=response.body().getMin_time_ppg();
                            final TextView textView4=binding.minDatePpg;
                            textView4.setText(MIn_time_ppg);

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                    }
                });



            }
        });

        
    }




        @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}