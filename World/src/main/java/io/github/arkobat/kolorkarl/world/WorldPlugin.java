package io.github.arkobat.kolorkarl.world;

import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.commonWorld.ExtendedWorld;
import io.github.arkobat.kolorkarl.commonWorld.WorldLoader;
import io.github.arkobat.kolorkarl.world.listener.DeathListener;
import org.jetbrains.annotations.NotNull;

public class WorldPlugin implements WorldLoader {

    private WorldController worldController;
    public static final String MODULE_NAME = "World";

    @NotNull
    @Override
    public ExtendedWorld start(@NotNull GameData gameData) {
        this.worldController = new WorldController();
        EventManager.registerListener(new DeathListener());
        return worldController.init();
    }

    @Override
    public ExtendedWorld getWorld(String name) {
        return worldController.getMap(name);
    }

    @Override
    public void stop() {
    }

}
