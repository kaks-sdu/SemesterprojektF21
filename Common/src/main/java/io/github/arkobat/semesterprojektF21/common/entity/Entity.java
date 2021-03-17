package io.github.arkobat.semesterprojektF21.common.entity;

import io.github.arkobat.semesterprojektF21.common.Collidable;
import io.github.arkobat.semesterprojektF21.common.Colorable;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;

public interface Entity extends Colorable, Collidable {

    Location getLocation();

    Vector getVelocity();

    void setVelocity(Vector velocity);

}