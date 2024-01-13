package com.example.first_game_tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        redPaint.setColor(Color.RED);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Canvas c = holder.lockCanvas();
        c.drawRect(50, 50, 100, 100, redPaint);

        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
