package com.example.first_game_tutorial.entities;

import static com.example.first_game_tutorial.helpers.GameConstants.Sprite.SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.example.first_game_tutorial.R;
import com.example.first_game_tutorial.helpers.GameConstants;
import com.example.first_game_tutorial.helpers.interfaces.BitmapMethods;
import com.example.first_game_tutorial.main.MainActivity;

public enum Buildings implements BitmapMethods {

    HOUSE_ONE(0, 0, 64, 48, 17, 32,14,15);
    Bitmap houseImage;
    RectF doorwayHitbox;

    public RectF getDoorwayHitbox() {
        return doorwayHitbox;
    }

    Buildings(int x, int y, int width, int height, int doorwayX, int doorwayY, int doorwayWidth, int doorwayHeight){
        options.inScaled = false;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.buildings_atlas, options);
        houseImage = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
        doorwayHitbox = new RectF(doorwayX * SCALE_MULTIPLIER, doorwayY * SCALE_MULTIPLIER,
                (doorwayX + doorwayWidth) * SCALE_MULTIPLIER, (doorwayY + doorwayHeight) * SCALE_MULTIPLIER);
    }

    public Bitmap getHouseImage() {
        return houseImage;
    }
}
