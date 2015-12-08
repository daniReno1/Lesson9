package com.example.reno.example9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by reno on 12/7/2015.
 */
public class GopherPokeView extends SurfaceView {

    private SurfaceHolder holder;
    private GopherPokeThread gpthread = null;
    private Bitmap gopher;
    private float gopherX;
    private float gopherY;
    private int score = 0;
    private Paint scorePaint;
    private Random gopherRandomizer;
    private long gopherTimer;
    public GopherPokeView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gopher = BitmapFactory.decodeResource(getResources(), R.drawable.cat1);
                gopherRandomizer = new Random();
                moveGopher();

                scorePaint = new Paint();
                scorePaint.setTextSize(50.0f);
                scorePaint.setColor(Color.BLACK);
                makeThread();
                gpthread.setRunning(true);
                gpthread.start();

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void moveGopher() {
        gopherX = (float) gopherRandomizer.nextInt(getWidth() - 100);
        gopherY = (float) gopherRandomizer.nextInt(getHeight() - 175);
        gopherY += 100.0f;
        gopherTimer = System.currentTimeMillis();
    }

    public void makeThread() {
        gpthread = new GopherPokeThread(this);

    }

    public void killThread() {
        boolean retry = true;
        gpthread.setRunning(false);
        while (retry) {
            try {
                gpthread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float touchedX = e.getX();
        float touchedY = e.getY();

        if (touchedX >= gopherX
                && touchedX <= gopherX + 100.0f
                && touchedY >= gopherY
                && touchedY <= gopherY + 75.0f) {
            score++;
            moveGopher();
        }

        return true;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
        if(System.currentTimeMillis() > gopherTimer + 750) moveGopher();
        canvas.drawBitmap(gopher, gopherX, gopherY, null);
    }

    public void onDestroy()
    {
        gopher.recycle();
        gopher=null;
        System.gc();
    }
}
