package io.github.arkobat.semesterprojektF21.world.model;

import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
/*import lombok.Getter;
import lombok.RequiredArgsConstructor;*/
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*@Getter
@RequiredArgsConstructor*/
public class WorldMap implements World {

    private final @NotNull String mapId;
    private final @Nullable WorldMap prevMap;
    private final @Nullable WorldMap nextMap;

    private final List<Entity> entities = new LinkedList<>();

    public WorldMap(@NotNull String mapId, @Nullable WorldMap prevMap, @Nullable WorldMap nextMap) {
        this.mapId = mapId;
        this.prevMap = prevMap;
        this.nextMap = nextMap;
    }

    public String getMapId() {
        return mapId;
    }

    @NotNull
    @Override
    public Collection<Entity> getEntities() {
        return this.entities;
    }

    @SafeVarargs
    @NotNull
    @Override
    public final <E extends Entity> Collection<Entity> getEntities(@NotNull Class<E>... entityTypes) {
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
    public void addEntity(@NotNull Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public void removeEntity(@NotNull Entity entity) {
        this.entities.remove(entity);
    }
}
