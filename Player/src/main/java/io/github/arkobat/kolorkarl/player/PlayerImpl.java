package io.github.arkobat.kolorkarl.player;

import io.github.arkobat.kolorkarl.assetmanager.Animation;
import io.github.arkobat.kolorkarl.common.*;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EntityColorChangeEvent;
import io.github.arkobat.kolorkarl.common.event.EntityDeathEvent;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static io.github.arkobat.kolorkarl.player.PlayerPlugin.MODULE_NAME;

public class PlayerImpl implements Player {

    private World world;
    private final Location location;
    private Color[] colors;
    private int currentColor;
    private int jumpCharges;
    private float size;
    private int health;
    private Vector velocity;
    private final Hitbox hitbox;
    private Map<String, Animation> animations;
    private Animation currentAnimation;

    public PlayerImpl(World world, Color[] colors, Location location) {
        this.world = world;
        this.colors = colors;
        this.location = location;
        this.size = 1;
        this.jumpCharges = 2;
        this.velocity = new Vector();
        this.hitbox = new Hitbox(8, 12, -4, 0);
        animations = new HashMap<>();
    }

    public void addAnimation(String id, Animation animation) {
        animations.put(id, animation);
    }

    public Animation getAnimation(String id) {
        return animations.get(id);
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void flip() {
        for (Animation animation : this.animations.values()) {
            animation.flip();
        }
    }

    @Override
    public Color getNextColor() {
        return colors[(currentColor + 1) % colors.length];
    }

    @Override
    public Color getPreviousColor() {
        if (currentColor == 0) return colors[colors.length - 1];
        return colors[currentColor - 1];
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
    public void kill() {
        EntityDeathEvent deathEvent = new EntityDeathEvent(this);
        EventManager.callEvent(deathEvent);
    }

    @Override
    public @NotNull World getWorld() {
        return this.world;
    }

    @Override
    public void setWorld(@NotNull World world) {
        this.world.removeEntity(this);
        world.addEntity(this);
        this.world = world;
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

    @Override
    public boolean hasColorCollision() {
        return true;
    }

    @NotNull
    @Override
    public Hitbox getHitbox() {
        return this.hitbox;
    }

    @Override
    public @NotNull Color getColor() {
        return this.colors[currentColor];
    }

    @Override
    public void setColor(@NotNull Color color) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                Color oldColor = getColor();

                EntityColorChangeEvent event = new EntityColorChangeEvent(this, oldColor, color);
                EventManager.callEvent(event);
                if (event.isCanceled()) return;

                this.currentColor = i;
                changeColor(colors[this.currentColor]);
                return;
            }
        }
        throw new IllegalArgumentException("Color not unlocked");
    }

    public void nextColor() {
        int newColor = (this.currentColor + 1) % this.colors.length;

        EntityColorChangeEvent event = new EntityColorChangeEvent(this, getColor(), this.colors[newColor]);
        EventManager.callEvent(event);
        if (event.isCanceled()) return;

        this.currentColor = newColor;
        changeColor(colors[currentColor]);
    }

    public void prevColor() {
        int newColor;
        if (currentColor == 0) {
            newColor = this.colors.length - 1;
        } else {
            newColor = currentColor - 1;
        }

        EntityColorChangeEvent event = new EntityColorChangeEvent(this, getColor(), this.colors[newColor]);
        EventManager.callEvent(event);
        if (event.isCanceled()) return;

        this.currentColor = newColor;
        changeColor(colors[currentColor]);
    }

    private void changeColor(Color color) {
        this.animations.clear();
        boolean flip = this.location.getDirection() == Direction.LEFT;
        this.addAnimation("idle", new Animation(MODULE_NAME, "idle/player_" + color.lowerCase() + "_idle.png", 2, 0.5f));
        this.addAnimation("run", new Animation(MODULE_NAME, "run/player_" + color.lowerCase() + "_run.png", 4, 0.5f));
        if (flip) flip();
    }

    public int getJumpCharges() {
        return jumpCharges;
    }

    public void setJumpCharges(int jumpCharges) {
        this.jumpCharges = jumpCharges;
    }

}
