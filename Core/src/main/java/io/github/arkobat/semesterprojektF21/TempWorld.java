package io.github.arkobat.semesterprojektF21;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;

import java.util.*;
import java.util.stream.Collectors;

public class TempWorld implements World {

    private final List<Entity> entities = new LinkedList<>();

    @Override
    public Collection<Entity> getEntities() {
        return this.entities;
    }

    @Override
    public <E extends Entity> Collection<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : entities) {
            for (Class<E> clazz : entityTypes) {
                if (clazz == entity.getClass()) {
                    list.add(entity);
                    break;
                }
            }
        }
        return list;
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
