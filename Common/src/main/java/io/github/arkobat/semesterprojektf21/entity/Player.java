package io.github.arkobat.semesterprojektf21.entity;

import io.github.arkobat.semesterprojektf21.Color;

public interface Player extends LivingEntity {

    Color getNextColor();

    Color getPreviousColor();

}
