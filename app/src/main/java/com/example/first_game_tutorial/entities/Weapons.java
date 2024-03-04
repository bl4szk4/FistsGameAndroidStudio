package com.example.first_game_tutorial.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.first_game_tutorial.R;
import com.example.first_game_tutorial.helpers.interfaces.BitmapMethods;
import com.example.first_game_tutorial.main.MainActivity;

public enum Weapons implements BitmapMethods {
    BIG_SWORD(R.drawable.big_sword);
    Bitmap weaponImage;

    Weapons(int resID) {
        options.inScaled = false;
        weaponImage = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options));
    }

    public Bitmap getWeaponImage() {
        return weaponImage;
    }

    public int getWidht(){
        return weaponImage.getWidth();
    }
    public int getHeight(){
        return weaponImage.getHeight();
    }
}
