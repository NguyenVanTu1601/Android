package com.example.app_flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * Xin quyền camera do flash của camera
 */
public class MainActivity extends AppCompatActivity {
    private boolean check = true;
    private boolean flash = true;
    private Camera camera;
    private Camera.Parameters parameters;
    Button btnFlash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFlash = findViewById(R.id.ButtonFlash);
        check = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH); // kiểm tra thiết bị hỗ trợ false ko
        if(check == false){
            Toasty.success(this, "Thiết bị không hỗ trợ flash!",
                    Toast.LENGTH_SHORT, true).show();

        }else{
            getCamera();
            btnFlash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flash == true){
                        btnFlash.setText("OFF");
                    }else{
                        btnFlash.setText("ON");
                    }
                    OnFlash();
                }
            });
        }

    }
    private void getCamera(){
        if(camera == null && parameters == null){
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }

    private void OnFlash(){
        if(flash == true){
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            flash = false;
        }else{
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            flash = true;
        }
    }

}
