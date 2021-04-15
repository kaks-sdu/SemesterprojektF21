package io.github.arkobat.semesterprojektF21.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Hitbox {

    private final float width;
    private final float height;

    private final float offsetX;
    private final float offsetY;

    public Hitbox(float width, float height) {
        this(width, height, 0, 0);
    }

}
