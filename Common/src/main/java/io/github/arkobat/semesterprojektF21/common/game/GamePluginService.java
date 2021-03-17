package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;

public interface GamePluginService {
    void start(GameData gameData, World world);

    void load(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
