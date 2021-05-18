package io.github.arkobat.kolorkarl.enemy;

import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.Vector;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.assetmanager.Animation;
import io.github.arkobat.kolorkarl.enemy.astar.AStar;
import io.github.arkobat.kolorkarl.common.*;
import io.github.arkobat.kolorkarl.common.entity.LivingEntity;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

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
    private final Map<String, Animation> animatons;
    private Animation currentAnimation;
    private float speed = 50f;
    private int jumpCharges = 1;
    private float jumpHeight = 75;
    private AStar ai;

    public Enemy(World world, Color[] colors, Location location) {
        this.world = world;
        this.colors = colors;
        this.currentColor = 0;
        this.location = location;
        this.size = 1;
        this.velocity = new Vector();
        this.hitbox = new Hitbox(8, 12, -4, 0);
        this.animatons = new HashMap<>();
        this.ai = new AStar(this);

        String moduleName = "Enemy";
        String color = getColor().lowerCase();
        Animation idleAnimation = new Animation(moduleName, "idle/enemy_0_" + color + "_idle.png", 2, 0.5f);
        Animation runAnimation = new Animation(moduleName, "run/enemy_0_" + color + "_run.png", 4, 0.5f);

        addAnimation("idle", idleAnimation);
        addAnimation("run", runAnimation);
        // Set current animation
        setCurrentAnimation(idleAnimation);
    }

    public float getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(float jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public int getJumpCharges() {
        return jumpCharges;
    }

    public void setJumpCharges(int jumpCharges) {
        this.jumpCharges = jumpCharges;
    }

    public AStar getAi() {
        return ai;
    }

    public void setAi(AStar ai) {
        this.ai = ai;
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

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    @Override
    public boolean hasCollision() {
        return true;
    }

    @Override
    public boolean hasColorCollision() {
        return false;
    }

    @Override
    public @NotNull Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public @NotNull Color getColor() {
        return this.colors[currentColor];
    }

    @Override
    public void setColor(@NotNull Color color) {
        throw new IllegalStateException("Not implemented in enemies");
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
        this.world.removeEntity(this);
    }

    @Override
    public @NotNull World getWorld() {
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
