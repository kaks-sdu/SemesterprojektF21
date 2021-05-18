package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Color;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a player
 */
public interface Player extends LivingEntity {

    /**
     * Get the next player color. If the current color is green, the next is blue.
     *
     * @return the next color
     */
    @Nullable Color getNextColor();

    /**
     * Gets the previous player color.
     *
     * @return the previous color
     */
    @Nullable Color getPreviousColor();

    /**
     * Get the amount of jump charges left
     *
     * @return the amount of jump charges
     */
    int getJumpCharges();

    /**
     * Set the amount of jump charges left
     *
     * @param jumpCharges the amount of jump charges
     */
    void setJumpCharges(int jumpCharges);

}
