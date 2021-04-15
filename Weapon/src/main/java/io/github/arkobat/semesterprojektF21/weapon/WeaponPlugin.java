package io.github.arkobat.semesterprojektF21.weapon;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

public class WeaponPlugin implements GamePluginService {
    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        System.out.println("Started weapon plugin");
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        System.out.println("Stopped weapon plugin");
    }
}
