package com.example.windowmanager;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Xin quyền hệ thống system aler window
 * Do có cờ Flag_not_focus nên ko thể tác động vào các text trong view. Cần phải xóa bỏ cờ khi click vào text
 */
public class Messge_Service extends Service implements View.OnClickListener, View.OnTouchListener {
    private WindowManager windowManager;
    //Bước 9 Thiết lập trạng thái và override phương thước onTouch
    int state;
    public  static  final  int TYPE_SMS = 0;
    public static final int TYPE_ICON = 0;

    //Bước 10. Thiết lập vị trí view và bắt sự kiện di chuyển icon trên màn hình
    private int proviousX;
    private int proviousY;

    // Bước 11 . Lấy vị trí nhấn xuống dùng trong onTouch
    private float mStartX;
    private float mstartY;


    // Bước 2 : Tạo class mGroupView để chứa các view icon. view sms đưa vào
    private myGroupView mViewIcons;

    // Bước 6 : Tạo viewgroup chứa viewsms và layoutparams để cài kích thước
    private myGroupView mViewSms;
    private WindowManager.LayoutParams mSmsViewParams;

    //Bước 4 tạo params để set cao rộng cho view icon đưa lên view group
    private WindowManager.LayoutParams  mIconViewParams;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Buoc 1
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initView();
        return START_STICKY; // hồi sinh sau khi chết
    }

    private void initView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE); // khởi tạo đối tượng mânger
        creatIconView();


        // Bước 7 : Create smsView
        creatSMSView();


        // Bước 5 thêm view vào viewGroup (cho icon hiện lên màn hình)
        showIcon();
        // Sau bước 5 này, khi chạy sẽ tạo ra 1 hình tròn có chữ H bên trong y như kiểu lúc thu nhỏ messenger


        // Bước 8. Tạo phương thức show sms. Tuy nhiên trước khi showw SMS cần remove ICON VIEW và ngược lại
        showSMS();
    }

    private void showSMS() {
        try {
            windowManager.removeView(mViewIcons);
        }catch (Exception e){
            e.printStackTrace();
        }
        windowManager.addView(mViewSms, mSmsViewParams);
        state = TYPE_SMS;
    }

    private void creatSMSView() {
        mViewSms = new myGroupView(this);// tạo viewGroup
        View view = View.inflate(this,R.layout.view_sms,mViewSms); // gán file xml chứa textview vào
        mViewSms.setOnTouchListener(this);
        view.findViewById(R.id.button_exit).setOnClickListener(this); // bắt sự kiện ấn vào nút exit trong sms

        // Bắt sự kiện click vào view
        view.findViewById(R.id.phone).setOnClickListener(this);
        view.findViewById(R.id.content_sms).setOnClickListener(this);

        // Khởi tạo thông số
        mSmsViewParams = new WindowManager.LayoutParams();
        mSmsViewParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mSmsViewParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mSmsViewParams.gravity = Gravity.CENTER; // để thuộc tính này đúng thì file view icon cần sửa linerLayout thành wrapcontent
        mSmsViewParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mSmsViewParams.format = PixelFormat.TRANSLUCENT; // tạo sự trong suốt cho viewGroup chứ textView (view icon)kia. Nếu ko có thì hiện ra sẽ là 1 hình tròn trên màn hình đen

        // tạo các cờ
        mSmsViewParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // vIEW có thể nằm ngoài khuôn màn hình
        mSmsViewParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // toán tử hợp tương tự +=, hợp cả 2 điều kiện
        mSmsViewParams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
    }

    private void showIcon() {
        try{
            windowManager.removeView(mViewSms);
        }catch (Exception e){
            e.printStackTrace();
        }
        windowManager.addView(mViewIcons, mIconViewParams);
        state = TYPE_ICON;
    }

    //Bước 3
    private void creatIconView() {
        mViewIcons = new myGroupView(this);// tạo viewGroup
        //LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = View.inflate(this,R.layout.view_icon,mViewIcons); // gán file xml chứa textview vào
        //v1 = inflater.inflate(R.layout.view_icon,null);
        TextView tvIcon = view.findViewById(R.id.textview_icon);
        tvIcon.setOnTouchListener(this);

        // Khởi tạo thông số
        mIconViewParams = new WindowManager.LayoutParams();
        mIconViewParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mIconViewParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mIconViewParams.gravity = Gravity.CENTER; // để thuộc tính này đúng thì file view icon cần sửa linerLayout thành wrapcontent
        mIconViewParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mIconViewParams.format = PixelFormat.TRANSLUCENT; // tạo sự trong suốt cho viewGroup chứ textView (view icon)kia. Nếu ko có thì hiện ra sẽ là 1 hình tròn trên màn hình đen

        // tạo các cờ
        mIconViewParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // vIEW có thể nằm ngoài khuôn màn hình
        mIconViewParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // toán tử hợp tương tự +=, hợp cả 2 điều kiện
        //mIconViewParams.flags

        // Bắt sự kiến nhấn vào text của viewIcon thì hiện smsview
        tvIcon.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_icon:
                showSMS();
                break;
            case R.id.button_exit:
                stopSelf(); // dừng server
                removeView(); // Xóa hết các view
                break;
            case R.id.content_sms:
                setFocus(true);
                break;
            case R.id.phone:
                setFocus(true); // hàm xóa bỏ cờ forcus để có thể click vào text
                v.requestFocus();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Lắng nghe sự kiện chạm màn hình
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: // chạm
                if(state  == TYPE_ICON){
                    proviousX = mIconViewParams.x;
                    proviousY = mIconViewParams.y; // Lấy tọa độ của view theo params
                }else{
                    proviousY = mSmsViewParams.y;
                    proviousX = mSmsViewParams.x;
                }
                mStartX = event.getRawX();
                mstartY = event.getRawY();
                break;


            case  MotionEvent.ACTION_MOVE:  // di chuyển tay
                double dentaX = event.getRawX() - mStartX; // vị trí tay hiện tại - vị trí tay cũ
                double dentaY = event.getRawY() - mstartY;

                if(state == TYPE_ICON){ // set lại X Y cho param
                    mIconViewParams.x = (int) (mStartX + dentaX);
                    mIconViewParams.y = (int) (mstartY + dentaY); // do có độ lệch nên vị trí view khác vj tri chạm
                    windowManager.updateViewLayout(mViewIcons, mIconViewParams);
                }
                break;
            case  MotionEvent.ACTION_OUTSIDE: // Kiểm tra có bấm ra ngoài hay không
                if(state == TYPE_SMS){ // ảnh hưởng với tác động của cờ OUT phía trên, nếu ko có cờ thì đoạn code này vô dụng
                    showIcon();
                }else{
                    showSMS();
                }
                break;
        }
        return false;
    }
    private void removeView(){
        try{
            windowManager.removeView(mViewSms);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setFocus(boolean focus){
        if(focus){
            mSmsViewParams.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }else{
            mSmsViewParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        windowManager.updateViewLayout(mViewSms, mSmsViewParams);
    }
}
