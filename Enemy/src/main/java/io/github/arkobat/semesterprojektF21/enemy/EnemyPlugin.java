package io.github.arkobat.semesterprojektF21.enemy;

import io.github.arkobat.semesterprojektF21.astar.AStar;
import io.github.arkobat.semesterprojektF21.astar.Node;
import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import io.github.arkobat.semesterprojektF21.common.texture.Animation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class EnemyPlugin implements GamePluginService {
    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        // Add entities to world
        Enemy enemy = new Enemy(world, new Color[]{Color.ORANGE, Color.GREEN, Color.BLUE}, new Location(42, 88));
        world.addEntity(enemy);

        // Set animations TODO: Add all colour animations
        String moduleName = "Enemy";
        Animation idleAnimation = new Animation(moduleName, "idle/enemy_0_blue_idle.png", 2, 0.5f);
        Animation runAnimation = new Animation(moduleName, "run/enemy_0_blue_run.png", 4, 0.5f);

        enemy.addAnimation("idle", idleAnimation);
        enemy.addAnimation("run", runAnimation);

        // Set current animation
        enemy.setCurrentAnimation(enemy.getAnimation("idle"));

        System.out.println("Started enemy plugin");

        System.out.println("Printing 2d map representation");
        AStar aStar = new AStar(enemy);
        enemy.setAi(aStar);

        enemy.getAi().gotoLocation(new Location(400, 88)); // Go right
        //enemy.getAi().gotoLocation(new Location(24, 120)); // Jump to moving platform


        //enemy.getAi().gotoLocation(new Location(21, 11));
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        // Remove entities
        Collection<Entity> enemies = world.getEntities(Enemy.class);
        for (Entity enemy : enemies) {
            world.removeEntity(enemy);
        }
        System.out.println("Stopped enemy plugin");
    }
}