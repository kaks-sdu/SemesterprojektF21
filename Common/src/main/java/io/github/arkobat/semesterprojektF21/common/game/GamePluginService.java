package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;

public interface GamePluginService {

    /**
     * Start the game
     * @param gameData
     * @param world
     */
    void start(GameData gameData, World world);


    void load();

    /**
     * Stop the game
     * @param gameData
     * @param world
     */
    void stop(GameData gameData, World world);
}
