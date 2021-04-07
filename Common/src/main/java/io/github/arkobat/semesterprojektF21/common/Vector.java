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

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
}
