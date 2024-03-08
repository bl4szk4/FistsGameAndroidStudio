package com.example.first_game_tutorial.helpers;

import android.graphics.Rect;
import android.graphics.RectF;

import com.example.first_game_tutorial.environments.Doorway;
import com.example.first_game_tutorial.environments.GameMap;

public class HelpMethods {
    public static void AddDoorwayToGameMa(GameMap gameMapLocatedIn, GameMap gameMapTarget, int buildingIdx){
        float houseX = gameMapLocatedIn.getBuildingArrayList().get(buildingIdx).getPos().x;

        float houseY = gameMapLocatedIn.getBuildingArrayList().get(buildingIdx).getPos().y;

        RectF hitbox = gameMapLocatedIn.getBuildingArrayList().get(buildingIdx).getBuildingType().getDoorwayHitbox();
        Doorway doorway = new Doorway(new RectF(hitbox.left + houseX, hitbox.top + houseY, hitbox.right + houseX, hitbox.bottom + houseY), gameMapTarget);

        gameMapLocatedIn.addDoorway(doorway);
    }
}
