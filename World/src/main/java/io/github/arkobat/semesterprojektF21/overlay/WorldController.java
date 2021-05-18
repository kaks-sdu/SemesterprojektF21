package io.github.arkobat.semesterprojektF21.overlay;

import io.github.arkobat.semesterprojektF21.overlay.model.MapBuilder;
import io.github.arkobat.semesterprojektF21.overlay.model.WorldMap;
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
        WorldMap lvl0 = new MapBuilder()
                .setMapFileName("level0")
                .setMusic("main_theme.mp3")
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

        WorldMap lvl4 = new MapBuilder()
                .setMapFileName("level4")
                .setMusic("main_theme.mp3")
                .build();
        lvl3.setNextMap(lvl4);
        addMap(lvl4);

        WorldMap lvl5 = new MapBuilder()
                .setMapFileName("level5")
                .setMusic("main_theme.mp3")
                .build();
        lvl4.setNextMap(lvl5);
        addMap(lvl5);

        WorldMap lvl6 = new MapBuilder()
                .setMapFileName("level6")
                .setMusic("main_theme.mp3")
                .build();
        lvl5.setNextMap(lvl6);
        addMap(lvl6);

        WorldMap lvl7 = new MapBuilder()
                .setMapFileName("level7")
                .setMusic("main_theme.mp3")
                .build();
        lvl6.setNextMap(lvl7);
        addMap(lvl7);

        WorldMap lvl8 = new MapBuilder()
                .setMapFileName("level8")
                .setMusic("main_theme.mp3")
                .build();
        lvl7.setNextMap(lvl8);
        addMap(lvl8);

        WorldMap lvl9 = new MapBuilder()
                .setMapFileName("level9")
                .setMusic("main_theme.mp3")
                .build();
        lvl8.setNextMap(lvl9);
        addMap(lvl9);
        lvl9.setNextMap(lvl0);

        return lvl0;
    }

}
