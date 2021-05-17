package io.github.arkobat.kolorkarl.common.game;

import io.github.arkobat.kolorkarl.common.World;
import org.jetbrains.annotations.NotNull;

public interface GamePluginService {

    /**
     * Start the game
     *
     * @param gameData
     * @param world
     */
    void start(@NotNull GameData gameData, @NotNull World world);

    /**
     * Stop the game
     *
     * @param gameData
     * @param world
     */
    void stop(@NotNull GameData gameData, @NotNull World world);
}
