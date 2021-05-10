package io.github.arkobat.semesterprojektF21.collision.listener;

import io.github.arkobat.semesterprojektF21.common.Damageable;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.event.EntityMoveEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.common.weapon.Bullet;

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
            ((Damageable) entity).kill();
        }
        event.getEntity().getWorld().removeEntity(event.getEntity());
        /*
        // This we like
        List betterList = event.getEntity().getWorld().getEntities()
                .stream()
                .filter(entity -> entity instanceof Damageable)
                .filter(entity -> event.getEntity().collides(entity))
                .collect(Collectors.toList());

         */

    }
}
