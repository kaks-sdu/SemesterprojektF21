package io.github.arkobat.kolorkarl.common.game;

import io.github.arkobat.kolorkarl.common.World;
import org.jetbrains.annotations.NotNull;

public interface GameProcessingService {

    /**
     * @param gameData
     * @param world
     */
    void process(@NotNull GameData gameData, @NotNull World world);
}
