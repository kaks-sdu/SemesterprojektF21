package io.github.arkobat.semesterprojektF21.bullet.model;

import io.github.arkobat.semesterprojektF21.common.*;
import io.github.arkobat.semesterprojektF21.common.texture.Animation;
import io.github.arkobat.semesterprojektF21.common.weapon.Bullet;
import io.github.arkobat.semesterprojektF21.bullet.BulletPlugin;
import org.jetbrains.annotations.NotNull;

public class BulletImpl implements Bullet {

    private final Hitbox hitbox = new Hitbox(4, 2);
    private final Color color;
    private final World world;
    private final Location location;
    private Vector velocity;
    private long spawnTime;
    private Animation animation;

    public BulletImpl(Color color, World world, Location location, Vector velocity) {
        this.color = color;
        this.world = world;
        this.location = location;
        this.velocity = velocity;
        this.spawnTime = System.currentTimeMillis();
        this.animation = new Animation(BulletPlugin.MODULE_NAME, "bullet/laser_" +color.lowerCase()+".png", 1, 50);
    }

    @Override
    public boolean hasCollision() {
        return true;
    }

    @Override
    public boolean hasColorCollision() {
        return true;
    }

    @Override
    public @NotNull Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(@NotNull Color color) {
        throw new IllegalStateException("Bullets cannot change colors");
    }

    @Override
    public @NotNull World getWorld() {
        return this.world;
    }

    @Override
    public @NotNull Location getLocation() {
        return this.location;
    }

    @Override
    public @NotNull Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public void setVelocity(@NotNull Vector velocity) {
        this.velocity = velocity;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public Animation getAnimation() {
        return animation;
    }
}
