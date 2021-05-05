package io.github.arkobat.semesterprojektF21.overlay;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import org.jetbrains.annotations.NotNull;

public class ColorOverlay {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Location location;
    private final Hitbox hitbox;
    private @NotNull Alignment alignment = Alignment.LEFT;

    public ColorOverlay(Location location, Hitbox hitbox) {
        this.location = location;
        this.hitbox = hitbox;
    }

    public void draw(Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.getRed(), color.getGreen(), color.getBlue(), 1f);
        Location loc = this.alignment.calculate(location, hitbox);
        shapeRenderer.rect(loc.getX(), loc.getY(), hitbox.getWidth(), hitbox.getHeight());
        shapeRenderer.end();
    }

    public void setAlignment(@NotNull Alignment alignment) {
        this.alignment = alignment;
    }

}
