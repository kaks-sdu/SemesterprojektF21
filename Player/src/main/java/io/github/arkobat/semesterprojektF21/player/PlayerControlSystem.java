package io.github.arkobat.semesterprojektF21.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.event.EntityMoveEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventManager;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.common.texture.TextureRenderService;
import org.jetbrains.annotations.NotNull;

public class PlayerControlSystem implements GameProcessingService, TextureRenderService {

    private static final float acceleration = 150F;
    private static final float deacceleration = 250F;
    private static final float jumpAcceleration = 75F;
    private static final float gravity = 250F;
    private static final float maxAcceleration = 75F;

    @Override
    public void process(@NotNull GameData gameData, World world) {
        for (Entity entity : world.getEntities(Player.class)) {
            if (entity instanceof PlayerImpl) {
                PlayerImpl player = (PlayerImpl) entity;
                final Location loc = player.getLocation();
                final float delta = gameData.getDelta();

                float oldX = loc.getX();
                float oldY = loc.getY();

                // Apply gravity
                Vector velocity = player.getVelocity();
                velocity.setY(velocity.getY() - gravity * delta);

                handleControls(player, delta);

                loc.setX((float) (loc.getX() + velocity.getX() * delta));
                loc.setY((float) (loc.getY() + velocity.getY() * delta));

                // Check collision X
                EntityMoveEvent event = new EntityMoveEvent(player, player.getLocation(), new Location(oldX, oldY));
                EventManager.callEvent(event);

                // Process animation
                player.getCurrentAnimation().process(gameData);
            }
        }
    }

    private void handleControls(PlayerImpl player, float delta) {
        Vector velocity = player.getVelocity();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (player.getCurrentAnimation().isFlipped()) {
                player.getCurrentAnimation().flip();
            }

            velocity.setX(Math.min(maxAcceleration, velocity.getX() + acceleration * delta));
        } else if (velocity.getX() > 0) {
            velocity.setX(Math.max(0, velocity.getX() - deacceleration * delta));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (!player.getCurrentAnimation().isFlipped()) player.getCurrentAnimation().flip();

            velocity.setX(Math.max(-maxAcceleration, velocity.getX() - acceleration * delta));
        } else if (velocity.getX() < 0) {
            velocity.setX(Math.min(0, velocity.getX() + deacceleration * delta));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && player.getJumpCharges() > 0) {
            //   player.setJumpCharges(player.getJumpCharges() - 1);
            velocity.setY(jumpAcceleration);
        }

        // Handle animations
        if (velocity.getX() > 0.1 || velocity.getX() < -0.1) {
            if (player.getCurrentAnimation() != player.getAnimation("run")) {
                player.setCurrentAnimation(player.getAnimation("run"));
            }
        } else if (player.getCurrentAnimation() != player.getAnimation("idle")) {
            player.setCurrentAnimation(player.getAnimation("idle"));
        }

        // Handle flips
        boolean flipped = player.getCurrentAnimation().isFlipped();
        if (velocity.getX() > 0 && flipped) {
            player.getCurrentAnimation().flip();
        } else if (velocity.getX() < 0 && !flipped) {
            player.getCurrentAnimation().flip();
        }

        handleTeleport(player);
    }

    private void handleTeleport(Player player) {
        Location loc = player.getLocation();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            loc.setX(loc.getX() - 32);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            loc.setX(loc.getX() + 32);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            loc.setY(loc.getY() + 32);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            loc.setY(loc.getY() - 32);
        }
    }

    @Override
    public void render(GameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(Player.class)) {
            PlayerImpl player = (PlayerImpl) entity;
            Location loc = player.getLocation();

            // Draw animation
            sb.draw(player.getCurrentAnimation().getFrame(), loc.getX() + entity.getHitbox().getOffsetX(), loc.getY() + entity.getHitbox().getOffsetY());
        }
    }

}
