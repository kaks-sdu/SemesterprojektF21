package io.github.arkobat.kolorkarl.player;

import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Direction;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GamePluginService;
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
