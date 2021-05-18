package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.World;

/**
 * An event for starting the world
 */
public class WorldStartEvent extends Event{

    private final World world;

    /**
     * Creates a new WorldStartEvent
     * @param world the world
     */
    public WorldStartEvent(World world) {
        this.world = world;
    }

    /**
     * Gets the world that starts
     * @return the world
     */
    public World getWorld() {
        return world;
    }

}
