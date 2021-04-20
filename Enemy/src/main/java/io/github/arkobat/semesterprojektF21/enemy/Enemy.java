package io.github.arkobat.semesterprojektF21.enemy;

import io.github.arkobat.semesterprojektF21.astar.AStar;
import io.github.arkobat.semesterprojektF21.common.*;
import io.github.arkobat.semesterprojektF21.common.entity.LivingEntity;
import io.github.arkobat.semesterprojektF21.common.texture.Animation;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class Enemy implements LivingEntity {

    @Setter
    private World world;
    private Location location;
    private Color[] colors;
    private int currentColor;
    private float size;
    private int health;
    private Vector velocity;
    private Hitbox hitbox;
    private Map<String, Animation> animatons;
    private Animation currentAnimation;
    private float speed = 50f;
    private AStar ai;

    public Enemy(World world, Color[] colors, Location location){
        this.world = world;
        this.colors = colors;
        this.location = location;
        this.size = 1;
        this.velocity = new Vector();
        this.hitbox = new Hitbox(8, 12, -4, 0);
        animatons = new HashMap<>();
    }

    public void setAi(AStar ai) {
        this.ai = ai;
    }

    public AStar getAi() {
        return ai;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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
    public boolean hasCollision() {
        return true;
    }

    @Override
    public @NotNull Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setColor(@NotNull Color color) {
        // Pass
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
    public void kill() {
        //System.out.println("Enemy died");
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public @NotNull Location getLocation() {
        return location;
    }

    @Override
    public @NotNull Vector getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(@NotNull Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public float getSize() {
        return this.size;
    }

    @Override
    public void setSize(float size) {
        this.size = size;
    }
}
