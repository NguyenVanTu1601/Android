package com.example.gesture_lac_thiet_bi;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    float SHAKE_THRESHOLD_GRAVITY = 2.0f; // mức tối thiểu để xem có lắc ko
    int SHAKE_SLOP_TIME_MS = 500; //Khoảng cách giữa 2 lần lắc . Nếu < 0.5 thì ko trả lại giá trị lắc()milis
    int SHAKE_COUNT_RESET_TIME_MS = 2000;// Sau khoảng thời gian này mà ko lắm thì trả về kq


    // Đoạn tạo interface này chưa hiểu @@
    public OnShakeListener listener;
    public long mShakeTimestamp; // thời gian lắc
    private int mShakeCount; // số lần lắc

    // Function bắt số lần lắm
    public void setOnShakeListener(OnShakeListener onShakeListener){
        this.listener = onShakeListener;
    }


    // Tạo interface
    public  interface OnShakeListener{
        public void onShake(int count);
    }


    /*
    Giair thích thử : Class ShakeDetector có 1 interface là onShakeListener có phương thức onShake cần overrie lại.
    Tạo trong ShakeDetector một biến OnShreListener có tên là listener.... chả hiểu mẹ gì
     */
    @Override
    public void onSensorChanged(SensorEvent event) { // khi nhận được cảm biến nào mới
        if(listener != null){
            // Lấy giá trị lắm theo 3 chiều
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //kẾT QUẢ đã Bỏ qua lực hút trái đất
            float gX = x/ SensorManager.GRAVITY_EARTH;
            float gY = y/ SensorManager.GRAVITY_EARTH;
            float gZ = z/ SensorManager.GRAVITY_EARTH;

            // tính
            float gForve = (float) Math.sqrt(gX*gX + gY*gY+gZ*gZ);
            if(gForve >= SHAKE_THRESHOLD_GRAVITY){
                long now = System.currentTimeMillis(); // lấy thời gian lăcs
                if(mShakeTimestamp + SHAKE_SLOP_TIME_MS > now){
                    return;
                }
                if(mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now){
                    mShakeCount = 0;
                }
                mShakeTimestamp = (long) now; // gán lại thời gian để tiếp tục lắm
                mShakeCount++;

                listener.onShake(mShakeCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { // Khi xác định được cảm biến và giá trị thay đổi

    }
}
