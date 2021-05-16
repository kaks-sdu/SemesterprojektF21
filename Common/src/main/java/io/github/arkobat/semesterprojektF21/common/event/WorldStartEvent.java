package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.World;

public class WorldStartEvent extends Event{

    private final World world;

    public WorldStartEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

}
