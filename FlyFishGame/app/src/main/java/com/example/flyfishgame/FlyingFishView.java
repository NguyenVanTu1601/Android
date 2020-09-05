package com.example.flyfishgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];

    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int Score;

    private int canvasWidth, canvasHeight;

    private int yellowX,yellowY, yellowSpeed = 16;
    private Paint yellowPain = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();


    private boolean touch = false;

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);


        yellowPain.setColor(Color.YELLOW);
        yellowPain.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart_grey);

        fishY = 550;
        Score = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if(fishY < maxFishY){
            fishY = minFishY;
        }
        if(fishY > maxFishY){
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;

        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;
        }else{
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }

        yellowX = yellowY - yellowSpeed;

        if(hitBallChecker(yellowX,yellowY)){
            Score = Score + 10;
            yellowX = yellowX - 100;
        }

        if(yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY) + minFishY);

        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPain);

        canvas.drawText("Score : " + Score,20,60,scorePaint);

        canvas.drawBitmap(life[0],420,10,null);
        canvas.drawBitmap(life[0],520,10,null);
        canvas.drawBitmap(life[0],620,10,null);
    }


    public boolean hitBallChecker(int x, int y){
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
