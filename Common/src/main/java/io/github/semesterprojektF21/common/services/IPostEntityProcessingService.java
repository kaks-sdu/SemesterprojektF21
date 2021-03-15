package io.github.semesterprojektF21.common.services;

import io.github.semesterprojektF21.common.data.GameData;
import io.github.semesterprojektF21.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
