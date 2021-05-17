package io.github.arkobat.kolorkarl.common;

/**
 * Represent the hitbox of objects, to check for collision
 */
public class Hitbox {

    private final float width;
    private final float height;

    private final float offsetX;
    private final float offsetY;

    /**
     * Creates a HitBox without any offset
     * @param width The width of the object
     * @param height The height of the object
     */
    public Hitbox(float width, float height) {
        this(width, height, 0, 0);
    }

    /**
     * Creates a HitBox with the possibility to create an offset.
     * If the object do not have any offset, take a look at {@link #Hitbox(float, float)}
     * @param width The width of the object
     * @param height The height of the object
     * @param offsetX The offset on the X axis
     * @param offsetY The offset on the Y axis
     */
    public Hitbox(float width, float height, float offsetX, float offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * How width an object is
     * @return The width of the object
     */
    public float getWidth() {
        return width;
    }

    /**
     * How high an object is
     * @return the height of the object
     */
    public float getHeight() {
        return height;
    }

    /**
     * Objects textures can have different offsets, which is used to display the asset in the right location.
     * If a asset have 3 invisible pixels before the {@link Hitbox} actually starts, the offset should be <code>-3</code>
     * @return The objects offset on the X axis
     */
    public float getOffsetX() {
        return offsetX;
    }

    /**
     * Like {@link #getOffsetX()}, this is used to calculate the offset on the Y axis
     * @return The objects offset on the Y axis
     */
    public float getOffsetY() {
        return offsetY;
    }

}
