package io.github.arkobat.kolorkarl.collision.listener;

import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.common.Damageable;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.common.weapon.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BulletListener extends EventListener {

    @Override
    public void onEntityMove(EntityMoveEvent event) {
        if (!(event.getEntity() instanceof Bullet)) {
            return;
        }

        // This is trash
        List<Entity> list = new ArrayList<>(event.getEntity().getWorld().getEntities());
        List<Entity> toRemove = new LinkedList<>();
        for (Entity entity : list) {
            if (!(entity instanceof Damageable)) {
                toRemove.add(entity);
            }
            if (!event.getEntity().collides(entity)) {
                toRemove.add(entity);
            }
        }
        list.removeAll(toRemove);
        if (list.size() == 0) {
            return;
        }

        for (Entity entity : list) {
            if (entity.getColor() != event.getEntity().getColor()) continue;
            ((Damageable) entity).kill();
            event.getEntity().getWorld().removeEntity(event.getEntity());
            AssetLoader.getInstance("Collision").playSound("hit.wav", "sound");
            return;
        }
    }
}
