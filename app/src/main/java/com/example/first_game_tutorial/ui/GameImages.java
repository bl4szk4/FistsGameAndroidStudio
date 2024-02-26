package com.example.first_game_tutorial.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.first_game_tutorial.R;
import com.example.first_game_tutorial.helpers.interfaces.BitmapMethods;
import com.example.first_game_tutorial.main.MainActivity;

public enum GameImages implements BitmapMethods {

    MAINMENU_MENUBG(R.drawable.mainmenu_menubackground);
    private final Bitmap image;
    GameImages(int resID){
        options.inScaled = false;
        image = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
    }

    public Bitmap getImage() {
        return image;
    }
}
