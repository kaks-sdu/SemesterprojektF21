package io.github.arkobat.kolorkarl.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Hitbox;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.entity.Player;

public class ColorHud {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final ColorBox prevColor, curColor, nextColor;

    public ColorHud() {
        float gameCenter = Gdx.graphics.getWidth() / 2f;
        float locY = Gdx.graphics.getHeight() - 5f;
        final float boxSpacing = 40f;

        this.prevColor = new ColorBox(new Location(gameCenter - boxSpacing, locY), new Hitbox(20f, 20f));
        this.curColor = new ColorBox(new Location(gameCenter, locY), new Hitbox(40f, 40f));
        this.nextColor = new ColorBox(new Location(gameCenter + boxSpacing, locY), new Hitbox(20f, 20f));
    }

    public void draw(Player player) {
        prevColor.draw(shapeRenderer, player.getPreviousColor());
        curColor.draw(shapeRenderer, player.getColor());
        nextColor.draw(shapeRenderer, player.getNextColor());
    }

    public void resize(float width, float height) {
        shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private static class ColorBox {

        private final Location location;
        private final Hitbox hitbox;

        public ColorBox(Location location, Hitbox hitbox) {
            this.location = Alignment.TOP_CENTER.calculate(location, hitbox);
            this.hitbox = hitbox;
        }

        public void draw(ShapeRenderer shapeRenderer, Color color) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(color.getRed(), color.getGreen(), color.getBlue(), 1f);
            shapeRenderer.rect(location.getX(), location.getY(), hitbox.getWidth(), hitbox.getHeight());
            shapeRenderer.end();
        }

    }

}
