package io.github.arkobat.semesterprojektF21.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.bullet.model.BulletImpl;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;

public class BulletRender implements TextureRenderService {

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(BulletImpl.class)) {
            BulletImpl bullet = (BulletImpl) entity;
            Location loc = bullet.getLocation();

            // Draw animation
            sb.draw(bullet.getAnimation().getFrame(), loc.getX() + bullet.getHitbox().getOffsetX(), loc.getY() + bullet.getHitbox().getOffsetY());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

}
