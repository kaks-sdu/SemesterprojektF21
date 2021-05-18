package io.github.arkobat.kolorkarl.common;

/**
 * Represents a 2D vector
 */
public class Vector {

    private float x;
    private float y;

    /**
     * A no-arg constructor for creating a Vector in (0,0)
     */
    public Vector() {
        this(0, 0);
    }

    /**
     * A constructor for creating a {@link Vector}
     * @param x represents the x-coordinate
     * @param y represents the y-coordinate
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
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
}
