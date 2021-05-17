package io.github.arkobat.kolorkarl.enemy;

import io.github.arkobat.kolorkarl.common.Direction;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.Vector;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GameProcessingService;
import org.jetbrains.annotations.NotNull;

public class EnemyControlSystem implements GameProcessingService {

    private static final float acceleration = 150F;
    private static final float deacceleration = 250F;
    private static final float jumpAcceleration = 75F;
    private static final float gravity = 250F;
    private static final float maxAcceleration = 75F;
    private boolean pathFound = false;


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
            enemy.getAi().process();

            // Track player
            for (Entity player : world.getEntities(Player.class)) {
                //System.out.println("Player location: " + player.getLocation().getX() + ", " + player.getLocation().getY());
                enemy.getAi().gotoLocation(player.getLocation());
            }

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
            boolean flipped = enemy.getLocation().getDirection() == Direction.LEFT;
            if (velocity.getX() > 0 && flipped) {
                enemy.getCurrentAnimation().flip();
            } else if (velocity.getX() < 0 && !flipped) {
                enemy.getCurrentAnimation().flip();
            }
        }
    }

}
