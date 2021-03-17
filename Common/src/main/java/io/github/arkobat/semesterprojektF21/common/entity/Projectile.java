package io.github.arkobat.semesterprojektF21.common.entity;

public interface Projectile extends Entity {

    Entity getShooter();

    double getTrajectory();

}
