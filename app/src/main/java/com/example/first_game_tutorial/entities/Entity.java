package com.example.first_game_tutorial.entities;

import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Entity implements Comparable<Entity>{
    protected RectF hitbox;
    protected boolean active = true;
    protected float lastCameraYVal = 0;

    public Entity(PointF pos, float width, float height){
        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);
    }

    // for building only
    public Entity(PointF pos){
        this.hitbox = new RectF(pos.x, pos.y, pos.x, pos.y);
    }

    public RectF getHitbox() {
        return hitbox;
    }

    public void setActive(boolean b) {
        active = b;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int compareTo(Entity o) {
        return Float.compare(hitbox.top - lastCameraYVal, o.hitbox.top - o.lastCameraYVal);
    }
}
