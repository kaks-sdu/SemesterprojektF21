package io.github.arkobat.semesterprojektf21.weapon;

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

    /**
     * Should be given a new name
     *
     * @return how many bullets there is left to be reloaded with
     */
    @Deprecated
    int getReloadBullets();

    /**
     * Should be given a new name
     * @return
     */
    @Deprecated
    int getMaxReloadBullets();

    /**
     * Should be given a new name
     *
     * @param bullets The amount of bullets
     */
    @Deprecated
    void setReloadBullets(int bullets);

    /**
     * Reloads the weapon.
     * It takes bullets from {@link #getReloadBullets()} and but them in the chamber
     */
    void reload();


}
