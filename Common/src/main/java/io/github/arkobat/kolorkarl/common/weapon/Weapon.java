package io.github.arkobat.kolorkarl.common.weapon;

import io.github.arkobat.kolorkarl.common.Colorable;

/**
 * Represent a weapon in any form
 */
public interface Weapon extends Colorable {

    /**
     * Get how much damage the weapon does.
     * @return how much the weapon damage.
     */
    int getDamage();

    /**
     * Get how fast the weapon can attack.
     * @return the attack speed of the weapon.
     */
    float getAttackSpeed();

}
