package io.github.arkobat.semesterprojektF21.common;

/*import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;*/
import org.jetbrains.annotations.Nullable;

/**
 * Represents a location
 */
/*@Getter
@Setter
@AllArgsConstructor*/
public class Location {

    private float x;
    private float y;
    private @Nullable Direction direction;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
