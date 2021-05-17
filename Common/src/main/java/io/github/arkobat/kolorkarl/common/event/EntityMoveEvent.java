package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.Location;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class EntityMoveEvent extends EntityEvent implements Cancelable {

    private final @NotNull Location newLocation;
    private final @NotNull Location oldLocation;
    private boolean canceled;

    public EntityMoveEvent(Entity entity, @NotNull Location newLocation, @NotNull Location oldLocation) {
        super(entity);
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
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
