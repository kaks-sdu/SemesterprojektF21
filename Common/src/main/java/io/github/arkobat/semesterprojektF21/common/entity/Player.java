package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Color;

public interface Player extends LivingEntity {

    /**
     * Get the next player color. If the current color is green, the next is blue.
     * @return Color - Color is an enum currently containing red, green, blue
     */
    Color getNextColor();

    /**
     * Gets the previous player color.
     * @return Color - Color is an enum.
     */
    Color getPreviousColor();

}
