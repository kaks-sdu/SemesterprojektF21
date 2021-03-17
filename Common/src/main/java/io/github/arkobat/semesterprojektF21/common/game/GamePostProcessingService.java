package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;

/**
 *
 * @author jcs
 */
public interface GamePostProcessingService {

        /**
         * Updates the game while it's running.
         * @param gameData
         * @param world
         */
        void process(GameData gameData, World world);
}
