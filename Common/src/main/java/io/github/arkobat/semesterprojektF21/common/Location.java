package io.github.arkobat.semesterprojektF21.common;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a location
 */
public class Location {

    private float x;
    private float y;
    private @Nullable Direction direction;

    public Location(float x, float y) {
        this(x, y, null);
    }

    public Location(float x, float y, @Nullable Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public @Nullable Direction getDirection() {
        return direction;
    }

    public void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    public double distanceSquared(Location o) {
        return Math.sqrt(Math.pow(o.x - this.x, 2) + Math.pow(o.y - this.x, y));
    }
}
