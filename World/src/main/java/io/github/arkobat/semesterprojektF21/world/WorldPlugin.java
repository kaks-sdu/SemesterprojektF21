package io.github.arkobat.semesterprojektF21.world;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.event.EventManager;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldLoader;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.world.listener.DeathListener;
import org.jetbrains.annotations.NotNull;

public class WorldPlugin implements WorldLoader {

    private WorldController worldController;
    public static final String MODULE_NAME = "World";

    @NotNull
    @Override
    public WorldTemp start(@NotNull GameData gameData) {
        this.worldController = new WorldController();
        EventManager.registerListener(new DeathListener());
        return worldController.init();
    }

    @Override
    public WorldTemp getWorld(String name) {
        return worldController.getMap(name);
    }

    @Override
    public void stop() {
    }

}
