package io.github.arkobat.semesterprojektF21.collision.listener;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.collision.CollisionHandler;
import io.github.arkobat.semesterprojektF21.common.Damageable;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.event.EntityMoveEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;

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

        if (CollisionHandler.getProperties(collisionLayer, event.getNewLocation(), event.getEntity().getHitbox())
                .stream()
                .noneMatch(property -> property.containsKey("spikes"))) {
            return;
        }

        ((Damageable) event.getEntity()).kill();
    }
}
