package io.github.arkobat.semesterprojektF21.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.common.weapon.Bullet;
import io.github.arkobat.semesterprojektF21.bullet.model.BulletImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

import static io.github.arkobat.semesterprojektF21.bullet.BulletPlugin.MODULE_NAME;

public class BulletControlSystem implements GameProcessingService, TextureRenderService {

    public static final AssetLoader assetLoader = AssetLoader.getInstance(MODULE_NAME);

    @Override
    public void process(@NotNull GameData gameData, @NotNull World world) {

        Collection<BulletImpl> bullets = new LinkedList<>();
        for (Entity entity : world.getEntities(BulletImpl.class)) {
            bullets.add((BulletImpl) entity);
        }

        for (BulletImpl bullet : bullets) {
            if (System.currentTimeMillis() - bullet.getSpawnTime() > 5000L) {
                world.removeEntity(bullet);
                continue;
            }
            moveBullet(bullet, gameData.getDelta());
        }
    }

    private void moveBullet(Bullet bullet, float delta) {
        Location loc = bullet.getLocation();
        Vector vec = bullet.getVelocity();
        loc.setX((loc.getX() + vec.getX() * delta));
        loc.setY((loc.getY() + vec.getY() * delta));
    }

    @Override
    public void render(GameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(BulletImpl.class)) {
            BulletImpl bullet = (BulletImpl) entity;
            Location loc = bullet.getLocation();

            // Draw animation
            sb.draw(bullet.getAnimation().getFrame(), loc.getX() + bullet.getHitbox().getOffsetX(), loc.getY() + bullet.getHitbox().getOffsetY());
        }
    }

}
