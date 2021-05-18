package io.github.arkobat.kolorkarl.collision.listener;

import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.LivingEntity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;

import java.util.Collection;

public class EnemyListener extends EventListener {

    @Override
    public void onEntityMove(EntityMoveEvent event) {
        World world = event.getEntity().getWorld();

        Collection<Entity> players = world.getEntities(Player.class);
        if (players.isEmpty()) {
            return;
        }

        Player player = null;
        for (Entity entity : players) {
            player = (Player) entity;
            break;
        }
        if (player == null) {
            return;
        }

        for (Entity entity : world.getEntities(LivingEntity.class)) {
            if (entity instanceof Player) continue;
            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.collides(player)) {
                player.kill();
                return;
            }
        }
    }

}
