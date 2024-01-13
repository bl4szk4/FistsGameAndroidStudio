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

import com.example.first_game_tutorial.entities.GameCharacters;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private SurfaceHolder holder;
    private Random random = new Random();
    private GameLoop gameLoop;
    private float x, y;
    private ArrayList<PointF> skeletons = new ArrayList<>();

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        redPaint.setColor(Color.RED);
        gameLoop = new GameLoop(this);
        for (int i = 0; i < 10; i++)
            skeletons.add(new PointF(random.nextInt(1080), random.nextInt(1920)));
    }


    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);
        c.drawBitmap(GameCharacters.SKELETON.getSprite(1, 1), x, y, null);
        for (PointF p: skeletons)
            c.drawBitmap(GameCharacters.SKELETON.getSprite(0,0), p.x, p.y, null);

        holder.unlockCanvasAndPost(c);
    }
    public void update(double delta){
        for (PointF p: skeletons) {
            p.y += delta * 300;

            if (p.y > 1920)
                p.y = 0;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x = event.getX();
            y = event.getY();
        }
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


}
