package io.github.arkobat.semesterprojektF21.collision.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.*;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.event.EntityMoveEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;


public class MoveListener extends EventListener {

    @Override
    public void onEntityMove(EntityMoveEvent event) {
        if (!event.getEntity().hasCollision()) {
            return;
        }

        if (event.getEntity() instanceof Player) ((Player) event.getEntity()).setJumpCharges(2);

        World world = event.getEntity().getWorld();
        if (!(world instanceof WorldTemp)) {
            return;
        }

        final TiledMapTileLayer collisionLayer = ((WorldTemp) world).getCollisionLayer();

        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        Hitbox hitbox = entity.getHitbox();

        // Calculate offset
        float offsetX = hitbox.getOffsetX();
        if (event.getNewLocation().getX() > event.getOldLocation().getX()) {
            offsetX += hitbox.getWidth();
        }
        float offsetY = hitbox.getOffsetY();
        if (event.getNewLocation().getY() > event.getOldLocation().getY()) {
            offsetY += hitbox.getHeight();
        }

        // Ensure the player is within game borders
        if (loc.getX() + offsetX < 0) {
            loc.setX(0);
        } else if (loc.getX() + offsetX > Gdx.graphics.getWidth()) {
            loc.setX(collisionLayer.getWidth() * collisionLayer.getTileWidth() - hitbox.getWidth());
        }
        if (loc.getY() + offsetY < 0) {
            loc.setY(0);
        } else if (loc.getY() + offsetY > collisionLayer.getHeight() * collisionLayer.getTileHeight()) {
            loc.setY(collisionLayer.getHeight() * collisionLayer.getTileHeight() - hitbox.getHeight());
        }


        int oldX = (int) (event.getOldLocation().getX() + offsetX) / collisionLayer.getTileWidth();
        int newX = (int) (event.getNewLocation().getX() + offsetX) / collisionLayer.getTileWidth();

        int oldY = (int) (event.getOldLocation().getY() + offsetY) / collisionLayer.getTileHeight();
        int newY = (int) (event.getNewLocation().getY() + offsetY) / collisionLayer.getTileHeight();

        if (oldX != newX) {
            while (true) {
                if (checkCollision(collisionLayer, event.getEntity(), oldX, oldY)) {
                    loc.setX(event.getOldLocation().getX());
                    entity.getVelocity().setX(0);
                    break;
                }
                if (newX > oldX) oldX++;
                else if (newX < oldX) oldX--;
                else break;
            }
        }

        if (oldY != newY) {
            while (true) {
                if (checkCollision(collisionLayer, event.getEntity(), oldX, oldY)) {
                    loc.setY(event.getOldLocation().getY());
                    entity.getVelocity().setY(0);
                    break;
                }
                if (newY > oldY) oldY++;
                else if (newY < oldY) oldY--;
                else break;
            }
        }
    }


    private boolean checkCollision(TiledMapTileLayer collisionLayer, Colorable colorable, int x, int y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
        if (cell == null) return false;
        TiledMapTile tile = cell.getTile();
        MapProperties properties = tile.getProperties();
        if (properties.containsKey("color")) {
            Color color = Color.valueOf(properties.get("color", String.class));
            if (color != Color.ALL && color != colorable.getColor()) return false;
        }
        return (properties.containsKey("collision"));
    }

}
