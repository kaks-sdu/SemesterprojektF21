package io.github.arkobat.kolorkarl.collision.listener;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.kolorkarl.collision.CollisionHandler;
import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Damageable;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.event.EntityColorChangeEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.commonWorld.ExtendedWorld;

import java.util.List;

public class ColorChangeListener extends EventListener {

    @Override
    public void onEntityColorChange(EntityColorChangeEvent event) {
        if (!(event.getEntity() instanceof Damageable)) {
            return;
        }

        World world = event.getEntity().getWorld();
        if (!(world instanceof ExtendedWorld)) {
            return;
        }
        final TiledMapTileLayer collisionLayer = ((ExtendedWorld) world).getCollisionLayer();

        List<MapProperties> propertiesList = CollisionHandler.getProperties(collisionLayer, event.getEntity().getLocation(), event.getEntity().getHitbox());
        for (MapProperties properties : propertiesList) {
            if (!properties.containsKey("collision")) {
                continue;
            }
            if (properties.containsKey("color")) {
                Color color = Color.valueOf(properties.get("color", String.class));
                if (color != Color.ALL && color != event.getNewColor()) {
                    continue;
                }
            }
            event.setCanceled(true);
            return;
        }

    }

}
