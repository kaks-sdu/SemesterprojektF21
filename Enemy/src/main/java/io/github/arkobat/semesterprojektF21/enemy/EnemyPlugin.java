package io.github.arkobat.semesterprojektF21.enemy;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

public class EnemyPlugin implements GamePluginService {
    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        System.out.println("Started enemy plugin");
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        System.out.println("Stopped enemy plugin");
    }
}
