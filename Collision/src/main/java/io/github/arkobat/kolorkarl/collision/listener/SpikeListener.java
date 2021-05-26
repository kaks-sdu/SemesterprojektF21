package io.github.arkobat.kolorkarl.collision.listener;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.kolorkarl.collision.CollisionHandler;
import io.github.arkobat.kolorkarl.common.event.EntityHealthChangeEvent;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Damageable;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.commonWorld.ExtendedWorld;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpikeListener extends EventListener {

    @Override
    public void onEntityMove(EntityMoveEvent event) {
        if (!(event.getEntity() instanceof Damageable)) {
            return;
        }

        World world = event.getEntity().getWorld();
        if (!(world instanceof ExtendedWorld)) {
            return;
        }

        final TiledMapTileLayer collisionLayer = ((ExtendedWorld) world).getCollisionLayer();
        List<MapProperties> properties = CollisionHandler.getProperties(collisionLayer, event.getNewLocation(), event.getEntity().getHitbox());
        List<MapProperties> list = new ArrayList<>();
        for (MapProperties mapProperties : properties) {
            if (mapProperties.containsKey("spikes")) {
                list.add(mapProperties);
            }
        }
        properties = list;

        if (properties.size() == 0) {
            return;
        }

        List<MapProperties> toRemove = new LinkedList<>();
        for (MapProperties property : properties) {
            if (!property.containsKey("color")) continue;
            Color color = Color.valueOf(property.get("color", String.class));
            if (color != Color.ALL && color != event.getEntity().getColor()) {
                toRemove.add(property);
            }
        }
        properties.removeAll(toRemove);

        if (properties.size() == 0) {
            return;
        }

        int health = ((Damageable) event.getEntity()).getHealth();
        EntityHealthChangeEvent healthEvent = new EntityHealthChangeEvent(event.getEntity(), 0);
        EventManager.callEvent(healthEvent);
        if (event.isCanceled()) {
            ((Damageable) event.getEntity()).setHealth(health);
        }

        ((Damageable) event.getEntity()).setHealth(healthEvent.getHealth());
        if (((Damageable) event.getEntity()).getHealth() <= 0) {
            ((Damageable) event.getEntity()).kill();
        }

    }
}
