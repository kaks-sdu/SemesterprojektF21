package io.github.arkobat.semesterprojektF21.world.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class MapBuilder {

    private @NotNull String mapId;
    private @NotNull String mapFileName;
    private @Nullable String music;

    public MapBuilder setMapId(String mapId) {
        this.mapId = mapId;
        return this;
    }

    public MapBuilder setMapFileName(String mapFileName) {
        this.mapFileName = mapFileName;
        return this;
    }

    public MapBuilder setMusic(String music) {
        this.music = music;
        return this;
    }

    public WorldMap build() {
        return new WorldMap(this.mapId, this.mapFileName, this.music);
    }
}
