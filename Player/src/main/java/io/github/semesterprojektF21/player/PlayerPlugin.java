package io.github.semesterprojektF21.player;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.semesterprojektF21.common.data.entityparts.LifePart;
import io.github.semesterprojektF21.common.data.entityparts.MovingPart;
import io.github.semesterprojektF21.common.data.entityparts.PositionPart;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import io.github.semesterprojektF21.common.texture.Animation;

import java.util.UUID;

public class PlayerPlugin implements GamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
        System.out.println("Started Player plugin from module scope");
    }

    private Entity createPlayerShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity player = new Player();
        player.setRadius(8);
        player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        player.add(new PositionPart(x, y, radians));
        UUID uuid = UUID.randomUUID();
        player.add(new LifePart(1));
        //AnimationPart animationPart = new AnimationPart("birdanimation.png", 3, 0.5f);
        //animationPart.flip();
        player.add(new Animation("birdanimation.png", 3, 0.5f));
        //TODO: Fix animation part
        System.out.println("Created Player and Animation");

        return player;
    }


    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }
}
