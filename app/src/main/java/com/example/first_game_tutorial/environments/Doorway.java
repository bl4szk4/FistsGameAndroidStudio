package com.example.first_game_tutorial.environments;

import android.graphics.PointF;
import android.graphics.RectF;

public class Doorway {
    private PointF doorwayPoint;
    private boolean active = true;
    private final GameMap gameMapLocatedIn;
    private Doorway doorwayConnectedTo;

    public Doorway(PointF doorwayPoint,GameMap gameMap) {
        this.doorwayPoint = doorwayPoint;
        this.gameMapLocatedIn = gameMap;
        gameMapLocatedIn.addDoorway(this);
    }

    public void connectDoorway(Doorway destinationDoorway){
        this.doorwayConnectedTo = destinationDoorway;
    }

    public Doorway getDoorwayConnectedTo() {
        if(doorwayConnectedTo != null)
            return doorwayConnectedTo;
        return null;
    }

    public PointF getPosOfDoorway(){
        return doorwayPoint;
    }

    public boolean isPlayerInsideDoorway(RectF playerHitbox, float cameraX, float cameraY){
        return playerHitbox.contains(doorwayPoint.x + cameraX, doorwayPoint.y + cameraY);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public GameMap getGameMapLocatedIn() {
        return gameMapLocatedIn;
    }
}
