package io.github.arkobat.semesterprojektF21.overlay;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import org.jetbrains.annotations.NotNull;

public class TextOverlay {

    private final BitmapFont font = new BitmapFont();
    private final Location location;
    private final Hitbox hitbox;
    private @NotNull Alignment alignment = Alignment.LEFT;

    public TextOverlay(Location location, Hitbox hitbox) {
        this.location = location;
        this.hitbox = hitbox;
    }

    public void draw(SpriteBatch sb, String text) {
        Location loc = alignment.calculate(this.location, this.hitbox);
        font.draw(sb, text, loc.getX(), loc.getY());
    }

    public void setAlignment(@NotNull Alignment alignment) {
        this.alignment = alignment;
    }

}
