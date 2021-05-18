package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EntityColorChangeEvent extends EntityEvent implements Cancelable {

    private final Color oldColor;
    private final Color newColor;
    private boolean canceled;

    public EntityColorChangeEvent(@NotNull Entity entity, Color oldColor, Color newColor) {
        super(entity);
        this.newColor = newColor;
        this.oldColor = oldColor;
    }

    public Color getNewColor() {
        return newColor;
    }

    public Color getOldColor() {
        return oldColor;
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
