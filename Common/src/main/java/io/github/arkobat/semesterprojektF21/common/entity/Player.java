package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Color;

public interface Player extends LivingEntity {

    Color getNextColor();

    Color getPreviousColor();

}
