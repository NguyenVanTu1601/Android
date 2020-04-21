package com.example.pikachu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class NewGame extends AppCompatActivity {
    // Khởi tạo

    Thread thread;                                          // Luồng đếm thời gian
    TableLayout tableLayout;                                // TableLayout dùng hiển thị ảnh
    ProgressBar progressBarTime;                            // ProgessBar đếm ngược thời gian
    TextView txtScore;                                      // Textview chứa điểm
    Button btnChoiMoi, btnPause, btnThoat;               // Các button chức năng
    ArrayList<String> arrayIcon;                            // Mảng icon lấy từ string.xml
    int[][] arraySetIcon;                                   // Mảng để lấy vị trí các icon lên table
    private MyPoint point1, point2;                         // Point lưu giữ vị trí click theo tọa độ i j
    private Handler mHandler;                               // Handler dùng truyền thông điệp messages
    public static final int MESSAGE_COUNT_DOUNT = 1000;     // Điểm đến của messages
    public static final int MESSAGE_FINAL = 1001;           // Điểm đến thứ 2 của messages
    private TableRow tbR1, tbR2;                            // Dùng để lấy vị trí ô click, xóa, đổi màu...
    private int ITEM = 66;                                  // Số lượng icon có trong table Layout, hết icon là thắng
    private int Score = 0;                                  // Số điểm giành được, chọn đúng +10 điểm
    private boolean isRunOfThread = true;
    private int time;
    private int clickPause = 0;
    public /*static final */int TIME = 150;                      // Thời gian chạy của progessBar
    MediaPlayer soundBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Anhxa();
        // Tạo mảng icon lấy các icon từ string.xml
        String[] mangIcon = getResources().getStringArray(R.array.List_icon);
        arrayIcon = new ArrayList<>(Arrays.asList(mangIcon));

        // Tạo mảng 2 chiều đại diện cho các icon
        arraySetIcon = new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,2,3,4,5,6,7,8,9,10,11,0},
                {0,1,2,3,4,5,6,7,8,9,10,11,0},
                {0,1,2,3,4,5,6,7,8,9,10,11,0},
                {0,1,2,3,4,5,6,7,8,9,10,11,0},
                {0,1,2,3,4,5,6,7,8,9,10,11,0},
                {0,1,2,3,4,5,6,7,8,9,10,11,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0}
        };

        // Các hàm sử dụng
        SoundBackGround();
        SetIcon();
        initHandler();
        CountDownTime();
        ThoatGame();
    }


    // Ánh xạ các thành phần
    private void Anhxa(){
        tableLayout = (TableLayout) findViewById(R.id.tableIcon);
        progressBarTime = (ProgressBar) findViewById(R.id.progressBar);
        txtScore = (TextView) findViewById(R.id.textViewScore);
        btnChoiMoi = (Button) findViewById(R.id.buttonNew);
        btnPause = findViewById(R.id.buttonPause);
        btnThoat = findViewById(R.id.buttonThoat);
    }


    // Nhận message truyền đến từ hendler, kiểm tra thắng thua
    private void initHandler() {
            mHandler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what == MESSAGE_COUNT_DOUNT){ // Kiểm tra cùng nơi đến
                        int x = Integer.parseInt(String.valueOf(msg.arg1));
                        progressBarTime.setProgress(x);
                        if(x == 0){
                            if(ITEM == 0){
                                Toast.makeText(NewGame.this, "Bạn đã thắng!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // Hiện dialog thông báo thua và âm thanh
                                SoundLose();
                                DialogLose();

                            }
                        }

                        else{
                            if(ITEM == 0){
                                Toast.makeText(NewGame.this, "Bạn đã thắng", Toast.LENGTH_SHORT).show();
                                isRunOfThread = false;
                            }
                        }
                    }

                }
            };
    }


    // Khởi tạo các icon và sự kiện click icon
    private void SetIcon(){
        for(int i = 1; i <= 6; i++){
            final TableRow tableRow = new TableRow(this);
            for(int j = 1; j <= 11; j++){
                final ImageView imageView = new ImageView(this);

                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,43,r.getDisplayMetrics());

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px,px); // tạo từng ô có kt 350*300
                imageView.setLayoutParams(layoutParams); // thêm ô vừa tạo vào imagview

                // Thêm ảnh vao imageView
                final  int vitri = arraySetIcon[i][j]; // biến cục bộ, final. Nếu toàn cục sẽ chỉ lưu vị trí cuối
                // Biến vị trí dùng để lấy icon từ mảng arrayIcon
                int idHinh = getResources().getIdentifier(arrayIcon.get(vitri),"drawable",getPackageName());
                imageView.setImageResource(idHinh);

                // Add ImageView vào TableRow
                tableRow.addView(imageView);

                // Lấy tọa độ i , j đưa vào bắt sự kiện
                final int X = i;
                final int Y = j;
                // Bắt sự kiện click vào hình và xử lý

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(NewGame.this, arrayIcon.get(vitri), Toast.LENGTH_SHORT).show();
                        // Nếu chưa click
                        SoundClick();
                        if(point1 == null){
                            point1 = new MyPoint(X,Y);                                   // Lấy tọa độ ô vừa click theo ij ứng với mảng arraySetIcon
                            tableRow.getVirtualChildAt(Y-1).setBackgroundColor(Color.RED); // Đổi màu nền thành đỏ
                            tbR1 = tableRow;                                             // gán tableRow
                        }

                        // Nếu đã click vào 1 ô trước đó
                        else{
                            point2 = new MyPoint(X,Y);
                            tbR2 = tableRow;

                            // Kiểm tra 2 ô có trùng nhau và có thỏa mãn đường đi không
                            if ((arraySetIcon[point1.getX()][point1.getY()] == arraySetIcon[point2.getX()][point2.getY()]) // 2 ô cùng gtri
                                 && ((point1.getX() != point2.getX()) || (point1.getY() != point2.getY()))                 // 2 ô khác nhau
                                 && (arraySetIcon[point1.getX()][point1.getY()] != 0) ) {                                     // giá trị khác 0
                                //Toast.makeText(NewGame.this,arraySetIcon[point1.getX()][point1.getY()] + " "
                                 //       + arraySetIcon[point2.getX()][point2.getY()] , Toast.LENGTH_SHORT).show();
                                int [][] check = new int[8][13];
                                int [][] dem = new int[8][13];
                                arraySetIcon[point1.getX()][point1.getY()] = 0;
                                arraySetIcon[point2.getX()][point2.getY()] = 0;

                                if(BFS(point1,point2,arraySetIcon,check,dem) == 1){
                                    ITEM-=2;
                                    SoundClickTrue();
                                    tbR1.getVirtualChildAt(point1.getY()-1).setVisibility(View.INVISIBLE);
                                    tbR2.getVirtualChildAt(point2.getY()-1).setVisibility(View.INVISIBLE);
                                    Score += 10;
                                    SoundClickTrue();
                                    txtScore.setText("Score : " + Score);
                                    //Toast.makeText(NewGame.this, " bằng nhau", Toast.LENGTH_SHORT).show();
                                }else{
                                    tbR1.getVirtualChildAt(point1.getY()-1).setBackgroundColor(Color.WHITE); // Xóa màu nền của ô click đầu tiên
                                    //Toast.makeText(NewGame.this, "Khác nhau", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                tbR1.getVirtualChildAt(point1.getY()-1).setBackgroundColor(Color.WHITE); // Xóa màu nền của ô click đầu tiên
                                //Toast.makeText(NewGame.this, "Khác nhau", Toast.LENGTH_SHORT).show();
                            }
                            point1 = null;
                        }
                    }
                });
            }

            tableLayout.addView(tableRow);
        }
    }

    // Luồng đếm ngược cho progessBar
    private void CountDownTime(){
        // Thread trong android không thể set trực tiếp các giá trị vào layout mà phải thông qua hadler và message
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                time = TIME;
                while(time > 0 && isRunOfThread == true){
                    time--;
                    TIME = time;
                    Pause();

                    if(time < 0 || ITEM == 0){          // Dừng thread
                        isRunOfThread = false;
                        return;
                    }
                    Message message = new Message();
                    message.what = MESSAGE_COUNT_DOUNT;  // Nơi đến
                    message.arg1 = time;                 // Thông điệp cần truyền đi
                    mHandler.sendMessage(message);       // Truyền messager đi

                    try {
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    // Thuật toán BFS kiếm tra xem 2 ảnh có thể xóa hay không
    private int BFS(MyPoint p1, MyPoint p2, int [][] a, int [][] check, int [][] dem){
        if(p1.getX() == p2.getX()){
            if(p1.getY() == p2.getY() + 1 || p1.getY() == p2.getY() - 1) return 1;
        }
        if(p1.getY() == p2.getY()){
            if(p1.getX() == p2.getX() + 1 || p1.getX() == p2.getX() - 1) return 1;
        }


        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 13; j++){
                check[i][j] = 0;
                dem[i][j] = 0;
            }
        }

        Queue<MyPoint> q = new LinkedList<>();
        check[p1.getX()][p1.getY()] = 1;
        dem[p1.getX()][p1.getY()] = 0;
        q.add(p1);
        while(!q.isEmpty()){
            MyPoint p = q.poll();
            for(int i = 1; i <= 4; i++){
                if(i == 1){
                    for(int j = p.getX() - 1; j >= 0; j--){
                        if(check[j][p.getY()] == 0){
                            if(a[j][p.getY()] != 0){
                                break;
                            }
                            else{
                                check[j][p.getY()] = 1;
                                if(dem[j][p.getY()] == 0) dem[j][p.getY()] = dem[p.getX()][p.getY()] + 1;
                                MyPoint m = new MyPoint(j, p.getY());
                                q.add(m);
                            }
                        }
                    }
                }
                if(i == 2){
                    for(int j = p.getY() + 1; j < 13; j++){
                        if(check[p.getX()][j] == 0){
                            if(a[p.getX()][j] != 0){
                                break;
                            }
                            else{
                                check[p.getX()][j] = 1;
                                if(dem[p.getX()][j] == 0) dem[p.getX()][j] = dem[p.getX()][p.getY()] + 1;
                                MyPoint m = new MyPoint(p.getX(),j);
                                q.add(m);
                            }
                        }
                    }
                }
                if(i == 3){
                    for(int j = p.getX() + 1; j < 8; j++){
                        if(check[j][p.getY()] == 0){
                            if(a[j][p.getY()] != 0){
                                break;
                            }
                            else{
                                check[j][p.getY()] = 1;
                                if(dem[j][p.getY()] == 0) dem[j][p.getY()] = dem[p.getX()][p.getY()] + 1;
                                MyPoint m = new MyPoint(j, p.getY());
                                q.add(m);
                            }
                        }
                    }
                }
                if(i == 4){
                    for(int j = p.getY() - 1; j >= 0; j--){
                        if(check[p.getX()][j] == 0){
                            if(a[p.getX()][j] != 0){
                                break;
                            }
                            else{
                                check[p.getX()][j] = 1;
                                if(dem[p.getX()][j] == 0) dem[p.getX()][j] = dem[p.getX()][p.getY()] + 1;
                                MyPoint m = new MyPoint(p.getX(),j);
                                q.add(m);
                            }
                        }
                    }
                }
            }
        }
        if(dem[p2.getX()][p2.getY()] <= 3 && dem[p2.getX()][p2.getY()] >= 1){
            return 1;
        }
        return 0;
    }


    // Hàm phát nhạc nền
    private void SoundBackGround(){
        soundBackground = MediaPlayer.create(this,R.raw.back_ground);
        soundBackground.setLooping(true);
        soundBackground.start();
    }

    // Hàm phát âm thanh khi click
    private void SoundClick(){
        MediaPlayer soundClick = MediaPlayer.create(this,R.raw.click_mouse);
        soundClick.start();
    }


    // Âm thanh nếu click đúng
    private void SoundClickTrue(){

    }

    // Âm thanh khi thua cuộc
    private void SoundLose(){
        soundBackground.stop(); // dừng phát nhạc
        soundBackground.release(); // giải phóng bộ nhớ
        MediaPlayer soundLose = MediaPlayer.create(this,R.raw.lose);
        soundLose.start();
    }

    // Hàm Pause dùng để dừng game cho button Pause
    private void Pause(){
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickPause++;
                if(clickPause % 2 == 0){
                    soundBackground.start();
                    isRunOfThread = true;
                    btnPause.setBackgroundColor(Color.LTGRAY);
                }
                else {
                    soundBackground.pause(); // dừng nhạc background do tạm dừng game
                    isRunOfThread = false;
                    btnPause.setBackgroundColor(Color.BLUE);
                }
                CountDownTime();
            }
        });
    }


    // Hàm thoát game để trở về màn hình chính
    private void ThoatGame(){
        // Thoat game
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundBackground.stop();
                startActivity(new Intent(NewGame.this, MainActivity.class));
            }
        });
    }


    // Hàm gọi Dialog khi thua
    private void DialogLose(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("LOSE");
        dialog.setMessage("Bạn đã thua, có muốn chơi lại không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                soundBackground.stop();
                soundBackground.start();
                SetIcon();
            }
        });

        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                soundBackground.stop();
                startActivity(new Intent(NewGame.this, MainActivity.class));
            }
        });
        dialog.show();
    }
}
