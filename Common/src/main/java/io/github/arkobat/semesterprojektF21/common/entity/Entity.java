package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Collidable;
import io.github.arkobat.semesterprojektF21.common.Colorable;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import org.jetbrains.annotations.NotNull;

public interface Entity extends Colorable, Collidable {


    /**
     * Gets the current location of this entity.
     * @return Location - which consists of 2 int values.
     */
    @NotNull Location getLocation();

    /**
     * Gets this entity's velocity and returns it as a 2d vector.
     * @return Vector - Vector contains 2 int values.
     */
    @NotNull  Vector getVelocity();

    /**
     * Sets the velocity of the current entity.
     * @param velocity - velocity is a Vector which contains 2 int values.
     */
    void setVelocity(@NotNull Vector velocity);

}