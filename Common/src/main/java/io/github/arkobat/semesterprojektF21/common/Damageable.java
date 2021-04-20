package io.github.arkobat.semesterprojektF21.common;

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
