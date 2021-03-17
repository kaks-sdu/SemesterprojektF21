package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Damageable;

public interface LivingEntity extends Entity, Damageable {

    float getSize();

    void setSize(float size);

}
