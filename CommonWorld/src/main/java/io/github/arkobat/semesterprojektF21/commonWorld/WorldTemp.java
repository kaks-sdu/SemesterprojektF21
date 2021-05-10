package io.github.arkobat.semesterprojektF21.commonWorld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.World;

public interface WorldTemp extends World {

    void update(ExtendedGameData gameData, SpriteBatch spriteBatch);

    TiledMap getMap();

    TiledMapTileLayer getCollisionLayer();

    void startMap();

    void endMap();

    WorldTemp getNextMap();

}
