package com.example.healthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.healthmonitor.R;
import com.example.healthmonitor.databinding.ActivityBottomNavigationBinding;
import com.example.healthmonitor.models.HealthService;
import com.example.healthmonitor.models.MyWorker;

import java.util.concurrent.TimeUnit;

public class BottomNavigationActivity extends AppCompatActivity {

    private static final String TAG = "BottomNavigation";
    private ActivityBottomNavigationBinding notificationbinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationbinding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(notificationbinding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);
        NavigationUI.setupWithNavController(notificationbinding.navView, navController);

        startServiceViaWorker();


    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy called");
        stopService();
        super.onDestroy();
    }


    public void stopService() {
        Log.d(TAG, "stopService called");
        if (HealthService.isServiceRunning) {
            Intent serviceIntent = new Intent(this, HealthService.class);
            stopService(serviceIntent);
        }
    }

    public void startServiceViaWorker() {
        Log.d(TAG, "startServiceViaWorker called");
        String UNIQUE_WORK_NAME = "StartMyServiceViaWorker";
        WorkManager workManager = WorkManager.getInstance(this);

        // As per Documentation: The minimum repeat interval that can be defined is 15 minutes
        // (same as the JobScheduler API), but in practice 15 doesn't work. Using 16 here
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(
                        MyWorker.class,
                        16,
                        TimeUnit.MINUTES)
                        .build();

        // to schedule a unique work, no matter how many times app is opened i.e. startServiceViaWorker gets called
        // do check for AutoStart permission
        workManager.enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, request);

    }


}