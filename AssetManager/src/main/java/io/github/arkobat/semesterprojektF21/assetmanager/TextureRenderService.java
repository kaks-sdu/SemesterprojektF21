package io.github.arkobat.semesterprojektF21.assetmanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;

public interface TextureRenderService {

    void render(GameData gameData, World world, SpriteBatch sb);

}
