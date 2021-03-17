package io.github.arkobat.semesterprojektF21.player;

import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import io.github.arkobat.semesterprojektF21.common.entity.Player;

public class PlayerImpl implements Player {

    private final Location location;
    private Color[] colors;
    private int currentColor;
    private int jumpCharges;
    private float size;
    private int health;
    private Vector velocity;

    public PlayerImpl(Color[] colors, Location location) {
        this.colors = colors;
        this.location = location;
        this.size = 1;
        this.jumpCharges = 2;
    }

    @Override
    public Color getNextColor() {
        return colors[++currentColor % colors.length];
    }

    @Override
    public Color getPreviousColor() {
        return colors[--currentColor % colors.length];
    }

    @Override
    public float getSize() {
        return this.size;
    }

    @Override
    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean hasCollision() {
        return true;
    }

    @Override
    public Hitbox getHitbox() {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(Color color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                this.currentColor = i;
                return;
            }
        }
        throw new IllegalArgumentException("Color not unlocked");
    }

    public int getJumpCharges() {
        return jumpCharges;
    }

    public PlayerImpl setJumpCharges(int jumpCharges) {
        this.jumpCharges = jumpCharges;
        return this;
    }

}
