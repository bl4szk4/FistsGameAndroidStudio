package com.example.first_game_tutorial.environments;

import com.example.first_game_tutorial.entities.Building;
import com.example.first_game_tutorial.entities.Entity;
import com.example.first_game_tutorial.entities.GameObject;
import com.example.first_game_tutorial.entities.enemies.Skeleton;
import com.example.first_game_tutorial.helpers.GameConstants;

import java.util.ArrayList;

public class GameMap {

    private int[][] spriteIds;
    private Tiles floorType;
    private ArrayList<Building> buildingArrayList;
    private ArrayList<Doorway> doorwayArrayList;
    private ArrayList<Skeleton> skeletonArrayList;
    private ArrayList<GameObject> gameObjectArrayList;

    public ArrayList<Skeleton> getSkeletonArrayList() {
        return skeletonArrayList;
    }

    public GameMap(int[][] spriteIds, Tiles floorType, ArrayList<Building> buildingArrayList, ArrayList<GameObject> gameObjectArrayList, ArrayList<Skeleton> skeletonArrayList){
        this.spriteIds = spriteIds;
        this.floorType = floorType;
        this.buildingArrayList = buildingArrayList;
        this.gameObjectArrayList = gameObjectArrayList;
        this.doorwayArrayList = new ArrayList<>();
        this.skeletonArrayList = skeletonArrayList;

    }

    public ArrayList<GameObject> getGameObjectArrayList() {
        return gameObjectArrayList;
    }

    public int getMapWidth(){
        return getArrayWidth() * GameConstants.Sprite.SIZE;
    }

    public int getMap(){
        return getArrayHeight() * GameConstants.Sprite.SIZE;
    }
    public void addDoorway(Doorway doorway){
        this.doorwayArrayList.add(doorway);
    }

    public ArrayList<Doorway> getDoorwayArrayList() {
        return doorwayArrayList;
    }

    public Tiles getFloorType() {
        return floorType;
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

    public ArrayList<Building> getBuildingArrayList() {
        return buildingArrayList;
    }

    public float getMapHeight() {
        return getArrayHeight()*GameConstants.Sprite.SIZE;
    }

    public Entity[] getDrawableList(){
        Entity[] list = new Entity[getDrawableAmount()];

        int i = 0;
        if(buildingArrayList !=null)
            for (Building b: buildingArrayList)
                list[i++] = b;
        if(skeletonArrayList !=null)

            for (Skeleton s: skeletonArrayList)
                list[i++] = s;
        if(gameObjectArrayList !=null)

            for (GameObject g: gameObjectArrayList)
                list[i++] = g;

        return list;
    }

    private int getDrawableAmount(){
        int amount = 0;
        if(buildingArrayList != null)
            amount += buildingArrayList.size();
        if(skeletonArrayList != null)
            amount += skeletonArrayList.size();
        if(gameObjectArrayList != null)
            amount += gameObjectArrayList.size();
        amount ++;  //player

        return amount;
    }
}
