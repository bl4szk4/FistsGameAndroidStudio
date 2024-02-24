package com.example.first_game_tutorial.entities;

import android.graphics.PointF;

import com.example.first_game_tutorial.MainActivity;

public class Player extends Character{
    public Player() {
        super(new PointF(MainActivity.GAME_WIDTH / 2, MainActivity.GAME_HEIGHT / 2), GameCharacters.PLAYER);
    }
    public void update(double delta, boolean movePlayer){
        if(movePlayer)
            updateAnimation();

    }
}
