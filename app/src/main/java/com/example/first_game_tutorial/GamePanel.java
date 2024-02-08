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
import com.example.first_game_tutorial.helpers.GameConstants;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private SurfaceHolder holder;
    private Random random = new Random();
    private GameLoop gameLoop;
    private float x, y;
//    private ArrayList<PointF> skeletons = new ArrayList<>();
    private PointF skeletonPos;
    private int skeletonDir = GameConstants.Face_Dir.LEFT;

    private int playerAniIndexY, playerFaceDir = GameConstants.Face_Dir.DOWN;
    private int aniTick;
    private int aniSpeed = 10;
    private long lastDirChange = System.currentTimeMillis();
    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        redPaint.setColor(Color.RED);
        gameLoop = new GameLoop(this);
        skeletonPos =  new PointF(random.nextInt(1080), random.nextInt(1920));
//        for (int i = 0; i < 10; i++)
//            skeletons.add(new PointF(random.nextInt(1080), random.nextInt(1920)));
    }


    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);
        c.drawBitmap(GameCharacters.PLAYER.getSprite(playerAniIndexY, playerFaceDir), x, y, null);
        c.drawBitmap(GameCharacters.SKELETON.getSprite(playerAniIndexY, skeletonDir), skeletonPos.x, skeletonPos.y, null);
//        for (PointF p: skeletons)
//            c.drawBitmap(GameCharacters.SKELETON.getSprite(0,0), p.x, p.y, null);

        holder.unlockCanvasAndPost(c);
    }
    public void update(double delta){
        if(System.currentTimeMillis() - lastDirChange >= 5000){
            lastDirChange = System.currentTimeMillis();
            skeletonDir = random.nextInt(4);
        }
        switch (skeletonDir){
            case GameConstants.Face_Dir.DOWN:
                skeletonPos.y += delta * 300;
                if(skeletonPos.y >= 1920)
                    skeletonDir = GameConstants.Face_Dir.UP;
                break;
            case GameConstants.Face_Dir.UP:
                skeletonPos.y -= delta * 300;
                if(skeletonPos.y <= 0)
                    skeletonDir = GameConstants.Face_Dir.DOWN;
                break;
            case GameConstants.Face_Dir.LEFT:
                skeletonPos.x -= delta * 300;
                if(skeletonPos.x <= 0)
                    skeletonDir = GameConstants.Face_Dir.RIGHT;
                break;
            case GameConstants.Face_Dir.RIGHT:
                skeletonPos.x += delta * 300;
                if(skeletonPos.x >= 1080)
                    skeletonDir = GameConstants.Face_Dir.LEFT;
                break;
        }
        updateAnimation();
    }

    private void updateAnimation(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            playerAniIndexY++;
            if(playerAniIndexY >= 4)
                playerAniIndexY = 0;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){

            float newX = event.getX();
            float newY = event.getY();

            float xDiff = Math.abs(newX - x);
            float yDiff = Math.abs(newY - y);

            if(xDiff > yDiff) {
                if (newX > x)
                    playerFaceDir = GameConstants.Face_Dir.RIGHT;
                else playerFaceDir = GameConstants.Face_Dir.LEFT;
            }else{
                if(newY > y)
                    playerFaceDir = GameConstants.Face_Dir.DOWN;
                else playerFaceDir = GameConstants.Face_Dir.UP;
            }
            x = newX;
            y = newY;
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
