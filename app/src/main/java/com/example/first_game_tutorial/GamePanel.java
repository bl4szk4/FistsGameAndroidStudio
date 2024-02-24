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

import com.example.first_game_tutorial.entities.Character;
import com.example.first_game_tutorial.entities.Player;
import com.example.first_game_tutorial.entities.enemies.Skeleton;
import com.example.first_game_tutorial.environments.MapManager;
import com.example.first_game_tutorial.helpers.GameConstants;
import com.example.first_game_tutorial.inputs.TouchEvents;

import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private SurfaceHolder holder;
    private GameLoop gameLoop;
    private TouchEvents touchEvents;
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private Player player;
    private ArrayList<Skeleton> skeletons;


    private MapManager mapManager;
    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        redPaint.setColor(Color.RED);
        gameLoop = new GameLoop(this);
        touchEvents = new TouchEvents(this);
        player = new Player();
        skeletons = new ArrayList<>();
        mapManager = new MapManager();

        for(int i = 0; i < 50; i++){
            skeletons.add(new Skeleton(new PointF(100,100)));
        }
    }


    public void render(){
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);
        mapManager.draw(c);
        touchEvents.draw(c);
        drawPlayer(c);
        for(Skeleton skeleton: skeletons)
            drawCharacter(c, skeleton);
        holder.unlockCanvasAndPost(c);
    }

    private void drawPlayer(Canvas c) {
        c.drawBitmap(
                player.getGameCharType().getSprite(player.getAniIndex(), player.getFaceDir()),
                player.getHitbox().left,
                player.getHitbox().top,
                null);
    }

    public void drawCharacter(Canvas c, Character character){
        c.drawBitmap(
                character.getGameCharType().getSprite(character.getAniIndex(), character.getFaceDir()),
                character.getHitbox().left + cameraX,
                character.getHitbox().top + cameraY,
                null);
    }

    public void update(double delta){
        updatePlayerMove(delta);
        mapManager.setCameraValues(cameraX, cameraY);
        player.update(delta, movePlayer);
        for(Skeleton skeleton: skeletons)
            skeleton.update(delta);

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
                player.setFaceDir(GameConstants.Face_Dir.RIGHT);
            else
                player.setFaceDir(GameConstants.Face_Dir.LEFT);
        }else{
            if(lastTouchDiff.y > 0)
                player.setFaceDir(GameConstants.Face_Dir.DOWN);
            else player.setFaceDir(GameConstants.Face_Dir.UP);
        }
        if(lastTouchDiff.x < 0)
            xSpeed *= -1;
        if(lastTouchDiff.y < 0)
            ySpeed *= -1;

        int pWidth = GameConstants.Sprite.SIZE;
        int pHeight = GameConstants.Sprite.SIZE;

        if(xSpeed <= 0)
            pWidth = 0;
        if(ySpeed <= 0)
            pHeight = 0;

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;
        if(mapManager.canMoveHere(player.getHitbox().left + cameraX * -1 + deltaX * -1 + pWidth, player.getHitbox().top + cameraY * -1 + deltaY * -1 + pHeight)){
            cameraX += deltaX;
            cameraY += deltaY;
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
        player.resetAnimation();
    }


}
