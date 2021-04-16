package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.Direction;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class EntityTurnEvent extends EntityEvent implements Cancelable {

    private final Direction direction;
    private boolean canceled;

    public EntityTurnEvent(@NotNull Entity entity, Direction direction) {
        super(entity);
        this.direction = direction;
        this.canceled = false;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
