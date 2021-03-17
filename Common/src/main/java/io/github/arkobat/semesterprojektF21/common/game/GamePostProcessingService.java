package io.github.arkobat.semesterprojektF21.common.game;

import io.github.arkobat.semesterprojektF21.common.World;

/**
 *
 * @author jcs
 */
public interface GamePostProcessingService {
        void process(GameData gameData, World world);
}
