package io.github.arkobat.kolorkarl.collision;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.kolorkarl.common.Hitbox;
import io.github.arkobat.kolorkarl.common.Location;

import java.util.LinkedList;
import java.util.List;

public class CollisionHandler {

    public static List<MapProperties> getProperties(TiledMapTileLayer layer, Location location, Hitbox hitbox) {
        List<MapProperties> list = new LinkedList<>();

        for (int cellX = (int) location.getX() / layer.getTileWidth(); cellX * layer.getTileWidth() < location.getX() + hitbox.getWidth(); cellX++) {
            for (int cellY = (int) location.getY() / layer.getTileHeight(); cellY * layer.getTileHeight() < location.getY() + hitbox.getHeight(); cellY++) {
                TiledMapTileLayer.Cell cell = layer.getCell(cellX, cellY);
                if (cell == null) continue;
                TiledMapTile tile = cell.getTile();
                MapProperties properties = tile.getProperties();
                list.add(properties);
            }
        }
        return list;
    }

}
