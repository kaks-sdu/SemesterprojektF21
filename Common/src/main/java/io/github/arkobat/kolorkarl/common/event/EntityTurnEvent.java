package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.Direction;
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
