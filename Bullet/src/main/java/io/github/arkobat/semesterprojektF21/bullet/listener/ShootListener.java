package io.github.arkobat.semesterprojektF21.bullet.listener;

import io.github.arkobat.semesterprojektF21.common.Direction;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.Vector;
import io.github.arkobat.semesterprojektF21.common.event.EntityShootEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.common.weapon.Bullet;
import io.github.arkobat.semesterprojektF21.bullet.model.BulletImpl;

public class ShootListener extends EventListener {

    private final static float BULLET_SPEED = 150F;

    @Override
    public void onEntityShoot(EntityShootEvent event) {
        Location loc = event.getEntity().getLocation();
        Bullet bullet = new BulletImpl(
                event.getEntity().getColor(),
                event.getEntity().getWorld(),
                new Location(loc.getX(), loc.getY()),
                new Vector(event.getEntity().getLocation().getDirection() == Direction.LEFT ? -BULLET_SPEED : BULLET_SPEED, 0)
        );

        event.getEntity().getWorld().addEntity(bullet);
    }

}
