package io.github.arkobat.semesterprojektF21.world;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldLoader;
import org.jetbrains.annotations.NotNull;

public class WorldPlugin implements WorldLoader {

    private WorldController worldController;

    @NotNull
    @Override
    public World start(@NotNull GameData gameData) {
        this.worldController = new WorldController();
        return worldController.init();
        System.out.println("Started World plugin");
        return null;
    }

    @Override
    public void stop() {
    }
}
