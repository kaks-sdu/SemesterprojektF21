package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.World;

public class WorldStartEvent extends Event{

    private final World world;

    public WorldStartEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

}
