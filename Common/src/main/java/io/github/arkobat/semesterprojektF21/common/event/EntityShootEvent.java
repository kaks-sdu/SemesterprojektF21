package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityShootEvent extends EntityEvent {


    public EntityShootEvent(@NotNull Entity entity) {
        super(entity);
    }

    @Override
    public final @NotNull Entity getEntity() {
        return super.getEntity();
    }

}
