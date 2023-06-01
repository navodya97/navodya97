package com.example.healthmonitor.apputil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.healthmonitor.R;


public class AppConfig {


    private static Context context;
    private static SharedPreferences sharedPreferences;



    public static void init(Context ctx){
        context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public AppConfig() {

    }


    public boolean isUserLogin(){

        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login),false);
    }

    public void updateUserLoginStatus(boolean status){

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login),status);
        editor.apply();

    }


    public void saveNameofUser(String name){

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user),name);
        editor.apply();

    }


    public String getNameofUser(){

        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user),"Unknown");
    }


    public void saveDeviceID(String name){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_device_id),name);
        editor.apply();

    }

    public String getDeviceID(){
        return sharedPreferences.getString(context.getString(R.string.pref_device_id),"Unknown");
    }


    public void saveTempUp(String saveTempUp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TempUp", saveTempUp);
        editor.apply();
    }
    public String getTempUp(){
        return sharedPreferences.getString("TempUp","00");
    }

    public void saveTempDown(String saveTempDown) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TempDown", saveTempDown);
        editor.apply();
    }
    public String getTempDown(){
        return sharedPreferences.getString("TempDown","00");
    }

    public void saveHeartUp(String saveHeartUp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HeartUp", saveHeartUp);
        editor.apply();
    }
    public String getHeartUp(){
        return sharedPreferences.getString("HeartUp","00");
    }

    public void saveHeartDown(String saveHeartDown) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HeartDown", saveHeartDown);
        editor.apply();
    }
    public String getHeartDown(){
        return sharedPreferences.getString("HeartDown","00");
    }

    public void savePpgUp(String savePpgUp) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PpgUp", savePpgUp);
        editor.apply();
    }
    public String getPpgUp(){
        return sharedPreferences.getString("PpgUp","00");
    }

    public void savePpgDown(String savePpgDown) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PpgDown", savePpgDown);
        editor.apply();
    }
    public String getPpgDown(){
        return sharedPreferences.getString("PpgDown","00");
    }

    public void saveContact_Name(String saveContact) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Contact", saveContact);
        editor.apply();
    }
    public String getContact_Name(){
        return sharedPreferences.getString("Contact","00");
    }


    public void saveContact_Num(String saveContact_Num) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Contact_Num", saveContact_Num);
        editor.apply();
    }
    public String getContact_Num(){
        return sharedPreferences.getString("Contact_Num","00");
    }
}
