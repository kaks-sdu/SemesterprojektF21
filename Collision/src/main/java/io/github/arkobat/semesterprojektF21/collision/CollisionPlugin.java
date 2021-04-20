package io.github.arkobat.semesterprojektF21.collision;

import io.github.arkobat.semesterprojektF21.collision.listener.MoveListener;
import io.github.arkobat.semesterprojektF21.collision.listener.SpikeListener;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.common.event.EventManager;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CollisionPlugin implements GamePluginService {

    private final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        this.listeners.add(new MoveListener());
        this.listeners.add(new SpikeListener());
        for(EventListener listener : listeners){
            EventManager.registerListener(listener);
        }
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        for(EventListener listener : listeners){
            EventManager.unregisterListener(listener);
        }
    }

}
