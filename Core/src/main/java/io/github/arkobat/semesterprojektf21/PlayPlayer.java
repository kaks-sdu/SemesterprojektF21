package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlayPlayer extends Sprite {

    private static final float acceleration = 3;
    private static final float deacceleration = 0.5F;
    private static final float jumpAcceleration = 300;
    private static final float gravity = 0.5F;
    private static final float maxAcceleration = 100;

    private final TiledMapTileLayer collisionLayer;
    private Vector velocity = new Vector();
    private int jumpCharges = 0;

    public PlayPlayer(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        setY(500);
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta) {
        float oldX = getX();
        float oldY = getY();

        // Apply gravity
        velocity.setY(velocity.getY() - gravity);

        handleControls();

        setX((float) (getX() + velocity.getX() * delta));
        setY((float) (getY() + velocity.getY() * delta));

        // Check collision X
        handleCollision(oldX, oldY);

    }

    private void handleControls() {
        if (Key.RIGHT.isPressed()) {
            velocity.setX(Math.min(maxAcceleration, velocity.getX() + acceleration));
        } else if (velocity.getX() > 0) {
            velocity.setX(Math.max(0, velocity.getX() - deacceleration));
        }
        if (Key.LEFT.isPressed()) {
            velocity.setX(Math.max(-maxAcceleration, velocity.getX() - acceleration));
        } else if (velocity.getX() < 0) {
            velocity.setX(Math.min(0, velocity.getX() + deacceleration));
        }

        if (Key.JUMP.isJustPressed() && jumpCharges > 0) {
            jumpCharges--;
            velocity.setY(jumpAcceleration);
        }
    }

    private void handleCollision(float oldX, float oldY) {

        // Ensure the player is within game borders
        if (getX() < 0) {
            setX(0);
        } else if (getX() + getWidth() > collisionLayer.getWidth() * collisionLayer.getTileWidth()) {
            setX(collisionLayer.getWidth() * collisionLayer.getTileWidth() - getWidth());
        }
        if (getY() < 0) {
            setY(0);
        } else if (getY() + getHeight() > collisionLayer.getHeight() * collisionLayer.getTileHeight()) {
            setY(collisionLayer.getHeight() * collisionLayer.getTileHeight() - getHeight());
        }

        boolean blocked = false;

        // Check X collision;
        if (velocity.getX() > 0) {
            int cellX = (int) (getX() + getWidth()) / collisionLayer.getTileWidth();
            int cellY = (int) (getY() + 0.1) / collisionLayer.getTileHeight();
            while (cellY * collisionLayer.getTileHeight() < getY() + getHeight()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellY++;
            }

        } else if (velocity.getX() < 0) {
            int cellX = (int) getX() / collisionLayer.getTileWidth();
            int cellY = (int) (getY() + 0.1) / collisionLayer.getTileHeight();
            while (cellY * collisionLayer.getTileHeight() < getY() + getHeight()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellY++;
            }
        }

        if (blocked) {
            setX(oldX);
            velocity.setX(0);
            blocked = false;
        }

        // Check collision Y
        if (velocity.getY() > 0) {
            int cellX = (int) getX() / collisionLayer.getTileWidth();
            int cellY = (int) (getY() + getHeight()) / collisionLayer.getTileHeight();
            while (cellX * collisionLayer.getTileWidth() < getX() + getWidth()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellX++;
            }

        } else if (velocity.getY() < 0) {
            int cellX = (int) getX() / collisionLayer.getTileWidth();
            int cellY = (int) getY() / collisionLayer.getTileHeight();
            while (cellX * collisionLayer.getTileWidth() < getX() + getWidth()) {
                if (checkCollision(cellX, cellY)) blocked = true;
                cellX++;
            }
        }

        if (blocked) {
            velocity.setY(0);
            setY(oldY);
            jumpCharges = 2;
        }
    }


    private boolean checkCollision(int x, int y) {
        try {
            TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
            TiledMapTile tile = cell.getTile();
            MapProperties properties = tile.getProperties();
            return (properties.containsKey("collision"));
        } catch (NullPointerException ignored) {
            return true;
        }
    }

}
