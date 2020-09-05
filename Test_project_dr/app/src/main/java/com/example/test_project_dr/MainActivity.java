package com.example.test_project_dr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Cancel(View view) {
        Toast.makeText(this, "Job cancel!!!", Toast.LENGTH_SHORT).show();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG,"Job cancelled");
    }

    public void Start(View view) {


        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,8);
        c.set(Calendar.MINUTE,30);
        c.set(Calendar.SECOND,0);

        Calendar a = Calendar.getInstance();

        ComponentName componentName = new ComponentName(this, ExJobService.class);

        long time = c.getTimeInMillis() - a.getTimeInMillis();
        Toast.makeText(this, "Job start!!!" + time, Toast.LENGTH_SHORT).show();

            JobInfo info = new JobInfo.Builder(123,componentName)
                    .setMinimumLatency(5000)
                    .build();

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.schedule(info);


    }
}
