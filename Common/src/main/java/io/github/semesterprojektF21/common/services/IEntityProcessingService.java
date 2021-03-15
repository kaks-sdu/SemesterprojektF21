package io.github.semesterprojektF21.common.services;

import io.github.semesterprojektF21.common.data.GameData;
import io.github.semesterprojektF21.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
