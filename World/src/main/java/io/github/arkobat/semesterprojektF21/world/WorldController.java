package io.github.arkobat.semesterprojektF21.world;

import io.github.arkobat.semesterprojektF21.world.model.MapBuilder;
import io.github.arkobat.semesterprojektF21.world.model.WorldMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

    private final Map<String, WorldMap> worldMap = new HashMap<>();

    public void addMap(@NotNull WorldMap map) {
        this.worldMap.put(map.getMapId().toLowerCase(), map);
    }

    public WorldMap getMap(@NotNull String name) {
        return this.worldMap.get(name.toLowerCase());
    }

    WorldMap init() {
        // TODO - Implement via config file, instead of hard coded maps
        WorldMap map = new MapBuilder()
                .setMapId("map1")
                .setMapFileName("level0")
                .build();

        return map;
    }

}
