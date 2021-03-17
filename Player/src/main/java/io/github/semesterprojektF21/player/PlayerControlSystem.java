package io.github.semesterprojektF21.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.common.game.Key;
import io.github.semesterprojektF21.common.texture.ITextureRenderService;

public class PlayerControlSystem implements GameProcessingService, ITextureRenderService {

    private static final float acceleration = 3;
    private static final float deacceleration = 0.5F;
    private static final float jumpAcceleration = 300;
    private static final float gravity = 0.5F;
    private static final float maxAcceleration = 100;

    @Override
    public void process(GameData gameData, World world) {
        world.getEntities(Player.class).stream().filter(player -> player instanceof PlayerImpl).map(player -> (PlayerImpl) player).forEach(player -> {
            final Location loc = player.getLocation();
            final float delta = gameData.getDelta();

            float oldX = loc.getX();
            float oldY = loc.getY();

            // Apply gravity
            Vector velocity = player.getVelocity();
            velocity.setY(velocity.getY() - gravity);

            handleControls(gameData, player);

            loc.setX((float) (loc.getX() + velocity.getX() * delta));
            loc.setY((float) (loc.getY() + velocity.getY() * delta));

            // Check collision X
            handleCollision(player, oldX, oldY);
        });
    }

    private void handleControls(GameData data, PlayerImpl player) {
        Vector velocity = player.getVelocity();

        if (data.isPressed(Key.RIGHT)) {
            velocity.setX(Math.min(maxAcceleration, velocity.getX() + acceleration));
        } else if (velocity.getX() > 0) {
            velocity.setX(Math.max(0, velocity.getX() - deacceleration));
        }
        if (data.isPressed(Key.LEFT)) {
            velocity.setX(Math.max(-maxAcceleration, velocity.getX() - acceleration));
        } else if (velocity.getX() < 0) {
            velocity.setX(Math.min(0, velocity.getX() + deacceleration));
        }

        if (data.isPressed(Key.JUMP) && player.getJumpCharges() > 0) {
            player.setJumpCharges(player.getJumpCharges() - 1);
            velocity.setY(jumpAcceleration);
        }
    }

    private void handleCollision(PlayerImpl player, float oldX, float oldY) {
        Location loc = player.getLocation();
        Hitbox hitbox = player.getHitbox();
/*
        // Ensure the player is within game borders
        if (loc.getX() < 0) {
            loc.setX(0);
        } else if (loc.getX() + hitbox.getWidth() > collisionLayer.getWidth() * collisionLayer.getTileWidth()) {
            loc.setX(collisionLayer.getWidth() * collisionLayer.getTileWidth() - hitbox.getWidth());
        }
        if (loc.getY() < 0) {
            loc.setY(0);
        } else if (loc.getY() + hitbox.getHeight() > collisionLayer.getHeight() * collisionLayer.getTileHeight()) {
            loc.setY(collisionLayer.getHeight() * collisionLayer.getTileHeight() - hitbox.getHeight());
        }

        boolean blocked = false;

        // Check X collision;
        if (player.getVelocity().getX() > 0) {
            int cellX = (int) (loc.getX() + hitbox.getWidth()) / collisionLayer.getTileWidth();
            int cellY = (int) (loc.getY() + 0.1) / collisionLayer.getTileHeight();
            while (cellY * collisionLayer.getTileHeight() < loc.getY() + hitbox.getHeight()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellY++;
            }

        } else if (player.getVelocity().getX() < 0) {
            int cellX = (int) loc.getX() / collisionLayer.getTileWidth();
            int cellY = (int) (loc.getY() + 0.1) / collisionLayer.getTileHeight();
            while (cellY * collisionLayer.getTileHeight() < loc.getY() + hitbox.getHeight()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellY++;
            }
        }

        if (blocked) {
            loc.setX(oldX);
            player.getVelocity().setX(0);
            blocked = false;
        }

        // Check collision Y
        if (player.getVelocity().getY() > 0) {
            int cellX = (int) loc.getX() / collisionLayer.getTileWidth();
            int cellY = (int) (loc.getY() + hitbox.getHeight()) / collisionLayer.getTileHeight();
            while (cellX * collisionLayer.getTileWidth() < loc.getX() + hitbox.getWidth()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellX++;
            }

        } else if (velocity.getY() < 0) {
            int cellX = (int) loc.getX() / collisionLayer.getTileWidth();
            int cellY = (int) loc.getY() / collisionLayer.getTileHeight();
            while (cellX * collisionLayer.getTileWidth() < loc.getX() + hitbox.getWidth()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellX++;
            }
        }

        if (blocked) {
            player.getVelocity().setY(0);
            loc.setY(oldY);
            player.setJumpCharges(2);
        }
    }


    private boolean checkCollision(int x, int y) {

        try {
            TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
            TiledMapTile tile = cell.getTile();
            MapProperties properties = tile.getProperties();
            return (properties.containsKey("collision"));
        } catch (NullPointerException ignored) {
            return true;
        }
         */
    }

    @Override
    public void render(GameData gameData, World world, SpriteBatch sb) {
        System.out.println("render");
    }

}

    /*

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(PlayerImpl.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            Animation animation = player.getPart(Animation.class);

            movingPart.setLeft(gameData.getKeys().isDown(Key.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(Key.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(Key.UP));

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


    @Override
    public void render(GameData gameData, World world, SpriteBatch sb) {
        sb.begin();
        for (Entity player : world.getEntities(PlayerImpl.class)) {
            System.out.println("Rendering player!");
            PositionPart positionPart = player.getPart(PositionPart.class);
            Animation animation = player.getPart(Animation.class);

            sb.draw(animation.getFrame(), positionPart.getX(), positionPart.getY());
            //System.out.println("Rendering player at X" + positionPart.getX() + ", Y" + positionPart.getY());
        }
    }
    }
*/

