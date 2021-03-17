package io.github.arkobat.semesterprojektF21.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location {

    private float x;
    private float y;
    private Direction direction;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
