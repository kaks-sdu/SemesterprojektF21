package io.github.arkobat.kolorkarl.commonWorld;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Disposable;
import io.github.arkobat.kolorkarl.common.World;

public interface WorldTemp extends World, Disposable {

    TiledMap getMap();

    TiledMapTileLayer getCollisionLayer();

    void startMap();

    void endMap();

    WorldTemp getNextMap();

}
