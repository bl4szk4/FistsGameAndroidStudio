package com.example.first_game_tutorial.environments;

import android.graphics.PointF;
import android.graphics.RectF;

public class Doorway {

    private RectF hitbox;
    private boolean active = true;
    private final GameMap gameMapLocatedIn;
    private Doorway doorwayConnectedTo;

    public Doorway(RectF doorwayHitbox,GameMap gameMap) {
        this.hitbox = doorwayHitbox;
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
        return new PointF(hitbox.left, hitbox.top);
    }

    public boolean isPlayerInsideDoorway(RectF playerHitbox, float cameraX, float cameraY){
        return playerHitbox.intersects(hitbox.left + cameraX, hitbox.top + cameraY, hitbox.right + cameraX, hitbox.bottom + cameraY);
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
