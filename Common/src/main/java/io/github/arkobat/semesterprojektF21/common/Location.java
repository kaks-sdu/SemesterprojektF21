package io.github.arkobat.semesterprojektF21.common;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a location
 */
public class Location {

    private float x;
    private float y;
    private @Nullable Direction direction;

    /**
     * A constructor for creating a location without a direction
     * @param x represents the x coordinate
     * @param y represents the y coordinate
     */
    public Location(float x, float y) {
        this(x, y, null);
    }

    /**
     *
     * @param x represents the x coordinate
     * @param y represents the y coordinate
     * @param direction an object that represents the {@link Direction}
     */
    public Location(float x, float y, @Nullable Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * A method for getting the x value
     * @return the x value as a float
     */
    public float getX() {
        return x;
    }

    /**
     * A method for setting the x value
     * @param x the new x-coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * A method for getting the y value
     * @return the y value as a float
     */
    public float getY() {
        return y;
    }

    /**
     * A method for setting the y value
     * @param y the new y-coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * A method for getting the direction
     * @return the direction
     */
    public @Nullable Direction getDirection() {
        return direction;
    }

    /**
     * A method for setting the direction
     * @param direction the new direction
     */
    public void setDirection(@Nullable Direction direction) {
        this.direction = direction;
    }

    /**
     * A method for calculating the distance between the location and another location
     * @param o a {@link Location} object
     * @return the distance between the two locations
     */
    public double distanceSquared(Location o) {
        return Math.sqrt(Math.pow(o.x - this.x, 2) + Math.pow(o.y - this.x, y));
    }
}
