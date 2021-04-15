package io.github.arkobat.semesterprojektF21.collision;

import io.github.arkobat.semesterprojektF21.collision.listener.MoveListener;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.event.EventManager;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

public class CollisionPlugin implements GamePluginService {

    private MoveListener moveListener;

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        this.moveListener = new MoveListener();
        EventManager.registerListener(this.moveListener);
        System.out.println("Started collision plugin");
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        EventManager.unregisterListener(this.moveListener);
        System.out.println("Stopped collision plugin");
    }
}
