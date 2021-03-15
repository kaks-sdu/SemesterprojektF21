package io.github.semesterprojektF21.common.services;

import io.github.semesterprojektF21.common.data.GameData;
import io.github.semesterprojektF21.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
