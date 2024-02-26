package com.example.first_game_tutorial.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.first_game_tutorial.entities.Character;
import com.example.first_game_tutorial.entities.Player;
import com.example.first_game_tutorial.entities.enemies.Skeleton;
import com.example.first_game_tutorial.environments.MapManager;
import com.example.first_game_tutorial.helpers.GameConstants;
import com.example.first_game_tutorial.helpers.interfaces.GameStateInterface;
import com.example.first_game_tutorial.main.Game;

import java.util.ArrayList;

public class Playing extends BaseState implements GameStateInterface {
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private Player player;
    private ArrayList<Skeleton> skeletons;
    private MapManager mapManager;

    //For UI
    private float xCenter = 250, yCenter = 700, radius = 150;
    private Paint circlePaint;
    private float xTouch, yTouch;
    private boolean touchDown;


    public Playing(Game game) {
        super(game);
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        player = new Player();
        skeletons = new ArrayList<>();
        mapManager = new MapManager();

        for(int i = 0; i < 50; i++){
            skeletons.add(new Skeleton(new PointF(100,100)));
        }
    }

    @Override
    public void update(double delta) {
        updatePlayerMove(delta);
        mapManager.setCameraValues(cameraX, cameraY);
        player.update(delta, movePlayer);
        for(Skeleton skeleton: skeletons)
            skeleton.update(delta);
    }

    @Override
    public void render(Canvas c) {
        mapManager.draw(c);
        drawUI(c);

        drawPlayer(c);
        for(Skeleton skeleton: skeletons)
            drawCharacter(c, skeleton);
    }

    private void drawUI(Canvas c) {
        c.drawCircle(xCenter, yCenter, radius, circlePaint);

    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                float a = Math.abs(x - xCenter);
                float b = Math.abs(y - yCenter);
                float c = (float)Math.hypot(a, b);

                if(c<= radius){
                    touchDown = true;
                    xTouch = x;
                    yTouch = y;
                }
                else{
                    game.setCurrentGameState(Game.GameState.MENU);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchDown){
                    xTouch = event.getX();
                    yTouch = event.getY();

                    float xDiff = xTouch - xCenter;
                    float yDiff = yTouch - yCenter;

                    setPlayerMoveTrue(new PointF(xDiff, yDiff));
                }
                break;
            case MotionEvent.ACTION_UP:
                touchDown = false;
                setPlayerMoveFalse();
                break;
        }
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

    private void updatePlayerMove(double delta) {

        if (!movePlayer)
            return;

        float baseSpeed = (float) delta * 300;
        float ratio = Math.abs(lastTouchDiff.y) / Math.abs(lastTouchDiff.x);
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (xSpeed > ySpeed) {
            if (lastTouchDiff.x > 0)
                player.setFaceDir(GameConstants.Face_Dir.RIGHT);
            else
                player.setFaceDir(GameConstants.Face_Dir.LEFT);
        } else {
            if (lastTouchDiff.y > 0)
                player.setFaceDir(GameConstants.Face_Dir.DOWN);
            else player.setFaceDir(GameConstants.Face_Dir.UP);
        }
        if (lastTouchDiff.x < 0)
            xSpeed *= -1;
        if (lastTouchDiff.y < 0)
            ySpeed *= -1;

        int pWidth = GameConstants.Sprite.SIZE;
        int pHeight = GameConstants.Sprite.SIZE;

        if (xSpeed <= 0)
            pWidth = 0;
        if (ySpeed <= 0)
            pHeight = 0;

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;
        if (mapManager.canMoveHere(player.getHitbox().left + cameraX * -1 + deltaX * -1 + pWidth, player.getHitbox().top + cameraY * -1 + deltaY * -1 + pHeight)) {
            cameraX += deltaX;
            cameraY += deltaY;
        }
    }

        public void setPlayerMoveTrue(PointF lastTouchDiff){
            movePlayer = true;
            this.lastTouchDiff = lastTouchDiff;
        }
        public void setPlayerMoveFalse() {
            movePlayer = false;
            player.resetAnimation();
        }
    }
