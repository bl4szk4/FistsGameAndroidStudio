package com.example.first_game_tutorial;

import static com.example.first_game_tutorial.MainActivity.GAME_HEIGHT;
import static com.example.first_game_tutorial.MainActivity.GAME_WIDTH;

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
import com.example.first_game_tutorial.inputs.TouchEvents;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private SurfaceHolder holder;
    private Random random = new Random();
    private GameLoop gameLoop;
    private TouchEvents touchEvents;
    private float x, y;
    private boolean movePlayer;
    private PointF lastTouchDiff;
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
        touchEvents = new TouchEvents(this);
        skeletonPos =  new PointF(random.nextInt(GAME_WIDTH), random.nextInt(GAME_HEIGHT));
//        for (int i = 0; i < 10; i++)
//            skeletons.add(new PointF(random.nextInt(1080), random.nextInt(1920)));
    }


    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);
        c.drawBitmap(GameCharacters.PLAYER.getSprite(playerAniIndexY, playerFaceDir), x, y, null);
        c.drawBitmap(GameCharacters.SKELETON.getSprite(playerAniIndexY, skeletonDir), skeletonPos.x, skeletonPos.y, null);
        touchEvents.draw(c);
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
                if(skeletonPos.y >= GAME_HEIGHT)
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
                if(skeletonPos.x >= GAME_WIDTH)
                    skeletonDir = GameConstants.Face_Dir.LEFT;
                break;
        }

        updatePlayerMove(delta);
        updateAnimation();
    }

    private void updatePlayerMove(double delta) {
        if(!movePlayer)
            return;

        float baseSpeed = (float)delta * 300;
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if(xSpeed > ySpeed){
            if(lastTouchDiff.x > 0)
                playerFaceDir = GameConstants.Face_Dir.RIGHT;
            else
                playerFaceDir = GameConstants.Face_Dir.LEFT;
        }else{
            if(lastTouchDiff.y > 0)
                playerFaceDir = GameConstants.Face_Dir.DOWN;
            else playerFaceDir = GameConstants.Face_Dir.UP;
        }
        if(lastTouchDiff.x < 0)
            xSpeed *= -1;
        if(lastTouchDiff.y < 0)
            ySpeed *= -1;
        x += xSpeed * baseSpeed;
        y += ySpeed * baseSpeed;

    }

    private void updateAnimation(){
        if(!movePlayer)
            return;
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

        return touchEvents.touchEvent(event);
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

    public void setPlayerMoveTrue(PointF lastTouchDiff){
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }
    public void setPlayerMoveFalse(){
        movePlayer = false;
        resetAnimation();
    }

    private void resetAnimation() {
        aniTick = 0;
        playerAniIndexY = 0;
    }

}
