package com.example.first_game_tutorial.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.first_game_tutorial.R;
import com.example.first_game_tutorial.helpers.GameConstants;
import com.example.first_game_tutorial.helpers.interfaces.BitmapMethods;
import com.example.first_game_tutorial.main.MainActivity;

public enum Buildings implements BitmapMethods {

    HOUSE_ONE(0, 0, 64, 48);
    Bitmap houseImage;
    Buildings(int x, int y, int width, int height){
        options.inScaled = false;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.buildings_atlas, options);
        houseImage = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    public Bitmap getHouseImage() {
        return houseImage;
    }
}
