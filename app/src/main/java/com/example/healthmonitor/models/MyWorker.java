package com.example.healthmonitor.models;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    private final Context context;
    private String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {

        super(context, params);
        this.context = context;

    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            Log.d(TAG, "doWork called for: " + this.getId());
            Log.d(TAG, "Service Running: " + HealthService.isServiceRunning);
            if (!HealthService.isServiceRunning) {
                Log.w(TAG, "starting service from doWork");
                Intent intent = new Intent(this.context, HealthService.class);
                ContextCompat.startForegroundService(context, intent);
            }

        } catch (Exception e){}

        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.d(TAG, "onStopped called for: " + this.getId());
        super.onStopped();
    }
}
