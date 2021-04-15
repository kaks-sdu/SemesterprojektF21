package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;
import org.jetbrains.annotations.NotNull;

public interface GameProcessingService {

    /**
     * @param gameData
     * @param world
     */
    void process(@NotNull GameData gameData, @NotNull World world);
}
