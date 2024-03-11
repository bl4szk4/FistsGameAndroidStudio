package com.example.first_game_tutorial.environments;

import com.example.first_game_tutorial.entities.Building;

import java.util.ArrayList;

public class GameMap {

    private int[][] spriteIds;
    private Tiles floorType;
    private ArrayList<Building> buildingArrayList;
    private ArrayList<Doorway> doorwayArrayList;

    public GameMap(int[][] spriteIds, Tiles floorType, ArrayList<Building> buildingArrayList){
        this.spriteIds = spriteIds;
        this.floorType = floorType;
        this.buildingArrayList = buildingArrayList;
        this.doorwayArrayList = new ArrayList<>();
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
}
