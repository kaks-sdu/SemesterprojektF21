package io.github.arkobat.semesterprojektF21.common;

import org.jetbrains.annotations.NotNull;

public interface Collidable {

    /**
     * Checks if there is a collision
     *
     * @return boolean - returns true if there is a collision, else returns false.
     */
    boolean hasCollision();

    /**
     * Not implemented.
     *
     * @return Hitbox
     */
    @NotNull Hitbox getHitbox();

    @NotNull Location getLocation();

    default boolean collides(Collidable collidable) {
        float thisX = this.getLocation().getX() + this.getHitbox().getOffsetX();
        float otherX = collidable.getLocation().getX() + collidable.getHitbox().getOffsetX();

        float thisY = this.getLocation().getY() + this.getHitbox().getOffsetY();
        float otherY = collidable.getLocation().getY() + collidable.getHitbox().getOffsetY();

        return thisX < otherX + collidable.getHitbox().getWidth() &&
                thisX + this.getHitbox().getWidth() > otherX &&
                thisY < otherY + collidable.getHitbox().getHeight() &&
                thisY + this.getHitbox().getHeight() > otherY;
    }

}
