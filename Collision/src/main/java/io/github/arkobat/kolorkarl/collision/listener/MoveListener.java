package io.github.arkobat.kolorkarl.collision.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Colorable;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.common.*;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.commonWorld.ExtendedWorld;


public class MoveListener extends EventListener {


    @Override
    public void onEntityMove(EntityMoveEvent event) {
        if (!event.getEntity().hasCollision()) {
            return;
        }

        World world = event.getEntity().getWorld();
        if (!(world instanceof ExtendedWorld)) {
            return;
        }

        final TiledMapTileLayer collisionLayer = ((ExtendedWorld) world).getCollisionLayer();

        Entity entity = event.getEntity();
        Location loc = entity.getLocation();
        Hitbox hitbox = entity.getHitbox();


        // Ensure the player is within game borders
        if (loc.getX() + hitbox.getOffsetX() < 0) {
            loc.setX(0);
        } else if (loc.getX() + hitbox.getWidth() + hitbox.getOffsetX() > Gdx.graphics.getWidth()) {
            loc.setX(collisionLayer.getWidth() * collisionLayer.getTileWidth() - hitbox.getWidth());
        }
        if (loc.getY() + hitbox.getOffsetY() < 0) {
            loc.setY(0);
        } else if (loc.getY() + hitbox.getHeight() + hitbox.getOffsetY() > collisionLayer.getHeight() * collisionLayer.getTileHeight()) {
            loc.setY(collisionLayer.getHeight() * collisionLayer.getTileHeight() - hitbox.getHeight());
        }

        boolean blocked = false;

        if (entity.getVelocity().getX() > 0) {
            int cellX = (int) (loc.getX() + hitbox.getWidth()) / collisionLayer.getTileWidth();
            int cellY = (int) (loc.getY() + 0.1) / collisionLayer.getTileHeight();
            while (cellY * collisionLayer.getTileHeight() < loc.getY() + hitbox.getHeight()) {
                if (checkCollision(collisionLayer, event.getEntity(), cellX, cellY, entity.hasColorCollision())) {
                    blocked = true;
                    break;
                }
                cellY++;
            }

        } else if (entity.getVelocity().getX() < 0) {
            int cellX = (int) loc.getX() / collisionLayer.getTileWidth();
            int cellY = (int) (loc.getY() + 0.1) / collisionLayer.getTileHeight();
            while (cellY * collisionLayer.getTileHeight() < loc.getY() + hitbox.getHeight()) {
                if (checkCollision(collisionLayer, event.getEntity(), cellX, cellY, entity.hasColorCollision())) {
                    blocked = true;
                    break;
                }
                cellY++;
            }
        }

        if (blocked) {
            loc.setX(event.getOldLocation().getX());
            entity.getVelocity().setX(0);
            blocked = false;
        }

        // Check collision Y
        if (entity.getVelocity().getY() > 0) {
            int cellX = (int) loc.getX() / collisionLayer.getTileWidth();
            int cellY = (int) (loc.getY() + hitbox.getHeight()) / collisionLayer.getTileHeight();
            while (cellX * collisionLayer.getTileWidth() < loc.getX() + hitbox.getWidth()) {
                if (checkCollision(collisionLayer, event.getEntity(), cellX, cellY, entity.hasColorCollision())) {
                    blocked = true;
                    break;
                }
                cellX++;
            }

        } else if (entity.getVelocity().getY() < 0) {
            int cellX = (int) loc.getX() / collisionLayer.getTileWidth();
            int cellY = (int) loc.getY() / collisionLayer.getTileHeight();
            while (cellX * collisionLayer.getTileWidth() < loc.getX() + hitbox.getWidth()) {
                if (checkCollision(collisionLayer, event.getEntity(), cellX, cellY, entity.hasColorCollision())) {
                    blocked = true;
                    if (entity instanceof Player) {
                        ((Player) entity).setJumpCharges(2);
                    }
                    break;
                }
                cellX++;
            }
        }

        if (blocked) {
            entity.getVelocity().setY(0);
            loc.setY(event.getOldLocation().getY());
        }

    }


    private boolean checkCollision(TiledMapTileLayer collisionLayer, Colorable colorable, int x, int y, boolean hasColorCollision) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
        if (cell == null) return false;
        TiledMapTile tile = cell.getTile();
        MapProperties properties = tile.getProperties();
        if (hasColorCollision && properties.containsKey("color")) {
            Color color = Color.valueOf(properties.get("color", String.class));
            if (color != Color.ALL && color != colorable.getColor()) return false;
        }
        return (properties.containsKey("collision"));
    }

}
