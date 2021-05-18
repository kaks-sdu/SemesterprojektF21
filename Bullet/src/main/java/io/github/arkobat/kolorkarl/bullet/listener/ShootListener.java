package io.github.arkobat.kolorkarl.bullet.listener;

import io.github.arkobat.kolorkarl.bullet.BulletControlSystem;
import io.github.arkobat.kolorkarl.bullet.model.BulletImpl;
import io.github.arkobat.kolorkarl.common.Direction;
import io.github.arkobat.kolorkarl.common.Hitbox;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.Vector;
import io.github.arkobat.kolorkarl.common.event.EntityShootEvent;
import io.github.arkobat.kolorkarl.common.event.EventListener;
import io.github.arkobat.kolorkarl.common.weapon.Bullet;

public class ShootListener extends EventListener {

    private final static float BULLET_SPEED = 150F;

    @Override
    public void onEntityShoot(EntityShootEvent event) {
        Location loc = event.getEntity().getLocation();
        Hitbox hb = event.getEntity().getHitbox();
        Direction direction = loc.getDirection();

        float x = loc.getX();
        if (direction == Direction.RIGHT) x += hb.getWidth() + 3;
        else x -= 3;
        float y = loc.getY() + hb.getOffsetY() + hb.getHeight() / 2;

        Bullet bullet = new BulletImpl(
                event.getEntity().getColor(),
                event.getEntity().getWorld(),
                new Location(x, y, direction),
                new Vector(direction == Direction.LEFT ? -BULLET_SPEED : BULLET_SPEED, 0)
        );

        event.getEntity().getWorld().addEntity(bullet);
        BulletControlSystem.assetLoader.playSound("shoot.wav", "sound");
    }

}
