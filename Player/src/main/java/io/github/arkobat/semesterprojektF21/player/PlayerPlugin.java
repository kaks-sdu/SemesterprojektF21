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

    static final String MODULE_NAME = "Player";

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        // Add entities to the world
        PlayerImpl player = new PlayerImpl(world, new Color[]{Color.ORANGE, Color.GREEN, Color.BLUE}, new Location(42, 96));
        player.setColor(Color.BLUE);
        world.addEntity(player);
        System.out.println("Started Player plugin from module scope");

        // TODO: Setup tests for assets loading

        // Set animations TODO: Add all colour animations

        Animation idleAnimation = new Animation(MODULE_NAME, "idle/player_blue_idle.png", 2, 0.5f);
        Animation runAnimation = new Animation(MODULE_NAME, "run/player_blue_run.png", 4, 0.5f);

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
