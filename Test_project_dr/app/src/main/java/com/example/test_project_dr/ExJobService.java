package com.example.test_project_dr;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ExJobService extends JobService {

    public static final String TAG = "ExampleJobService";
    public boolean jobCancel = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(jobCancel){
                    Intent service = new Intent(getApplicationContext(), Music.class);
                    service.putExtra("state","OFF");
                    getApplicationContext().startService(service);
                    return;
                }

                NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                notificationHelper.getManager().notify(1, nb.build());


                Intent service = new Intent(getApplicationContext(), Music.class);
                service.putExtra("state","ON");
                getApplicationContext().startService(service);

                // Kết thúc công việc
                jobFinished(params, false);
            }
        }).start();
    }
    @Override
    public boolean onStopJob(JobParameters params) {


        Log.d(TAG,"Job cancelled before completion!");

        jobCancel = true;



        return true;
    }
}
