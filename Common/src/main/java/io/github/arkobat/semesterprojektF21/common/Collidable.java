package io.github.arkobat.semesterprojektF21.common;

import org.jetbrains.annotations.NotNull;

/**
 * Represents objects that can collide with other objects
 */
public interface Collidable {

    /**
     * Checks if there is a collision.
     *
     * @return if the collidable have collision.
     */
    boolean hasCollision();

    /**
     * If the collidable respect colors of platforms.
     * If the Collidable have color collision, it will fall thru platforms of different colors.
     *
     * @return if the collidable have color collision
     */
    boolean hasColorCollision();

    /**
     * Get the {@link Hitbox} of the collidable
     *
     * @return the hitbox
     */
    @NotNull Hitbox getHitbox();

    /**
     * Gets the current location of this entity.
     *
     * @return the location of the collidable.
     */
    @NotNull Location getLocation();

    /**
     * Checks if the collidable collides with another collidable.
     * It is assumed all collidable are squares.
     *
     * @param collidable the other collidable.
     * @return weather the two collides.
     */
    default boolean collides(@NotNull Collidable collidable) {
        float thisX = this.getLocation().getX();
        float otherX = collidable.getLocation().getX();

        float thisY = this.getLocation().getY();
        float otherY = collidable.getLocation().getY();

        return thisX < otherX + collidable.getHitbox().getWidth() &&
                thisX + this.getHitbox().getWidth() > otherX &&
                thisY < otherY + collidable.getHitbox().getHeight() &&
                thisY + this.getHitbox().getHeight() > otherY;
    }

}
