package com.example.first_game_tutorial.environments;

import android.graphics.Canvas;

import com.example.first_game_tutorial.helpers.GameConstants;

public class GameMap {

    private int[][] spriteIds;

    public GameMap(int[][] spriteIds){
        this.spriteIds = spriteIds;
    }

    public int getSpriteId(int xIndex, int yIndex){
        return spriteIds[yIndex][xIndex];

    }
    public int getArrayWidth(){
        return spriteIds[0].length;
    }
    public int getArrayHeight(){
        return spriteIds.length;
    }


}
