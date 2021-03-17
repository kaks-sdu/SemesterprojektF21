package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Damageable;

public interface LivingEntity extends Entity, Damageable {

    /**
     * Get the size of the living entity.
     */
    void getSize();

    /**
     * Set the size of the current entity.
     * @param size
     * @return float - the size in float
     */
    float setSize(float size);

}
