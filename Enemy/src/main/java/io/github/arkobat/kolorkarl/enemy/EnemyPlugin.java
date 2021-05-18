package io.github.arkobat.kolorkarl.enemy;

import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GamePluginService;
import io.github.arkobat.kolorkarl.listener.WorldStartListener;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class EnemyPlugin implements GamePluginService {

    private WorldStartListener listener;

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        //enemy.getAi().gotoLocation(new Location(400, 88)); // Go right
        //enemy.getAi().gotoLocation(new Location(24, 120)); // Jump to moving platform
        //enemy.getAi().gotoLocation(new Location(21, 11));

        listener = new WorldStartListener();
        EventManager.registerListener(listener);

    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        // Remove entities
        Collection<Entity> enemies = world.getEntities(Enemy.class);
        for (Entity enemy : enemies) {
            world.removeEntity(enemy);
        }
        EventManager.unregisterListener(listener);
    }
}