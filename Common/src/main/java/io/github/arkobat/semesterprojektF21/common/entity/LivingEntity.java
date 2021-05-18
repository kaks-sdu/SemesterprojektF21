package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Damageable;

/**
 * Represents a living entity, such as a monster or player
 */
public interface LivingEntity extends Entity, Damageable {

    /**
     * Get the size of the living entity.
     *
     * @return the size ratio
     */
    float getSize();

    /**
     * Set the size of the current entity.
     *
     * @param size the new size ratio
     */
    void setSize(float size);

}
