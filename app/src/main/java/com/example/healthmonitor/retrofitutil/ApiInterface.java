package com.example.healthmonitor.retrofitutil;

import com.example.healthmonitor.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse>performUserSignIn(@Field("device_id") String userName,@Field("password") String password,@Field("full_name") String full_Name,@Field("email") String email,@Field("age") int age,@Field("gender") String gender);

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse>performUserLogin(@Field("device_id") String userName,@Field("password") String password);

    @FormUrlEncoded
    @POST("prof_data.php")
    Call<ApiResponse>performProfData(@Field("device_id") String deviceID);

    @FormUrlEncoded
    @POST("graph_temp.php")
    Call<ApiResponse>performGraphTempData(@Field("device_id") String deviceID);

    @FormUrlEncoded
    @POST("graph_heartrate.php")
    Call<ApiResponse>performGraphHeartRateData(@Field("device_id") String deviceID);

    @FormUrlEncoded
    @POST("graph_ecg.php")
    Call<ApiResponse>performGraphECGData(@Field("device_id") String deviceID);

    @FormUrlEncoded
    @POST("graph_ppg.php")
    Call<ApiResponse>performGraphPpgData(@Field("device_id") String deviceID);

    @FormUrlEncoded
    @POST("max_min_temp.php")
    Call<ApiResponse>performMaxMinTemp(@Field("device_id") String userName,@Field("limit_temp") String limit_temp);

    @FormUrlEncoded
    @POST("max_min_heart.php")
    Call<ApiResponse>performMaxMinHeart(@Field("device_id") String userName,@Field("limit_temp") String limit_temp);

    @FormUrlEncoded
    @POST("max_min_ppg.php")
    Call<ApiResponse>performMaxMinPpg(@Field("device_id") String userName,@Field("limit_temp") String limit_temp);



}

