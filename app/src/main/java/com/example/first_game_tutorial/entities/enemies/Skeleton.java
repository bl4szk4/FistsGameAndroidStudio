package com.example.first_game_tutorial.entities.enemies;

import static com.example.first_game_tutorial.main.MainActivity.GAME_HEIGHT;
import static com.example.first_game_tutorial.main.MainActivity.GAME_WIDTH;

import android.graphics.PointF;

import com.example.first_game_tutorial.entities.Character;
import com.example.first_game_tutorial.entities.GameCharacters;
import com.example.first_game_tutorial.helpers.GameConstants;

import java.util.Random;

public class Skeleton extends Character {
    private long lastDirChange = System.currentTimeMillis();
    private Random random = new Random();

    public Skeleton(PointF pos) {
        super(pos, GameCharacters.SKELETON);
    }
    
    public void update(double delta){
        updateMove(delta);
        updateAnimation();
        
    }

    private void updateMove(double delta) {

        if(System.currentTimeMillis() - lastDirChange >= 5000){
            lastDirChange = System.currentTimeMillis();
            faceDir = random.nextInt(4);
        }
        switch (faceDir){
            case GameConstants.Face_Dir.DOWN:
                hitbox.top += delta * 300;
                hitbox.bottom += delta * 300;
                if(hitbox.top >= GAME_HEIGHT)
                    faceDir = GameConstants.Face_Dir.UP;
                break;
            case GameConstants.Face_Dir.UP:
                hitbox.top -= delta * 300;
                hitbox.bottom -= delta * 300;
                if(hitbox.top <= 0)
                    faceDir = GameConstants.Face_Dir.DOWN;
                break;
            case GameConstants.Face_Dir.LEFT:
                hitbox.left -= delta * 300;
                hitbox.right -= delta * 300;
                if(hitbox.left <= 0)
                    faceDir = GameConstants.Face_Dir.RIGHT;
                break;
            case GameConstants.Face_Dir.RIGHT:
                hitbox.left += delta * 300;
                hitbox.right += delta * 300;
                if(hitbox.left >= GAME_WIDTH)
                    faceDir = GameConstants.Face_Dir.LEFT;
                break;
        }
    }
}
