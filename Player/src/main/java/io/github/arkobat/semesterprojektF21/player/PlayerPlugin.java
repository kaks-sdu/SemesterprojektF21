package io.github.arkobat.semesterprojektF21.player;

import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;

import java.util.Collection;

public class PlayerPlugin implements GamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        Player player = new PlayerImpl(new Color[]{Color.RED, Color.GREEN, Color.BLUE}, new Location(0, 500));
        world.addEntity(player);

        System.out.println("Started Player plugin from module scope");
    }

    @Override
    public void load(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        Collection<Entity> players = world.getEntities(Player.class);
        for (Entity player : players) {
            world.removeEntity(player);
        }
    }

}
