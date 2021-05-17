package io.github.arkobat.kolorkarl.common.entity;

import org.jetbrains.annotations.Nullable;

public interface Projectile extends Entity {

    /**
     * Get the entity who is shooting.
     *
     * @return Entity - to get the position of where the projectile should spawn
     */
    @Nullable Entity getShooter();

    /**
     * Get the trajectory of the projectile.
     *
     * @return double
     */
    double getTrajectory();

}
