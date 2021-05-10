package io.github.arkobat.semesterprojektF21.overlay.listener;

import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.event.EntityDeathEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.overlay.WorldPlugin;

public class DeathListener extends EventListener {

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            AssetLoader.getInstance(WorldPlugin.MODULE_NAME).playSound("death.wav", "sound");
            ((WorldTemp) event.getEntity().getWorld()).startMap();
        } else {
            event.getEntity().getWorld().removeEntity(event.getEntity());
        }
    }

}
