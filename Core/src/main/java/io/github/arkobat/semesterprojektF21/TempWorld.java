package io.github.arkobat.semesterprojektF21;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TempWorld implements World {

    private final List<Entity> entities = new LinkedList<>();

    @Override
    public Collection<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public <E extends Entity> Collection<Entity> getEntities(Class<E>... entityTypes) {
        return entities.stream().filter(entity -> Arrays.stream(entityTypes).anyMatch(clazz -> clazz == entity.getClass())).collect(Collectors.toList());
    }

    @Override
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

}
