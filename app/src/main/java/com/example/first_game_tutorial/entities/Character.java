package com.example.first_game_tutorial.entities;

import static com.example.first_game_tutorial.helpers.GameConstants.Sprite.HITBOX_SIZE;


import android.graphics.PointF;

import com.example.first_game_tutorial.helpers.GameConstants;

public abstract class Character extends Entity{
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstants.Face_Dir.DOWN;
    protected final GameCharacters gameCharType;
    public Character(PointF pos, GameCharacters gameCharType) {
        super(pos, HITBOX_SIZE, HITBOX_SIZE);
        this.gameCharType = gameCharType;
    }
    protected void updateAnimation(){
        aniTick++;
        if(aniTick >= GameConstants.Animation.SPEED){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GameConstants.Animation.AMOUNT)
                aniIndex = 0;
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }


    public int getFaceDir() {
        return faceDir;
    }

    public void setFaceDir(int faceDir) {
        this.faceDir = faceDir;
    }

    public GameCharacters getGameCharType() {
        return gameCharType;
    }
    public void resetAnimation(){
        aniTick = 0;
        aniIndex = 0;

    }
}
