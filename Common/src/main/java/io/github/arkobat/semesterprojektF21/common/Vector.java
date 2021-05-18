package io.github.arkobat.semesterprojektF21.common;

/**
 * A 2D vector
 */
public class Vector {

    private float x;
    private float y;

    public Vector() {
        this(0, 0);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
