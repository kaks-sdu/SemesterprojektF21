package io.github.arkobat.semesterprojektF21.collision.listener;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.collision.CollisionHandler;
import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Damageable;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.event.EntityMoveEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SpikeListener extends EventListener {

    @Override
    public void onEntityMove(EntityMoveEvent event) {
        if (!(event.getEntity() instanceof Damageable)) {
            return;
        }

        World world = event.getEntity().getWorld();
        if (!(world instanceof WorldTemp)) {
            return;
        }

        final TiledMapTileLayer collisionLayer = ((WorldTemp) world).getCollisionLayer();
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
            if (color != event.getEntity().getColor()) {
                toRemove.add(property);
            }
        }
        properties.removeAll(toRemove);

        if (properties.size() == 0) {
            return;
        }
        /*
        if (properties.stream().allMatch(property -> {
            @Nullable Color color = property.get("color", Color.class);
            if (color == null) return false;
            return color != event.getEntity().getColor();
        })) {
            return;
        }
         */

        ((Damageable) event.getEntity()).kill();
    }
}
