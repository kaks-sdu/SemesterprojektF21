package io.github.arkobat.kolorkarl.common;

/**
 * Represent different objects that have the ability to take damage
 */
public interface Damageable {

    /**
     * Get the current health.
     * @return int
     */
    int getHealth();

    /**
     * Set the current health.
     * @param health - health is an int
     */
    void setHealth(int health);

    void kill();

}
