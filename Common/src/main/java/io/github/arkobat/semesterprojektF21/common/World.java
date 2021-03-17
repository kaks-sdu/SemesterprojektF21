package io.github.arkobat.semesterprojektF21.common;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;

import java.util.Collection;

public interface World {

    Collection<Entity> getEntities();

    void addEntity(Entity entity);

    void removeEntity(Entity entity);

}