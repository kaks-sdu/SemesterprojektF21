package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class EntityEvent extends Event {

    private final @NotNull Entity entity;

}
