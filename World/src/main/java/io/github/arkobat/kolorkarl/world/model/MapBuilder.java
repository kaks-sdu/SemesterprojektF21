package io.github.arkobat.kolorkarl.world.model;

import lombok.Getter;

@Getter
public class MapBuilder {

    private String mapFileName;
    private String music;

    public MapBuilder setMapFileName(String mapFileName) {
        this.mapFileName = mapFileName;
        return this;
    }

    public MapBuilder setMusic(String music) {
        this.music = music;
        return this;
    }

    public WorldMap build() {
        return new WorldMap(this.mapFileName, this.music);
    }

}
