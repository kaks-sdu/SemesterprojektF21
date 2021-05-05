package io.github.arkobat.semesterprojektF21.player;

import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Direction;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PlayerPlugin implements GamePluginService {

    static final String MODULE_NAME = "Player";

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        // Add entities to the world
        PlayerImpl player = new PlayerImpl(world, new Color[]{Color.ORANGE, Color.GREEN, Color.BLUE}, new Location(42, 96, Direction.RIGHT));
        player.setColor(Color.BLUE);
        world.addEntity(player);

        // TODO: Setup tests for assets loading
        // Set current animation
        player.setCurrentAnimation(player.getAnimation("idle"));

        System.out.println("Started Player plugin from module scope");
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
