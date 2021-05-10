package io.github.arkobat.semesterprojektF21.assetmanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.World;

public interface TextureRenderService {

    void render(ExtendedGameData gameData, World world, SpriteBatch sb);

}
