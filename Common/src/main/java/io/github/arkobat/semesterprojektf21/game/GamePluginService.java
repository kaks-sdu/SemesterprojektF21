package io.github.arkobat.semesterprojektf21.game;

import io.github.arkobat.semesterprojektf21.World;

public interface GamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);

}
