package io.github.arkobat.kolorkarl.collision;

import io.github.arkobat.kolorkarl.collision.listener.BulletListener;
import io.github.arkobat.kolorkarl.collision.listener.MoveListener;
import io.github.arkobat.kolorkarl.collision.listener.PortalListener;
import io.github.arkobat.kolorkarl.collision.listener.SpikeListener;
import io.github.arkobat.kolorkarl.collision.listener.*;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CollisionPlugin implements GamePluginService {

    private final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        this.listeners.add(new MoveListener());
        this.listeners.add(new EnemyListener());
        this.listeners.add(new PortalListener());
        this.listeners.add(new SpikeListener());
        this.listeners.add(new BulletListener());
        for (EventListener listener : this.listeners) {
            EventManager.registerListener(listener);
        }
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        for (EventListener listener : this.listeners) {
            EventManager.unregisterListener(listener);
        }
        this.listeners.clear();
    }

}
