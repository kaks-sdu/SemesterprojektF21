package io.github.arkobat.kolorkarl.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.kolorkarl.assetmanager.game.TextureRenderService;
import io.github.arkobat.kolorkarl.assetmanager.game.ExtendedGameData;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;

public class EnemyRender implements TextureRenderService {

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) entity;
            Location loc = enemy.getLocation();

            // Draw animation
            sb.draw(enemy.getCurrentAnimation().getFrame(), loc.getX() + entity.getHitbox().getOffsetX(), loc.getY() + entity.getHitbox().getOffsetY());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

}
