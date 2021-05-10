package io.github.arkobat.semesterprojektF21.overlay;

import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;

import java.util.function.BiFunction;

public enum Alignment {

    TOP(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return new Location(location.getX(), location.getY() - hitbox.getHeight());
        }
    }),
    RIGHT(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return new Location(location.getX() - hitbox.getWidth(), location.getY());
        }
    }),
    BOTTOM(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return location;
        }
    }),
    LEFT(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return location;
        }
    }),

    TOP_RIGHT(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return new Location(location.getX() - hitbox.getWidth(), location.getY() - hitbox.getHeight());
        }
    }),
    BOTTOM_RIGHT(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return new Location(location.getX() - hitbox.getWidth(), location.getY());
        }
    }),
    BOTTOM_LEFT((new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return location;
        }
    })),
    TOP_LEFT(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return new Location(location.getX(), location.getY() - hitbox.getHeight());
        }
    }),

    TOP_CENTER(new BiFunction<Location, Hitbox, Location>() {
        @Override
        public Location apply(Location location, Hitbox hitbox) {
            return new Location(location.getX() - hitbox.getWidth() / 2, location.getY() - hitbox.getHeight());
        }
    });

    private final BiFunction<Location, Hitbox, Location> function;

    Alignment(BiFunction<Location, Hitbox, Location> function) {
        this.function = function;
    }

    public Location calculate(Location location, Hitbox hitbox) {
        return this.function.apply(location, hitbox);
    }

}
