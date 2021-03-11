package io.github.arkobat.semesterprojektf21.entity;

public interface Projectile extends Entity {

    Entity getShooter();

    double getTrajectory();

}
