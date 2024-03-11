package com.example.first_game_tutorial.helpers;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

import com.example.first_game_tutorial.entities.Building;
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

    public static RectF CreateHitboxForDoorway(int xTile, int yTile){
        float x = xTile * GameConstants.Sprite.SIZE;
        float y = yTile * GameConstants.Sprite.SIZE;

        return new RectF(x,y,x + GameConstants.Sprite.SIZE, y + GameConstants.Sprite.SIZE);
    }

    public static RectF CreateHitboxForDoorway(GameMap gameMapLocatedIn, int buildingIdx){
        Building building = gameMapLocatedIn.getBuildingArrayList().get(buildingIdx);


        float x = building.getPos().x;
        float y = building.getPos().y;
        RectF hitbox = gameMapLocatedIn.getBuildingArrayList().get(buildingIdx).getBuildingType().getDoorwayHitbox();

        return new RectF(hitbox.left + x, hitbox.top + y, hitbox.right + x, hitbox.bottom + y);
    }

    public static void ConnectTwoDoorways(GameMap gameMapOne, RectF hitboxOne, GameMap gameMapTwo, RectF hitboxTwo){
        Doorway doorwayOne = new Doorway(hitboxOne, gameMapOne);
        Doorway doorwayTwo = new Doorway(hitboxTwo, gameMapTwo);

        doorwayOne.connectDoorway(doorwayTwo);
        doorwayTwo.connectDoorway(doorwayOne);
    }
}
