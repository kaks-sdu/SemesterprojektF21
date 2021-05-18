package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class EntityEvent extends Event {

    private final @NotNull Entity entity;

    public EntityEvent(@NotNull Entity entity) {
        this.entity = entity;
    }

    public @NotNull Entity getEntity() {
        return entity;
    }
}
