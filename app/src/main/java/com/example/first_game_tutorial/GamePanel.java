package com.example.first_game_tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private SurfaceHolder holder;
    private ArrayList<RndSqare> sqares = new ArrayList<>();
    private Random random = new Random();

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        redPaint.setColor(Color.RED);
    }

    private void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        for(RndSqare square: sqares){
            square.draw(c);

        }

        holder.unlockCanvasAndPost(c);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            PointF pos = new PointF(event.getX(), event.getY());
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            int size = 25 + random.nextInt(100);
            sqares.add(new RndSqare(pos, color, size));
            render();

        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        render();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    private class RndSqare{
        private PointF p;
        private int size;
        private Paint paint;
        public RndSqare(PointF p, int color, int size){
            this.p = p;
            this.size = size;
            paint = new Paint();
            paint.setColor(color);
        }
        public void draw(Canvas c){
            c.drawRect(p.x, p.y, p.x + size, p.y + size, paint);
        }
    }
}
