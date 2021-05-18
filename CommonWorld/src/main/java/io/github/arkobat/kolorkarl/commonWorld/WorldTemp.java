package io.github.arkobat.kolorkarl.commonWorld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Disposable;
import io.github.arkobat.kolorkarl.assetmanager.game.ExtendedGameData;
import io.github.arkobat.kolorkarl.common.World;

public interface WorldTemp extends World, Disposable {

    void update(ExtendedGameData gameData, SpriteBatch spriteBatch);

    TiledMap getMap();

    TiledMapTileLayer getCollisionLayer();

    void startMap();

    void endMap();

    WorldTemp getNextMap();

}
