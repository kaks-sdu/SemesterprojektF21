package io.github.arkobat.semesterprojektF21.enemy;

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

public class EnemyControlSystem implements GameProcessingService, TextureRenderService {

    private static final float acceleration = 150F;
    private static final float deacceleration = 250F;
    private static final float jumpAcceleration = 75F;
    private static final float gravity = 250F;
    private static final float maxAcceleration = 75F;


    @Override
    public void process(@NotNull GameData gameData, @NotNull World world) {
        for (Entity entity : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) entity;
            final Location loc = enemy.getLocation();
            final float delta = gameData.getDelta();

            float oldX = loc.getX();
            float oldY = loc.getY();

            // Apply gravity
            Vector velocity = enemy.getVelocity();
            velocity.setY(velocity.getY() - gravity * delta);

            // Handle pathfinding
            findPath(enemy, delta);

            loc.setX((float) (loc.getX() + velocity.getX() * delta));
            loc.setY((float) (loc.getY() + velocity.getY() * delta));

            // Check collision X
            EntityMoveEvent event = new EntityMoveEvent(enemy, enemy.getLocation(), new Location(oldX, oldY));
            EventManager.callEvent(event);

            // Process animation
            enemy.getCurrentAnimation().process(gameData);

            // Handle animations
            if (velocity.getX() > 0.1 || velocity.getX() < -0.1) {
                if (enemy.getCurrentAnimation() != enemy.getAnimation("run")) {
                    enemy.setCurrentAnimation(enemy.getAnimation("run"));
                }
            } else if (enemy.getCurrentAnimation() != enemy.getAnimation("idle")) {
                enemy.setCurrentAnimation(enemy.getAnimation("idle"));
            }

            // Handle flips
            boolean flipped = enemy.getCurrentAnimation().isFlipped();
            if (velocity.getX() > 0 && flipped) {
                enemy.getCurrentAnimation().flip();
            } else if (velocity.getX() < 0 && !flipped) {
                enemy.getCurrentAnimation().flip();
            }
        }
    }

    public void findPath(Enemy enemy, float delta){
        Vector velocity = enemy.getVelocity();

        //velocity.setX(Math.min(maxAcceleration, velocity.getX() + acceleration * delta));
    }
    
    private void moveRight(Vector velocity, float delta)
    {
        velocity.setX(Math.min(maxAcceleration, velocity.getX() + acceleration * delta));

        if (velocity.getX() > 0) {
            velocity.setX(Math.max(0, velocity.getX() - deacceleration * delta));
        }
    }

    @Override
    public void render(GameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) entity;
            Location loc = enemy.getLocation();

            // Draw animation
            sb.draw(enemy.getCurrentAnimation().getFrame(), loc.getX() + entity.getHitbox().getOffsetX(), loc.getY() + entity.getHitbox().getOffsetY());
        }
    }
}
