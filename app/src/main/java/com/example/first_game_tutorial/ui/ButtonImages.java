package com.example.first_game_tutorial.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.first_game_tutorial.R;
import com.example.first_game_tutorial.helpers.interfaces.BitmapMethods;
import com.example.first_game_tutorial.main.MainActivity;

public enum ButtonImages implements BitmapMethods {
    MENU_START(R.drawable.mainmenu_button_start, 300, 140),
    PLAYING_MENU(R.drawable.playing_button_menu, 140, 140);

    private  int width, height;
    private Bitmap normal, pushed;
    ButtonImages(int resId, int width, int height) {
        options.inScaled = false;
        this.width = width;
        this.height = height;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options);
        normal = Bitmap.createBitmap(buttonAtlas, 0, 0, width, height);
        pushed = Bitmap.createBitmap(buttonAtlas, width, 0, width, height);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBtnImg(boolean isBtnPushed){

        return isBtnPushed ? pushed : normal;
    }
}
