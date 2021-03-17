package io.github.arkobat.semesterprojektF21.common;

public interface Collidable {

    /**
     * Checks if there is a collision
     * @return boolean - returns true if there is a collision, else returns false.
     */
    boolean hasCollision();

    /**
     * Not implemented.
     * @return Hitbox
     */
    Hitbox getHitbox();

}
