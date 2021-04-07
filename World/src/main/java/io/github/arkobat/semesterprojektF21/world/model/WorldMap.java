package io.github.arkobat.semesterprojektF21.world.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class WorldMap implements World {

    private final @NotNull String mapId;
    private final List<Entity> entities = new LinkedList<>();

    private @Nullable WorldMap nextMap;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Music music;

    WorldMap(@NotNull String mapId, @NotNull String mapFileName, @Nullable String music) {
        this.mapId = mapId;
        this.map = new TmxMapLoader().load("map/" + mapFileName + ".tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map);

        if (music != null) {
            this.music = Gdx.audio.newMusic(Gdx.files.internal("sound/" + music));
        }
    }

    @NotNull
    @Override
    public Collection<Entity> getEntities() {
        return this.entities;
    }

    @SafeVarargs
    @NotNull
    @Override
    public final <E extends Entity> Collection<Entity> getEntities(@NotNull Class<E>... entityTypes) {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : entities) {
            for (Class<E> clazz : entityTypes) {
                if (clazz == entity.getClass()) {
                    list.add(entity);
                    break;
                }
            }
        }
        return list;
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        this.entities.add(entity);
    }

    @Override
    public void removeEntity(@NotNull Entity entity) {
        this.entities.remove(entity);
    }

    public void startMap() {
        toggleMusic(true);
    }

    private void toggleMusic(boolean start) {
        if (this.music == null) return;
        if (start) {
            music.setVolume(0.1F);
            music.setLooping(true);
            music.play();
        } else {
            music.stop();
        }
    }

    public void endMap() {
        toggleMusic(false);
    }

}
