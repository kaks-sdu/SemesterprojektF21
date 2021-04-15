package io.github.arkobat.semesterprojektF21.player;

import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import io.github.arkobat.semesterprojektF21.common.texture.Animation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PlayerPlugin implements GamePluginService {


    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        // Add entities to the world
        PlayerImpl player = new PlayerImpl(world, new Color[]{Color.RED, Color.GREEN, Color.BLUE}, new Location(42, 96));
        world.addEntity(player);
        System.out.println("Started Player plugin from module scope");

        // TODO: Setup tests for assets loading

        // Set animations TODO: Add all colour animations
        String moduleName = "Player";
        Animation idleAnimation = new Animation(moduleName, "idle/player_blue_idle.png", 2, 0.5f, -4, 0);
        Animation runAnimation = new Animation(moduleName, "run/player_blue_run.png", 4, 0.5f, -4, 0);

        player.addAnimation("idle", idleAnimation);
        player.addAnimation("run", runAnimation);

        // Set current animation
        player.setCurrentAnimation(player.getAnimation("run"));
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        // Remove entities
        Collection<Entity> players = world.getEntities(Player.class);
        for (Entity player : players) {
            world.removeEntity(player);
        }
    }

}
