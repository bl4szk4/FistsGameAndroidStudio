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
import com.example.first_game_tutorial.ui.ButtonImages;
import com.example.first_game_tutorial.ui.CustomButton;
import com.example.first_game_tutorial.ui.PlayingUI;

import java.nio.Buffer;
import java.util.ArrayList;

public class Playing extends BaseState implements GameStateInterface {
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private Player player;
    private ArrayList<Skeleton> skeletons;
    private MapManager mapManager;

    private PlayingUI playingUI;


    public Playing(Game game) {
        super(game);

        player = new Player();
        skeletons = new ArrayList<>();
        mapManager = new MapManager();

        for(int i = 0; i < 50; i++){
            skeletons.add(new Skeleton(new PointF(100,100)));
        }
        playingUI = new PlayingUI(this);

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

        drawPlayer(c);
        for(Skeleton skeleton: skeletons)
            drawCharacter(c, skeleton);
        playingUI.draw(c);
    }



    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
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

    public void setGameStateToMenu(){
        game.setCurrentGameState(Game.GameState.MENU);

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

