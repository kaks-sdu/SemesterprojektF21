package io.github.arkobat.semesterprojektf21.old.temp;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektf21.Key;
import io.github.arkobat.semesterprojektf21.Location;
import io.github.arkobat.semesterprojektf21.Vector;
import lombok.Getter;

public class PlayerHandler implements ApplicationListener {

    private final TiledMapTileLayer collissionLayer;
    @Getter
    PlayerImpl player;
    Texture playerImage;
    SpriteBatch batch;

    public PlayerHandler(TiledMapTileLayer collissionLayer) {
        this.collissionLayer = collissionLayer;
        player = new PlayerImpl();
        player.getLocation().setX(Gdx.graphics.getWidth() / 2F);
        player.getLocation().setX(Gdx.graphics.getHeight() / 2F);
        playerImage = new Texture(Gdx.files.internal("player.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Vector velocity = player.getVelocity();
        if (Key.LEFT.isPressed()) {
            velocity.setX(Math.max(-1, velocity.getX() - 0.2F));
        } else if (velocity.getX() < 0) {
            velocity.setX(Math.max(0, velocity.getX() + 0.2));
        }

        if (Key.RIGHT.isPressed()) {
            velocity.setX(Math.min(1, velocity.getX() + 0.2F));
        } else if (velocity.getX() > 0) {
            velocity.setX(Math.min(0, velocity.getX() - 0.2));
        }

        if (Key.JUMP.isPressed() && player.canJump) {
            velocity.setY(3);
            //    player.setCanJump(false);
        } else {
            velocity.setY(velocity.getY() - 0.005);
        }

        Location location = player.getLocation();
        float oldX = location.getX();
        float oldY = location.getY();

        float x = (float) (location.getX() + 200 * velocity.getX() * Gdx.graphics.getDeltaTime());
        float y = (float) (location.getY() + 200 * velocity.getY() * Gdx.graphics.getDeltaTime());

        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - 5));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - 10));
        if (y == 0) player.setCanJump(true);




        // Check collision X
        if (velocity.getX() < 0) {
            // Going left


        } else if (velocity.getX() > 0) {

        }

        // Check collision Y
        if (velocity.getY() > 0) {

        } else if (velocity.getY() < 0) {
         //  boolean blocked =  collissionLayer.getCell((int) x / collissionLayer.getTileWidth(), (int) y / collissionLayer.getTileHeight()).getTile().getProperties().containsKey("collision");
         //  if (blocked) y = oldY;
        }

        location.setX(x);
        location.setY(y);

        batch.begin();
        batch.draw(playerImage, player.getLocation().getX(), player.getLocation().getY());
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
