package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityDeathEvent extends EntityEvent {

    public EntityDeathEvent(@NotNull Entity entity) {
        super(entity);
    }

}
