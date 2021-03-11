package io.github.arkobat.semesterprojektf21;

import io.github.arkobat.semesterprojektf21.entity.Entity;

import java.util.Collection;

public interface World {

    Collection<Entity> getEntities();

    void addEntity(Entity entity);

    void removeEntity(Entity entity);

}
