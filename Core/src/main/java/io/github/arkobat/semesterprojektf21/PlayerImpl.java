package io.github.arkobat.semesterprojektf21;

/**
 * A temporary player, only using for initial testing
 */
public class PlayerImpl  {

    private final Location location;

    public PlayerImpl() {
        this.location = new Location(50, 0);
    }

    public Color getPreviousColor() {
        return null;
    }

    public Location getLocation() {
        return this.location;
    }

}
