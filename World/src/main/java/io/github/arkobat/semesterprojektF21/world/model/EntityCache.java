package io.github.arkobat.semesterprojektF21.world.model;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EntityCache {

    private List<Entity> entities = new LinkedList<>();
    private Map<Object[], List<Entity>> cached = new HashMap<>();

    /**
     * Gets all the entities in the cache
     * @return A unmodifiable list of entities
     */
    @NotNull
    public Collection<Entity> get() {
        List<Entity> entities = this.cached.get(null);
        if (entities == null) {
            entities = Collections.unmodifiableList(this.entities);
            this.cached.put(null, entities);
        }
        return this.entities;
    }

    /**
     * Get the entities from the cache.
     * If it is the first time the method is called with the same args, the cache will be build.
     * Afterwards, the cache will grantee O(1) access to the list.
     *
     * @param entityTypes The types of entities
     * @param <E>         The class of the entity
     * @return A unmodifiable list of all entities with the specifed type
     */
    public <E extends Entity> List<Entity> get(@NotNull Class<E>... entityTypes) {
        if (entityTypes.length == 0) throw new IllegalArgumentException("No classes specified");
        List<Entity> entities = cached.get(entityTypes);
        if (entities == null) {
            entities = new LinkedList<>();
            for (Entity entity : this.entities) {
                for (Class<E> clazz : entityTypes) {
                    if (clazz.isInstance(entity)) {
                        entities.add(entity);
                        break;
                    }
                }
            }
            entities = Collections.unmodifiableList(entities);
            this.cached.put(entityTypes, entities);
        }
        return entities;
    }

    /**
     * Adds a new entity, and destroy the cache afterwards, ensuring the cache will stay up to date
     * When the cache is destroyed, it will auto generate on request.
     *
     * @param entity The new entity
     */
    public void add(Entity entity) {
        this.entities.add(entity);
        destroy();
    }

    /**
     * Removes an entity from the cache and destroy the cache afterwards
     * @param entity The entity to remove
     */
    public void remove(Entity entity) {
        this.entities.remove(entity);
        destroy();
    }

    /**
     * Destroy the entire cache
     */
    public void destroy() {
        this.cached.clear();
    }

    /**
     * Destroy part of the cache, referenced by the classes
     *
     * @param objects The classes of the types wanted removed
     */
    public <E extends Entity> void destroy(Class<E>... objects) {
        this.cached.remove(objects);
    }

}
