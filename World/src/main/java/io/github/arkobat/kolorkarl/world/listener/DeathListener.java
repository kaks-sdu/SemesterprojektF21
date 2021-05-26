package io.github.arkobat.kolorkarl.world.listener;

import io.github.arkobat.kolorkarl.commonWorld.ExtendedWorld;
import io.github.arkobat.kolorkarl.world.WorldPlugin;
import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EntityDeathEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;

public class DeathListener extends EventListener {

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            AssetLoader.getInstance(WorldPlugin.MODULE_NAME).playSound("death.wav", "sound");
            ((ExtendedWorld) event.getEntity().getWorld()).startMap();
        } else {
            event.getEntity().getWorld().removeEntity(event.getEntity());
        }
    }

}
