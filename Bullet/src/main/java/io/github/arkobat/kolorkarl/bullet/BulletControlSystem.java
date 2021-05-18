package io.github.arkobat.kolorkarl.bullet;

import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.bullet.model.BulletImpl;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.Vector;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.event.EntityMoveEvent;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GameProcessingService;
import io.github.arkobat.kolorkarl.common.weapon.Bullet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class BulletControlSystem implements GameProcessingService {

    public static final AssetLoader assetLoader = AssetLoader.getInstance(BulletPlugin.MODULE_NAME);

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

        EntityMoveEvent moveEvent = new EntityMoveEvent(bullet, new Location(loc.getX() + vec.getX() * delta, loc.getY() + vec.getY() * delta), loc);
        EventManager.callEvent(moveEvent);
        if (moveEvent.isCanceled()) {
            return;
        }

        loc.setX(moveEvent.getNewLocation().getX());
        loc.setY(moveEvent.getNewLocation().getY());
    }



}
