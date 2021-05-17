package io.github.arkobat.kolorkarl.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.common.Direction;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.Vector;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EntityShootEvent;
import io.github.arkobat.kolorkarl.common.event.EntityTurnEvent;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GameProcessingService;
import org.jetbrains.annotations.NotNull;

import static io.github.arkobat.kolorkarl.player.PlayerPlugin.MODULE_NAME;

public class PlayerControlSystem implements GameProcessingService {

    private final static AssetLoader assetLoader = AssetLoader.getInstance(MODULE_NAME);
    private static final float acceleration = 150F;
    private static final float deacceleration = 250F;
    private static final float jumpAcceleration = 80F;
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

                // Check collision X
                if (loc.getX() != oldX || loc.getY() != oldY) {
                    EntityMoveEvent event = new EntityMoveEvent(player, player.getLocation(), new Location(oldX, oldY));
                    EventManager.callEvent(event);
                }

                handleTeleport(player);

                // Process animation
                player.getCurrentAnimation().process(gameData);
            }
        }
    }

    private void handleControls(PlayerImpl player, float delta) {
        Vector velocity = player.getVelocity();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (velocity.getX() < maxAcceleration) {
                velocity.setX(Math.min(maxAcceleration, velocity.getX() + acceleration * delta));
            }
        } else if (velocity.getX() > 0) {
            velocity.setX(Math.max(0, velocity.getX() - deacceleration * delta));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (velocity.getX() > -maxAcceleration) {
                velocity.setX(Math.max(-maxAcceleration, velocity.getX() - acceleration * delta));
            }
        } else if (velocity.getX() < 0) {
            velocity.setX(Math.min(0, velocity.getX() + deacceleration * delta));
        }

        // Jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && player.getJumpCharges() > 0) {
            velocity.setY(jumpAcceleration);
            player.setJumpCharges(player.getJumpCharges() - 1);
            assetLoader.playSound("jump.wav", "sound");
        }

        //Dash
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
            float dashSpeed = maxAcceleration * 5;
            velocity.setX(player.getLocation().getDirection() == Direction.RIGHT ? dashSpeed : -dashSpeed);
            assetLoader.playSound("dash.wav", "sound");
        }
        if (velocity.getX() > maxAcceleration) {
            velocity.setX(Math.max(0, velocity.getX() - deacceleration * delta * 5));
        } else if (velocity.getX() < -maxAcceleration) {
            velocity.setX(Math.min(0, velocity.getX() + deacceleration * delta * 5));
        }

        Location loc = player.getLocation();
        loc.setX((loc.getX() + velocity.getX() * delta));
        loc.setY((loc.getY() + velocity.getY() * delta));

        // Shoot
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            EntityShootEvent shootEvent = new EntityShootEvent(player);
            EventManager.callEvent(shootEvent);
        }

        // Color change
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            player.nextColor();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            player.prevColor();
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
        boolean flipped = player.getLocation().getDirection() == Direction.LEFT;
        if (velocity.getX() > 0 && flipped) {
            flip(player, Direction.RIGHT);
        } else if (velocity.getX() < 0 && !flipped) {
            flip(player, Direction.LEFT);
        }

    }

    private void flip(PlayerImpl player, Direction direction) {
        EntityTurnEvent entityTurnEvent = new EntityTurnEvent(player, direction);
        EventManager.callEvent(entityTurnEvent);
        if (!entityTurnEvent.isCanceled()) {
            player.getLocation().setDirection(entityTurnEvent.getDirection());
            player.flip();
        }
    }

    private void handleTeleport(PlayerImpl player) {
        Location loc = player.getLocation();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            loc.setX(loc.getX() - 32);
            player.getVelocity().setX(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            loc.setX(loc.getX() + 32);

            player.getVelocity().setX(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            loc.setY(loc.getY() + 32);
            player.getVelocity().setY(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            loc.setY(loc.getY() - 32);
            player.getVelocity().setY(0);
        }
    }

}
