package io.github.arkobat.semesterprojektF21.common;

import org.jetbrains.annotations.NotNull;

/**
 * Represent different objects that have the ability to change color
 */
public interface Colorable {

    /**
     * Get the current color.
     * @return Color - Color is an enum.
     */
    @NotNull Color getColor();

    /**
     * Set the color
     * @param color - color is an enum
     */
    void setColor(@NotNull Color color);

}
