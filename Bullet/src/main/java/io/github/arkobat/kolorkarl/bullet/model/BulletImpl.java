package io.github.arkobat.kolorkarl.bullet.model;

import io.github.arkobat.kolorkarl.bullet.BulletPlugin;
import io.github.arkobat.kolorkarl.common.*;
import io.github.arkobat.kolorkarl.assetmanager.Animation;
import io.github.arkobat.kolorkarl.common.*;
import io.github.arkobat.kolorkarl.common.weapon.Bullet;
import org.jetbrains.annotations.NotNull;

public class BulletImpl implements Bullet {

    private final Hitbox hitbox;
    private final Color color;
    private World world;
    private final Location location;
    private Vector velocity;
    private long spawnTime;
    private Animation animation;

    public BulletImpl(Color color, World world, Location location, Vector velocity) {
        this.hitbox = new Hitbox(3, 1);
        this.color = color;
        this.world = world;
        this.location = location;
        this.velocity = velocity;
        this.spawnTime = System.currentTimeMillis();
        this.animation = new Animation(BulletPlugin.MODULE_NAME, "bullet/laser_" + color.lowerCase() + ".png", 1, 50);

        if (this.location.getDirection() == Direction.LEFT) {
            this.location.setX(location.getX() - hitbox.getWidth());
        }
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
    public @NotNull Color getColor() {
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
    public void setWorld(@NotNull World world) {
        this.world.removeEntity(this);
        this.world = world;
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
