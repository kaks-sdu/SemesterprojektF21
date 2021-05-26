package io.github.arkobat.kolorkarl.world.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import io.github.arkobat.kolorkarl.commonWorld.ExtendedWorld;
import io.github.arkobat.kolorkarl.world.WorldPlugin;
import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.event.WorldStartEvent;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@Getter
public class WorldMap implements ExtendedWorld {

    private final @NotNull String mapFileName;
    private final EntityCache entityCache = new EntityCache();

    @Setter
    private @Nullable WorldMap nextMap;

    private final AssetLoader assetLoader = AssetLoader.getInstance(WorldPlugin.MODULE_NAME);
    private final TiledMap map;
    private TiledMapTileLayer collisionLayer;

    private Music music;

    private final Location playerStart;

    WorldMap(@NotNull String mapFileName, @Nullable String music) {
        this.mapFileName = mapFileName;
        String mapPath = assetLoader.getRawFilePath("map/" + mapFileName + ".tmx");
        this.map = new TmxMapLoader().load(mapPath);
        //TODO: Make AssetLoader handle loading maps

        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().endsWith("_map")) {
                this.collisionLayer = (TiledMapTileLayer) layer;
                break;
            }
        }
        if (music != null) {
            String musicPath = assetLoader.getRawFilePath("sound/" + music);
            this.music = Gdx.audio.newMusic(new FileHandle(musicPath));
        }

        int startX = map.getProperties().get("spawnX", int.class);
        int startY = map.getProperties().get("height", int.class) - map.getProperties().get("spawnY", int.class) - 1;
        this.playerStart = new Location(startX * 8, startY * 8);
    }

    @NotNull
    @Override
    public Collection<Entity> getEntities() {
        return this.entityCache.get();
    }

    @SafeVarargs
    @NotNull
    @Override
    public final <E extends Entity> Collection<Entity> getEntities(@NotNull Class<E>... entityTypes) {
        return this.entityCache.get(entityTypes);
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        this.entityCache.add(entity);
    }

    @Override
    public void removeEntity(@NotNull Entity entity) {
        this.entityCache.remove(entity);
    }

    @Override
    public @NotNull String getName() {
        return mapFileName;
    }

    @Override
    public void startMap() {
        for (Entity player : getEntities(Player.class)) {
            player.getLocation().setX(playerStart.getX());
            player.getLocation().setY(playerStart.getY());
        }
        toggleMusic(true);
        EventManager.callEvent(new WorldStartEvent(this));
    }

    private void toggleMusic(boolean start) {
        if (this.music == null) return;
        if (start) {
            if (music.isPlaying()) {
                return;
            }
            music.setVolume(AssetLoader.BACKGROUND_MUSIC);
            music.setLooping(true);
            music.play();
        } else {
            music.stop();
        }
    }

    @Override
    public void endMap() {
        toggleMusic(false);
    }

    @Override
    public TiledMapTileLayer getCollisionLayer() {
        return this.collisionLayer;
    }


    @Override
    public void dispose() {
        map.dispose();
    }

}
