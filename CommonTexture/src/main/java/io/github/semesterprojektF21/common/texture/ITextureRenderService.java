package io.github.semesterprojektF21.common.texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.semesterprojektF21.common.data.GameData;
import io.github.semesterprojektF21.common.data.World;

public interface ITextureRenderService {

    void render(GameData gameData, World world, SpriteBatch sb);
}
