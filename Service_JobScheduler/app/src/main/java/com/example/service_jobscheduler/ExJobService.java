package com.example.service_jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.nfc.Tag;
import android.util.Log;

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
                for(int i = 0; i < 10; i++){
                    Log.d(TAG, "run : " + i);
                    if(jobCancel){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Finished");
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
