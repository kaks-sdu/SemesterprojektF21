package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;
import org.jetbrains.annotations.NotNull;

/**
 * @author jcs
 */
public interface GamePostProcessingService {

    /**
     * Updates the game while it's running.
     *
     * @param gameData
     * @param world
     */
    void process(@NotNull GameData gameData, @NotNull World world);
}
