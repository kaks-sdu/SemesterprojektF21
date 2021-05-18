package io.github.arkobat.semesterprojektF21.common.weapon;

import io.github.arkobat.semesterprojektF21.common.Colorable;

/**
 * An interface for representing a weapon
 */
public interface Weapon extends Colorable {

    /**
     * A method for getting the weapons damage
     * @return the weapons damage
     */
    int getDamage();

    /**
     * A method for getting the weapons attack speed
     * @return the weapons attack speed
     */
    float getAttackSpeed();

}
