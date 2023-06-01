package com.example.healthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.healthmonitor.R;
import com.example.healthmonitor.apputil.AppConfig;
import com.example.healthmonitor.databinding.ActivitySettingsBinding;

public class Settings extends AppCompatActivity {

    private ActivitySettingsBinding settingsbinding;
    AppConfig appConfig = new AppConfig();
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        AppConfig.init(this);
        settingsbinding= DataBindingUtil.setContentView(this,R.layout.activity_settings);

        settingsbinding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TempUp=settingsbinding.tempUp.getText().toString();
                String TempDown=settingsbinding.tempDown.getText().toString();
                String HeartUp=settingsbinding.heartUp.getText().toString();
                String HeartDown=settingsbinding.heartDown.getText().toString();
                String BloodUp=settingsbinding.bloodUp.getText().toString();
                String BloodDown=settingsbinding.bloodDown.getText().toString();

                appConfig.saveTempUp(TempUp);
                appConfig.saveTempDown(TempDown);
                appConfig.saveHeartUp(HeartUp);
                appConfig.saveHeartDown(HeartDown);
                appConfig.savePpgUp(BloodUp);
                appConfig.savePpgDown(BloodDown);


            }
        });

        TextView Temp_up = settingsbinding.tempUp;
        String temp_UP=appConfig.getTempUp();
        Temp_up.setText(temp_UP);
        TextView Temp_down = settingsbinding.tempDown;
        String temp_Down =appConfig.getTempDown();
        Temp_down.setText(temp_Down);

        TextView Heart_up =settingsbinding.heartUp;
        String heart_Up =appConfig.getHeartUp();
        Heart_up.setText(heart_Up);
        TextView Heart_down =settingsbinding.heartDown;
        String heart_Down =appConfig.getHeartDown();
        Heart_down.setText(heart_Down);

        TextView Blood_up =settingsbinding.bloodUp;
        String blood_Up =appConfig.getPpgUp();
        Blood_up.setText(blood_Up);
        TextView Blood_down =settingsbinding.bloodDown;
        String blood_Down =appConfig.getPpgDown();
        Blood_down.setText(blood_Down);

settingsbinding.buttonSaveNum.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String Name=settingsbinding.conName.getText().toString();
        String Number=settingsbinding.conNum.getText().toString();

        if(!Name.isEmpty()&&!Number.isEmpty()){

            Intent intent=new Intent(Intent.ACTION_INSERT);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME,Name);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE,Number);
            appConfig.saveContact_Name(Name);
            appConfig.saveContact_Num(Number);


            if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
            }else{

                Toast.makeText(Settings.this,"There is no App that support this action",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(Settings.this, "Please Fill All the Field", Toast.LENGTH_SHORT).show();
        }
    }
});

        TextView Name = settingsbinding.conName;
        String Con_Name=appConfig.getContact_Name();
        Name.setText(Con_Name);
        TextView Number = settingsbinding.conNum;
        String Con_Number =appConfig.getContact_Num();
        Number.setText(Con_Number);


    }
}