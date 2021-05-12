package io.github.arkobat.semesterprojektF21.assetmanager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.World;

public interface TextureRenderService extends Disposable {

    void render(ExtendedGameData gameData, World world, SpriteBatch sb);

    void resize(int width, int height);

}
