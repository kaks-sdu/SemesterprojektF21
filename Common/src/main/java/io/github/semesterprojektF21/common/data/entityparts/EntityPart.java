/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.semesterprojektF21.common.data.entityparts;

import io.github.semesterprojektF21.common.data.Entity;
import io.github.semesterprojektF21.common.data.GameData;

/**
 *
 * @author Alexander
 */
public interface EntityPart {
    void process(GameData gameData, Entity entity);
}
