package io.github.arkobat.semesterprojektF21.world;

import io.github.arkobat.semesterprojektF21.world.model.MapBuilder;
import io.github.arkobat.semesterprojektF21.world.model.WorldMap;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

    private final Map<String, WorldMap> worldMap = new HashMap<>();

    public void addMap(@NotNull WorldMap map) {
        this.worldMap.put(map.getMapFileName().toLowerCase(), map);
    }

    public WorldMap getMap(@NotNull String name) {
        return this.worldMap.get(name.toLowerCase());
    }

    WorldMap init() {
<<<<<<< HEAD
        // TODO - Implement via config file, instead of hard coded maps
        WorldMap map = new MapBuilder()
                .setMapId("map1")
                .setMapFileName("level3")
=======
        WorldMap lvl0 = new MapBuilder()
                .setMapFileName("level0")
                .setMusic("main_theme.mp3")
>>>>>>> e70eba32c145443e1fca12999d97639b79603078
                .build();
        addMap(lvl0);

        WorldMap lvl1 = new MapBuilder()
                .setMapFileName("level1")
                .setMusic("main_theme.mp3")
                .build();
        lvl0.setNextMap(lvl1);
        addMap(lvl1);

        WorldMap lvl2 = new MapBuilder()
                .setMapFileName("level2")
                .setMusic("main_theme.mp3")
                .build();
        lvl1.setNextMap(lvl2);
        addMap(lvl2);

        WorldMap lvl3 = new MapBuilder()
                .setMapFileName("level3")
                .setMusic("main_theme.mp3")
                .build();
        lvl2.setNextMap(lvl3);
        addMap(lvl3);

        return lvl0;
    }

}
