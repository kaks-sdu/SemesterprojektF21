package io.github.arkobat.semesterprojektF21.common.texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;

public interface ITextureRenderService {

    void render(GameData gameData, World world, SpriteBatch sb);
}
