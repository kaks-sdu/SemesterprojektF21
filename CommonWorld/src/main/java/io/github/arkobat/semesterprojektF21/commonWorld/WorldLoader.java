package io.github.arkobat.semesterprojektF21.commonWorld;

import io.github.arkobat.semesterprojektF21.common.game.GameData;
import org.jetbrains.annotations.NotNull;

public interface WorldLoader {

    /**
     * @param gameData basic information regarding the game
     * @return the initial world
     */
    WorldTemp start(@NotNull GameData gameData);

    WorldTemp getWorld(String name);

    void stop();

}