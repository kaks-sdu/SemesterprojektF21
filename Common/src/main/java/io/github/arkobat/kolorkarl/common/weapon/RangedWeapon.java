package io.github.arkobat.kolorkarl.common.weapon;

public interface RangedWeapon extends Weapon {

    /**
     * @return the amount of bullets currently in the weapon
     */
    int getChamber();

    int getMaxChamber();

    /**
     * Change the chamber with bullets.
     * Bullets are replaced and <b>not</b> added
     *
     * @param bullets The amount of bullets
     */
    void setChamber(int bullets);

}
