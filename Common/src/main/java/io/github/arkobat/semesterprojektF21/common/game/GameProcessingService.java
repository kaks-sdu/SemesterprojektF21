package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;
import org.jetbrains.annotations.NotNull;

/**
 * An interface that represents the game processing service
 */
public interface GameProcessingService {

    /**
     * A method that should run on each frame, an functions like an update loop for the service
     * @param gameData a {@link GameData} object that holds the delta value
     * @param world a {@link World} object that contains the list of entities
     */
    void process(@NotNull GameData gameData, @NotNull World world);
}
