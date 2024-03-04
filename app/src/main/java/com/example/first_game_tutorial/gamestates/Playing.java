package com.example.first_game_tutorial.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.first_game_tutorial.entities.Character;
import com.example.first_game_tutorial.entities.Player;
import com.example.first_game_tutorial.entities.Weapons;
import com.example.first_game_tutorial.entities.enemies.Skeleton;
import com.example.first_game_tutorial.environments.MapManager;
import com.example.first_game_tutorial.helpers.GameConstants;
import com.example.first_game_tutorial.helpers.interfaces.GameStateInterface;
import com.example.first_game_tutorial.main.Game;
import com.example.first_game_tutorial.ui.PlayingUI;

import java.util.ArrayList;

public class Playing extends BaseState implements GameStateInterface {
    private float cameraX, cameraY;
    private boolean movePlayer;
    private PointF lastTouchDiff;
    private Player player;
    private ArrayList<Skeleton> skeletons;
    private MapManager mapManager;

    private PlayingUI playingUI;
    private final Paint redPaint;
    private RectF attackBox = null;
    private boolean attacking, attackChecked;


    public Playing(Game game) {
        super(game);

        player = new Player();
        skeletons = new ArrayList<>();
        mapManager = new MapManager();

        redPaint = new Paint();
        redPaint.setStrokeWidth(1);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setColor(Color.RED);

        playingUI = new PlayingUI(this);

        for (int i = 0; i < 5; i++){
            spawnSkeleton();
        }

        udpateWepHitbox();

    }

    public void spawnSkeleton(){

        skeletons.add(new Skeleton(new PointF(player.getHitbox().left - cameraX, player.getHitbox().top - cameraY)));

    }
    @Override
    public void update(double delta) {
        updatePlayerMove(delta);
        mapManager.setCameraValues(cameraX, cameraY);
        player.update(delta, movePlayer);

        udpateWepHitbox();
        if (attacking) if (!attackChecked){
            checkAttack();
        }

        for(Skeleton skeleton: skeletons)
            if (skeleton.isActive()) skeleton.update(delta);

    }

    private void checkAttack() {

        RectF attackBoxWithoutCam = new RectF(attackBox);
        attackBoxWithoutCam.left -= cameraX;
        attackBoxWithoutCam.top -= cameraY;
        attackBoxWithoutCam.bottom -= cameraY;
        attackBoxWithoutCam.right -= cameraX;

        for(Skeleton s: skeletons){
            if(attackBoxWithoutCam.intersects(s.getHitbox().left, s.getHitbox().top, s.getHitbox().right, s.getHitbox().bottom)){
                s.setActive(false);
            }
            attackChecked = true;
        }
    }

    private void udpateWepHitbox() {
        PointF pos = getWepPos();
        float w = getWepWidth();
        float h = getWepHeight();

        attackBox = new RectF(pos.x, pos.y, pos.x + w, pos.y + h);
    }

    private float getWepHeight() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                Weapons.BIG_SWORD.getHeight();
            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                Weapons.BIG_SWORD.getWidht();

            default -> throw new IllegalStateException("Unexpected value: " + player.getFaceDir());
        };
    }

    private float getWepWidth() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.UP, GameConstants.Face_Dir.DOWN ->
                    Weapons.BIG_SWORD.getWidht();
            case GameConstants.Face_Dir.LEFT, GameConstants.Face_Dir.RIGHT ->
                Weapons.BIG_SWORD.getHeight();

            default -> throw new IllegalStateException("Unexpected value: " + player.getFaceDir());
        };
    }

    private PointF getWepPos() {
        return switch (player.getFaceDir()){

            case GameConstants.Face_Dir.UP ->
                new PointF(player.getHitbox().left + 1.75f * GameConstants.Sprite.SCALE_MULTIPLIER,
                        player.getHitbox().top - Weapons.BIG_SWORD.getHeight());
            case GameConstants.Face_Dir.DOWN ->
                new PointF(player.getHitbox().left + 2.5f * GameConstants.Sprite.SCALE_MULTIPLIER,
                        player.getHitbox().bottom);
            case GameConstants.Face_Dir.LEFT ->
                new PointF(player.getHitbox().left - Weapons.BIG_SWORD.getHeight(),
                        player.getHitbox().bottom - Weapons.BIG_SWORD.getWidht() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);
            case GameConstants.Face_Dir.RIGHT ->
                new PointF(player.getHitbox().right,
                        player.getHitbox().bottom - Weapons.BIG_SWORD.getWidht() - 0.75f * GameConstants.Sprite.SCALE_MULTIPLIER);

            default -> throw new IllegalStateException("Unexpected value: " + player.getFaceDir());
        };
    }

    @Override
    public void render(Canvas c) {
        mapManager.draw(c);

        drawPlayer(c);


        for(Skeleton skeleton: skeletons)
            if (skeleton.isActive()) drawCharacter(c, skeleton);

        playingUI.draw(c);
    }



    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
    }

    private void drawPlayer(Canvas c) {
        c.drawBitmap(
                player.getGameCharType().getSprite(getAniIndex(), player.getFaceDir()),
                player.getHitbox().left,
                player.getHitbox().top,
                null);

        c.drawRect(player.getHitbox(), redPaint);
        if (attacking)
            drawWeapon(c);
    }
    private int getAniIndex(){
        if (attacking) return 4;
        return player.getAniIndex();
    }

    private void drawWeapon(Canvas c) {
        c.rotate(getWepRot(), attackBox.left, attackBox.top);
        c.drawBitmap(Weapons.BIG_SWORD.getWeaponImage(),
                attackBox.left + wepRotAdjLeft(),
                attackBox.top + wepRotAdjTop(),
                null);
        c.rotate(-1 * getWepRot(), attackBox.left, attackBox.top);
        c.drawRect(attackBox, redPaint);
    }

    private float wepRotAdjTop() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.UP,GameConstants.Face_Dir.LEFT -> -Weapons.BIG_SWORD.getHeight();
            default -> 0;
        };
    }

    private float wepRotAdjLeft() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.UP,GameConstants.Face_Dir.RIGHT -> -Weapons.BIG_SWORD.getWidht();
            default -> 0;
        };
    }

    private float getWepRot() {
        return switch (player.getFaceDir()){
            case GameConstants.Face_Dir.LEFT -> 90;
            case GameConstants.Face_Dir.UP -> 180;
            case GameConstants.Face_Dir.RIGHT -> 270;

            default -> 0;
        };
    }

    public void drawCharacter(Canvas c, Character character){
        c.drawBitmap(
                character.getGameCharType().getSprite(character.getAniIndex(), character.getFaceDir()),
                character.getHitbox().left + cameraX,
                character.getHitbox().top + cameraY,
                null);

        c.drawRect(character.getHitbox().left + cameraX, character.getHitbox().top + cameraY,
                character.getHitbox().right + cameraX, character.getHitbox().bottom + cameraY, redPaint);
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

    public void setAttacking(boolean b) {
        attacking = b;
        if (!attacking)
            attackChecked = false;
    }
}

