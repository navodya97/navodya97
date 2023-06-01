package com.example.healthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.healthmonitor.R;
import com.example.healthmonitor.apputil.AppConfig;
import com.example.healthmonitor.databinding.ActivityMainBinding;
import com.example.healthmonitor.models.ApiResponse;
import com.example.healthmonitor.models.HealthService;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private boolean isRememberUserLogin = false;
    AppConfig appConfig = new AppConfig();
    private String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        startService(new Intent(getApplicationContext(), HealthService.class));

        AppConfig.init(this);

        if(appConfig.isUserLogin()){
            String fullName=appConfig.getNameofUser();
            String DeviceID=appConfig.getDeviceID();
            Intent intent= new Intent(MainActivity.this,BottomNavigationActivity.class);
            intent.putExtra("fullName",fullName);
            intent.putExtra("deviceID",DeviceID);
            startActivity(intent);
            finish();


        }



        mainBinding.textViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });



        mainBinding.bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preformLogin();
                mainBinding.showProgress.setVisibility(View.VISIBLE);
            }
        });




    }



    private void preformLogin()
    {

        String deviceId=mainBinding.UserId.getText().toString();
        String password=mainBinding.Password.getText().toString();
        appConfig.saveDeviceID(deviceId);


        Call<ApiResponse> call= ApiClient.getApiClient().create(ApiInterface.class).performUserLogin(deviceId,password);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if(response.code()==200){

                    if(response.body().getStatus().equals("ok")){

                        if (response.body().getResultCode()==1){

                          String fullName=response.body().getFullName();

                          if(isRememberUserLogin){


                              appConfig.updateUserLoginStatus(true);
                              appConfig.saveNameofUser(fullName);

                          }

                            Intent intent= new Intent(MainActivity.this,BottomNavigationActivity.class);
                            intent.putExtra("fullName",fullName);
                            intent.putExtra("deviceID",deviceId);
                            startActivity(intent);
                            finish();

                        }else {
                            displayUserInfo("Logging Failed.....");
                            mainBinding.Password.setText("");

                        }

                    }else {
                        displayUserInfo("Something Went Wrong....");
                        mainBinding.Password.setText("");

                    }

                }else {
                    displayUserInfo("Something Went Wrong....");
                    mainBinding.Password.setText("");

                }


            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });




    }

    private void displayUserInfo(String message){
        Snackbar.make(mainBinding.myCoordinatorLayout,message,Snackbar.LENGTH_SHORT).show();
        mainBinding.Password.setText("");
        mainBinding.showProgress.setVisibility(View.INVISIBLE);


    }

public void checkBoxClick (View view){
isRememberUserLogin=((CheckBox)view).isChecked();

}




}

