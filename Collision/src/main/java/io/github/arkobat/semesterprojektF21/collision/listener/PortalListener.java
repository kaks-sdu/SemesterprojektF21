package io.github.arkobat.semesterprojektF21.collision.listener;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.collision.CollisionHandler;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.event.*;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;

import java.util.List;

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
