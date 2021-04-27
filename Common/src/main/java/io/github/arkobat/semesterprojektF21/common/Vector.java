package io.github.arkobat.semesterprojektF21.common;

/*import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;*/

/**
 * A 2D vector
 */
/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor*/
public class Vector {

    private float x;
    private float y;

    public Vector() {
        this(0, 0);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
}
