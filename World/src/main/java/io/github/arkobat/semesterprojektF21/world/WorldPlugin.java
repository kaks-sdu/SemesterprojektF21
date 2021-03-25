package io.github.arkobat.semesterprojektF21.world;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldLoader;
import org.jetbrains.annotations.NotNull;

public class WorldPlugin implements WorldLoader {

    @NotNull
    @Override
    public World start(@NotNull GameData gameData) {
        return null;
    }

    @Override
    public void stop() {

    }

}
