package com.example.first_game_tutorial.helpers;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.service.quicksettings.Tile;

import com.example.first_game_tutorial.entities.Building;
import com.example.first_game_tutorial.entities.enemies.Skeleton;
import com.example.first_game_tutorial.environments.Doorway;
import com.example.first_game_tutorial.environments.GameMap;
import com.example.first_game_tutorial.environments.Tiles;

import java.util.ArrayList;

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

    public static ArrayList<Skeleton> GetSkeletonsRandomized(int numOfSkeletons, int[][] gamemapArr){
        int width = (gamemapArr[0].length - 1) * GameConstants.Sprite.SIZE;
        int height = (gamemapArr.length - 1) * GameConstants.Sprite.SIZE;

        ArrayList<Skeleton> skeletonArrayList = new ArrayList<>();
        for(int i = 0; i<numOfSkeletons; i++){
            float x =(float) Math.random() * width;
            float y =(float) Math.random() * height;
            skeletonArrayList.add(new Skeleton(new PointF(x, y)));
        }
        return skeletonArrayList;
    }

    public  static boolean CanWalkHere(float x, float y, GameMap gameMap){

        if(x < 0 || y < 0)
            return false;

        if(x >= gameMap.getMapWidth() || y >= gameMap.getArrayHeight())
            return false;

        int tileX = (int) (x / GameConstants.Sprite.SIZE);
        int tileY = (int) (y / GameConstants.Sprite.SIZE);

        int tileId = gameMap.getSpriteId(tileX, tileY);

        return IsTileWalkable(tileId, gameMap.getFloorType());


    }

    public  static boolean CanWalkHere(RectF hitbox,float deltaX, float deltaY, GameMap gameMap){
        if(hitbox.left + deltaX < 0 || hitbox.top + deltaY < 0)
            return false;
        else if (hitbox.right + deltaX >= gameMap.getMapWidth()) {
            return false;
        } else if (hitbox.bottom + deltaY >= gameMap.getArrayHeight()) {
            return false;
        }

        Point[] tileCords = GetTileCords(hitbox, deltaX, deltaY);

        int[] tileIds = GetTileIds(tileCords, gameMap);

        return IsTilesWalkable(tileIds, gameMap.getFloorType());


    }

    public static boolean IsTilesWalkable(int[] tileIds, Tiles tilesType){
        for(int i: tileIds){
            if( ! IsTileWalkable(i, tilesType))
                return false;
        }
        return true;

    }

    private static int[] GetTileIds(Point[] tileCords, GameMap gameMap) {
        int[] tileIds = new int[4];

        for(int i = 0; i<tileIds.length; i++){
            tileIds[i] = gameMap.getSpriteId(tileCords[i].x, tileCords[i].y);
        }
        return tileIds;
    }

    private static Point[] GetTileCords(RectF hitbox, float deltaX, float deltaY) {
        Point[] tileCords = new Point[4];

        int left = (int) (hitbox.left + deltaX) / GameConstants.Sprite.SIZE;
        int right = (int) (hitbox.right + deltaX) / GameConstants.Sprite.SIZE;
        int top = (int) (hitbox.top + deltaY) / GameConstants.Sprite.SIZE;
        int bottom = (int) (hitbox.bottom + deltaY) / GameConstants.Sprite.SIZE;

        tileCords[0] = new Point(left, top);
        tileCords[1] = new Point(right, top);
        tileCords[2] = new Point(left, bottom);
        tileCords[3] = new Point(right, bottom);

        return tileCords;
    }

    public static boolean IsTileWalkable(int tileId, Tiles tilesType){
        if(tilesType == Tiles.INSIDE)
            return (tileId == 394 || tileId < 374);
        return true;
    }
}
