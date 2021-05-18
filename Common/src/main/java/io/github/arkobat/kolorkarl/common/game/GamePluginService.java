package io.github.arkobat.kolorkarl.common.game;

import io.github.arkobat.kolorkarl.common.World;
import org.jetbrains.annotations.NotNull;

/**
 * A interface that represents a game plugin service
 */
public interface GamePluginService {

    /**
     * A method that should run when starting the service
     *
     * @param gameData a {@link GameData} object that holds the delta value
     * @param world a {@link World} object that contains the list of entities
     */
    void start(@NotNull GameData gameData, @NotNull World world);

    /**
     * A method that should run when stopping the service
     *
     * @param gameData a {@link GameData} object that holds the delta value
     * @param world a {@link World} object that contains the list of entities
     */
    void stop(@NotNull GameData gameData, @NotNull World world);
}
