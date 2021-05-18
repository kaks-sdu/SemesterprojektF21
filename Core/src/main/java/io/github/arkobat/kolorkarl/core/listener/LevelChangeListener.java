package io.github.arkobat.kolorkarl.core.listener;

import io.github.arkobat.kolorkarl.core.Game;
import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.common.event.LevelChangeEvent;
import io.github.arkobat.kolorkarl.commonWorld.WorldTemp;

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
