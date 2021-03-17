package io.github.semesterprojektF21.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.semesterprojektF21.common.data.Entity;
import io.github.semesterprojektF21.common.data.GameData;
import io.github.semesterprojektF21.common.data.GameKeys;
import io.github.semesterprojektF21.common.data.World;
import io.github.semesterprojektF21.common.data.entityparts.LifePart;
import io.github.semesterprojektF21.common.data.entityparts.MovingPart;
import io.github.semesterprojektF21.common.data.entityparts.PositionPart;
import io.github.semesterprojektF21.common.services.IEntityProcessingService;
import io.github.semesterprojektF21.common.texture.Animation;
import io.github.semesterprojektF21.common.texture.ITextureRenderService;

public class PlayerControlSystem implements IEntityProcessingService, ITextureRenderService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            Animation animation = player.getPart(Animation.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
            animation.process(gameData, player);
            //System.out.println("Processing player!");
            //TODO: Fix animation part
            updateShape(player);

        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    @Override
    public void render(GameData gameData, World world, SpriteBatch sb) {
        sb.begin();
        for (Entity player : world.getEntities(Player.class)) {
            System.out.println("Rendering player!");
            PositionPart positionPart = player.getPart(PositionPart.class);
            Animation animation = player.getPart(Animation.class);

            sb.draw(animation.getFrame(), positionPart.getX(), positionPart.getY());
            //System.out.println("Rendering player at X" + positionPart.getX() + ", Y" + positionPart.getY());
        }
    }
}
