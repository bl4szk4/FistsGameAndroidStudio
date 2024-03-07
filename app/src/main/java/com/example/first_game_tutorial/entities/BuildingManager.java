package com.example.first_game_tutorial.entities;

import android.graphics.Canvas;
import android.graphics.PointF;

import java.util.ArrayList;

public class BuildingManager {

    private ArrayList<Building> buildingArrayList;
    private  float cameraX, cameraY;
    public BuildingManager() {
        buildingArrayList = new ArrayList<>();
        buildingArrayList.add(new Building(new PointF(200, 200), Buildings.HOUSE_ONE));
    }

    public void draw(Canvas c){
        for(Building b:buildingArrayList){
            c.drawBitmap(b.getBuildingType().houseImage, b.getPos().x + cameraX, b.getPos().y + cameraY, null);
        }
    }
    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }
}
