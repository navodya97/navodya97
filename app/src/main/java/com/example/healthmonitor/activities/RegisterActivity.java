package com.example.healthmonitor.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.healthmonitor.R;
import com.example.healthmonitor.databinding.ActivityRegisterBinding;
import com.example.healthmonitor.models.ApiResponse;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
private ActivityRegisterBinding registerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        registerBinding.bnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    performSignUp();
                    registerBinding.showProgress.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this,"Please Fill All the Fields",
                            Toast.LENGTH_LONG).show();
                }



            }
        });



    }
private void performSignUp(){


            String deviceId = registerBinding.UserId.getText().toString();
            String password = registerBinding.Password.getText().toString();
            String userFullName = registerBinding.UserFullName.getText().toString();
            String userEmail = registerBinding.UserEmail.getText().toString();
            String userAge = registerBinding.UserAge.getText().toString();
            int Age = Integer.parseInt(userAge);
            String gender = ((RadioButton) findViewById(registerBinding.radioGender.getCheckedRadioButtonId())).getText().toString();


    Call<ApiResponse> call= ApiClient.getApiClient().create(ApiInterface.class).performUserSignIn(deviceId,password,userFullName,userEmail,
                               Age,gender);

   call.enqueue(new Callback<ApiResponse>() {
       @Override
       public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
if(response.code()==200){

if(response.body().getStatus().equals("ok")){

    if (response.body().getResultCode()==1){
        Toast.makeText(RegisterActivity.this,"Registration Successfull. Now you can Login",
        Toast.LENGTH_LONG).show();
        onBackPressed();
        finish();

    }else {
        displayUserInfo("User Already Exists...");
        registerBinding.Password.setText("");

    }

}else {
    displayUserInfo("Something Went Wrong....");
    registerBinding.Password.setText("");

}

}else {
displayUserInfo("Something Went Wrong....");
registerBinding.Password.setText("");

}
       }

       @Override
       public void onFailure(Call<ApiResponse> call, Throwable t) {

       }
   });

}
private void displayUserInfo(String message){
    Snackbar.make(registerBinding.myCoordinatorLayout,message,Snackbar.LENGTH_SHORT).show();
    registerBinding.Password.setText("");
    registerBinding.showProgress.setVisibility(View.INVISIBLE);


}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}