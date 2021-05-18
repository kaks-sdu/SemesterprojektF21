package io.github.arkobat.kolorkarl.assetmanager.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import io.github.arkobat.kolorkarl.assetmanager.game.ExtendedGameData;
import io.github.arkobat.kolorkarl.common.World;

public interface TextureRenderService extends Disposable {

    void render(ExtendedGameData gameData, World world, SpriteBatch sb);

    void resize(int width, int height);

}
