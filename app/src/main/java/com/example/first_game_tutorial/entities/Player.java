package com.example.first_game_tutorial.entities;

import android.graphics.PointF;

import com.example.first_game_tutorial.main.MainActivity;

public class Player extends Character{

    public Player() {
        super(new PointF(MainActivity.GAME_WIDTH / 2, MainActivity.GAME_HEIGHT / 2), GameCharacters.PLAYER);
    }
    public void update(double delta, boolean movePlayer){
        if(movePlayer)
            updateAnimation();

    }

    public void setLastCameraYVal(float lastCameraYVal) {
        this.lastCameraYVal = lastCameraYVal;
    }


}
