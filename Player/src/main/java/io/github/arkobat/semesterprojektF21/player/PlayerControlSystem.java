package io.github.arkobat.semesterprojektF21.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.common.game.Key;
import io.github.arkobat.semesterprojektF21.common.texture.TextureRenderService;
import org.jetbrains.annotations.NotNull;

public class PlayerControlSystem implements GameProcessingService, TextureRenderService {

    private static final float acceleration = 3;
    private static final float deacceleration = 0.5F;
    private static final float jumpAcceleration = 300;
    private static final float gravity = 0.5F;
    private static final float maxAcceleration = 100;
    private Texture texture;

    public PlayerControlSystem() {

        /*String jarUrl = java.nio.file.Paths.get(new File("").getAbsolutePath(), "target", "Player-1.0-SNAPSHOT.jar!", "player.png").toString();
        jarUrl = jarUrl.replace("runner", "" + "Player").replace('\\', '/');

        // Load the texture
        AssetLoader.getInstance().load(jarUrl, Texture.class);
        AssetLoader.getInstance().finishLoading();

        texture = AssetLoader.getInstance().get(jarUrl, Texture.class);*/


        // texture = AssetLoader.getInstance().loadTexture("Player", "player.png");
    }

    @Override
    public void process(@NotNull GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (player instanceof PlayerImpl) {
                PlayerImpl player1 = (PlayerImpl) player;
                final Location loc = player1.getLocation();
                final float delta = gameData.getDelta();

                float oldX = loc.getX();
                float oldY = loc.getY();

                // Apply gravity
                Vector velocity = player1.getVelocity();
                velocity.setY(velocity.getY() - gravity);

                handleControls(gameData, player1);

                loc.setX((float) (loc.getX() + velocity.getX() * delta));
                loc.setY((float) (loc.getY() + velocity.getY() * delta));

                // Check collision X
                handleCollision(player1, oldX, oldY);
            }
        }
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
        for (Entity player : world.getEntities(Player.class)) {
            Location loc = player.getLocation();
            //   sb.draw(texture, loc.getX(), loc.getY());
        }
        //System.out.println("Rendering");
    }

}
