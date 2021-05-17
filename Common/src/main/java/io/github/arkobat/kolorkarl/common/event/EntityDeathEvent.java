package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityDeathEvent extends EntityEvent {

    public EntityDeathEvent(@NotNull Entity entity) {
        super(entity);
    }

}
