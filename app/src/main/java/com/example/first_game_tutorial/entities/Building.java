package com.example.first_game_tutorial.entities;

import android.graphics.PointF;

public class Building {

    private PointF pos;
    private Buildings buildingType;

    public Building(PointF pos, Buildings buildingType){
        this.buildingType = buildingType;
        this.pos = pos;
    }

    public PointF getPos() {
        return pos;
    }

    public Buildings getBuildingType() {
        return buildingType;
    }
}
