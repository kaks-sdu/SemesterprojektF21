package io.github.arkobat.semesterprojektF21.overlay.listener;

import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.event.EntityDeathEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;

public class DeathListener extends EventListener {

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            event.getEntity().getWorld().removeEntity(event.getEntity());
        } else {
            ((WorldTemp) event.getEntity().getWorld()).startMap();
        }
    }
}
