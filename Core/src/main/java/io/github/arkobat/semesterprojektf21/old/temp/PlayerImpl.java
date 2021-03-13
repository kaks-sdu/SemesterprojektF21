package io.github.arkobat.semesterprojektf21.old.temp;

import io.github.arkobat.semesterprojektf21.Location;
import io.github.arkobat.semesterprojektf21.Vector;
import lombok.Getter;
import lombok.Setter;

/**
 * A temporary player, only using for initial testing
 */
public class PlayerImpl {

    private final Location location;
    private final Vector velocity;

    @Setter
    @Getter
    boolean canJump = true;

    public PlayerImpl() {
        this.location = new Location(50, 0);
        this.velocity = new Vector(0, 0);
    }

    public Location getLocation() {
        return this.location;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector velocity) {

    }
}
