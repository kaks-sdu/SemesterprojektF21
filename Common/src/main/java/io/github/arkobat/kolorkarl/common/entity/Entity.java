package io.github.arkobat.kolorkarl.common.entity;

import io.github.arkobat.kolorkarl.common.*;
import io.github.arkobat.kolorkarl.common.*;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a base entity in the world
 */
public interface Entity extends Colorable, Collidable {

    /**
     * Gets the current world of the entity
     *
     * @return the current world of the entity
     */
    @NotNull World getWorld();

    /**
     * Change the world of the entity
     *
     * @param world the new world
     */
    void setWorld(@NotNull World world);

    /**
     * Gets the current location of this entity.
     *
     * @return the location of the entity
     */
    @NotNull Location getLocation();

    /**
     * Gets this entity's velocity and returns it as a 2d vector.
     *
     * @return Vector - Vector contains 2 int values.
     */
    @NotNull Vector getVelocity();

    /**
     * Sets the velocity of the current entity.
     *
     * @param velocity - velocity is a Vector which contains 2 int values.
     */
    void setVelocity(@NotNull Vector velocity);

}