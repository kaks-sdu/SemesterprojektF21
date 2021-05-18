package io.github.arkobat.kolorkarl.collision.listener;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.kolorkarl.collision.CollisionHandler;
import io.github.arkobat.kolorkarl.common.event.*;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.commonWorld.WorldTemp;

import java.util.List;

/**
 * A listener for changing levels when reaching the "portal" sprite
 */
public class PortalListener extends EventListener {

    @Override
    public void onEntityMove(EntityMoveEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        World world = event.getEntity().getWorld();
        if (!(world instanceof WorldTemp)) {
            return;
        }

        final TiledMapTileLayer collisionLayer = ((WorldTemp) world).getCollisionLayer();
        List<MapProperties> properties = CollisionHandler.getProperties(collisionLayer, event.getEntity().getLocation(), event.getEntity().getHitbox());
        for (MapProperties property : properties) {
            if (property.containsKey("portal")) {
                Event levelChangeEvent = new LevelChangeEvent(property.get("portal", String.class));
                EventManager.callEvent(levelChangeEvent);
                break;
            }
        }
    }

}
