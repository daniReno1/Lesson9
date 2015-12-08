package com.example.reno.example9;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

/**
 * Created by reno on 12/7/2015.
 */
public class GopherPokeThread extends Thread {
    private GopherPokeView view;
    private boolean running = false;

    public GopherPokeThread(GopherPokeView viewIn)
    {
        this.view = viewIn;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run()
    {
        while (running)
        {
            Canvas c = null;
            try
            {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder())
                {
                    view.onDraw(c);
                }
            }
            finally
            {
                if (c != null)
                {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
