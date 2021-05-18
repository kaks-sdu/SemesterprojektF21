package io.github.arkobat.kolorkarl.common.weapon;

/**
 * Represents a Ranged Weapon
 */
public interface RangedWeapon extends Weapon {

    /**
     * Get how many bullets are left in the chamber
     *
     * @return the bullets in the chamber
     */
    int getChamber();

    /**
     * Change the chamber with bullets.
     * Bullets are replaced and <b>not</b> added
     *
     * @param bullets The amount of bullets
     */
    void setChamber(int bullets);

    /**
     * Get how many bullets the chamber can hold
     *
     * @return the max bullets for the chamber
     */
    int getMaxChamber();

}
