package com.example.first_game_tutorial.environments;

import android.graphics.PointF;
import android.graphics.RectF;

public class Doorway {

    private RectF hitbox;
    private boolean active = true;
    private final GameMap gameMap;

    public Doorway(RectF doorwayHitbox,GameMap gameMap) {
        this.hitbox = doorwayHitbox;
        this.gameMap = gameMap;
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

    public GameMap getGameMap() {
        return gameMap;
    }
}
