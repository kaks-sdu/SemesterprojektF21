package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Damageable;

public interface LivingEntity extends Entity, Damageable {

    void getSize();

    float setSize(float size);

}
