package com.example.healthmonitor.models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.healthmonitor.R;
import com.example.healthmonitor.apputil.AppConfig;
import com.example.healthmonitor.retrofitutil.ApiClient;
import com.example.healthmonitor.retrofitutil.ApiInterface;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthService extends Service {

    private String TAG = "HealthService";
    public static boolean isServiceRunning;
    AppConfig appConfig = new AppConfig();
    String Temp,Heart_rate,PPG;

    public HealthService() {
        Log.d(TAG, "constructor called");
        isServiceRunning = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
        isServiceRunning = true;


    }



    public int onStartCommand(Intent intent, int flags, int startId) {
        AppConfig.init(this);
        String DeviceID=appConfig.getDeviceID();


        try {

        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){

                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performProfData(DeviceID);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if (response.code() == 200){




                            Temp = response.body().getTemp();
                            Heart_rate = response.body().getHeart_rate();
                            PPG = response.body().getPPG();

                            float body_temp =Float.parseFloat(Temp);

                            String TempUP=appConfig.getTempUp();
                            float Temp_up =Float.parseFloat(TempUP);
                            String TempDown=appConfig.getTempDown();
                            float Temp_down = Float.parseFloat(TempDown);

                            int heart_rate =Integer.parseInt(Heart_rate);

                            String HeartUP=appConfig.getHeartUp();
                            int Heart_up = Integer.parseInt(HeartUP);
                            String HeartDown=appConfig.getHeartDown();
                            int Heart_down = Integer.parseInt(HeartDown);

                            int ppg =Integer.parseInt(PPG);

                            String PpgUP=appConfig.getPpgUp();
                            int Ppg_up = Integer.parseInt(PpgUP);
                            String PpgDown=appConfig.getPpgDown();
                            int Ppg_down = Integer.parseInt(PpgDown);

                            if(Temp_up!=00) {
                                if (Temp_up < body_temp) {
                                    Noti_temp_up();
                                }
                                if (Temp_down > body_temp) {
                                    Noti_temp_down();
                                }
                            }

                            if(Heart_up!=00) {
                       if (Heart_up < heart_rate) {
                           Noti_heart_up();
                       }
                       if (Heart_down > heart_rate) {
                           Noti_heart_down();
                       }
                   }

                            if(Ppg_up!=00) {

                                if (Ppg_up < ppg) {
                                    Noti_ppg_up();
                                }
                                if (Ppg_down > ppg) {
                                    Noti_ppg_down();
                                }
                            }




                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                    }
                });
                
            }

        }, 0, 50000);

        }
        catch (Exception e){}

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy() {

            Log.w(TAG, "onDestroy called");
        try {

            isServiceRunning = false;
            Intent broadcastIntent = new Intent(this, MyReceiver.class);
            sendBroadcast(broadcastIntent);
            super.onDestroy();

    } catch (Exception e){}
        
    }


    private void Noti_temp_up(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Room Temperature";
            String description = "Include all the notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("Body Temp", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent fullScreenIntent = new Intent();
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, "Body Temp")
                .setContentTitle("[ Health Monitor Alert ]")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentText("Body Temperature is High at "+Temp)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder1.build());
    }


    private void Noti_temp_down(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Body Temperature";
            String description = "Include all the notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("Body Temp", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent fullScreenIntent = new Intent();
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, "Body Temp")
                .setContentTitle("[ Health Monitor Alert ]")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentText("Body Temperature is Low at "+Temp)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder1.build());
    }



    private void Noti_heart_up(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Heart Rate";
            String description = "Include all the notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("Heart Rate", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent fullScreenIntent = new Intent();
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "Heart Rate")
                .setContentTitle("[ Health Monitor Alert ]")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentText("Heart Rate is High at "+Heart_rate)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(2, builder2.build());
    }


    private void Noti_heart_down(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Heart Rate";
            String description = "Include all the notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("Heart Rate", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent fullScreenIntent = new Intent();
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "Heart Rate")
                .setContentTitle("[ Health Monitor Alert ]")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentText("Heart Rate is Low at "+Heart_rate)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(2, builder2.build());
    }



    private void Noti_ppg_up(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Blood Oxygen Level";
            String description = "Include all the notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("Blood Oxygen Level", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent fullScreenIntent = new Intent();
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(this, "Blood Oxygen Level")
                .setContentTitle("[ Health Monitor Alert ]")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentText("Blood Oxygen Level is High at "+PPG)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(3, builder3.build());
    }


    private void Noti_ppg_down(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Blood Oxygen Level";
            String description = "Include all the notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("Blood Oxygen Level", name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent fullScreenIntent = new Intent();
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(this, "Blood Oxygen Level")
                .setContentTitle("[ Health Monitor Alert ]")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentText("Blood Oxygen Level is Low at "+PPG)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(3, builder3.build());
    }

}