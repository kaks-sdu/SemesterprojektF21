package io.github.arkobat.semesterprojektf21.entity;

import io.github.arkobat.semesterprojektf21.Colorable;
import io.github.arkobat.semesterprojektf21.Location;
import io.github.arkobat.semesterprojektf21.Vector;

public interface Entity extends Colorable {

    Location getLocation();

    Vector getVelocity();

    void setVelocity(Vector velocity);

}
