package io.github.arkobat.semesterprojektf21.entity;

import io.github.arkobat.semesterprojektf21.Damageable;

public interface LivingEntity extends Entity, Damageable {

    void getSize();

    float setSize(float size);

}
