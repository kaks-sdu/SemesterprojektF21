package io.github.arkobat.semesterprojektF21.commonWorld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.World;

public interface WorldTemp extends World {

    void update(SpriteBatch spriteBatch);

    TiledMapTileLayer getCollisionLayer();

    void resize(int width, int height);

}
