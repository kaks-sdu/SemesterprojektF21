package io.github.arkobat.semesterprojektF21.core.listener;

import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.common.event.LevelChangeEvent;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.core.Game;

public class LevelChangeListener extends EventListener {

    private final Game game;

    public LevelChangeListener(Game game) {
        this.game = game;
    }

    @Override
    public void onLevelChange(LevelChangeEvent event) {
        WorldTemp nextWorld = game.getWorld().getNextMap();
        if (nextWorld == null) {
            return;
        }
        game.getWorld().endMap();
        for (Entity player : game.getWorld().getEntities()) {
            player.setWorld(nextWorld);
            break;
        }
        game.setWorld(nextWorld);
        nextWorld.startMap();
        AssetLoader.getInstance("Core").playSound("portal.wav", "sound");
    }
}
