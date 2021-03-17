package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;

public interface GameProcessingService {

    void process(GameData gameData, World world);
}
