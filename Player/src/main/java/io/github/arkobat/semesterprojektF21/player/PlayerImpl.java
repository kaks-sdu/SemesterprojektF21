package io.github.arkobat.semesterprojektF21.player;

import io.github.arkobat.semesterprojektF21.common.*;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.texture.Animation;

import java.util.HashMap;
import java.util.Map;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class PlayerImpl implements Player {

    @Setter
    private World world;
    private final Location location;
    private Color[] colors;
    private int currentColor;
    private int jumpCharges;
    private float size;
    private int health;
    private Vector velocity;
    private Hitbox hitbox;
    private Map<String, Animation> animatons;
    private Animation currentAnimation;

    public PlayerImpl(World world, Color[] colors, Location location) {
        this.world = world;
        this.colors = colors;
        this.location = location;
        this.size = 1;
        this.jumpCharges = 2;
        this.velocity = new Vector();
        this.hitbox = new Hitbox(8, 16);
        animatons = new HashMap<>();
    }

    public void addAnimation(String id, Animation animation) {
        animatons.put(id, animation);
    }

    public Animation getAnimation(String id) {
        return animatons.get(id);
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
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
    public World getWorld() {
        return this.world;
    }

    @NotNull
    @Override
    public Location getLocation() {
        return this.location;
    }

    @NotNull
    @Override
    public Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public void setVelocity(@NotNull Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean hasCollision() {
        return true;
    }

    @NotNull
    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(@NotNull Color color) {
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
