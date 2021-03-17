package io.github.arkobat.semesterprojektF21.common.entity;

public interface Projectile extends Entity {

    /**
     * Get the entity who is shooting.
     * @return Entity - to get the position of where the projectile should spawn
     */
    Entity getShooter();

    /**
     * Get the trajectory of the projectile.
     * @return double
     */
    double getTrajectory();

}
