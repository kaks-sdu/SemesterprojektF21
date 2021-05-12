package io.github.arkobat.semesterprojektF21.overlay.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

import static io.github.arkobat.semesterprojektF21.overlay.WorldPlugin.MODULE_NAME;

@Getter
public class WorldMap implements WorldTemp {

    private final @NotNull String mapFileName;
    private final EntityCache entityCache = new EntityCache();

    @Setter
    private @Nullable WorldMap nextMap;

    private final AssetLoader assetLoader = AssetLoader.getInstance(MODULE_NAME);
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
    public void startMap() {
        for (Entity player : getEntities(Player.class)) {
            player.getLocation().setX(playerStart.getX());
            player.getLocation().setY(playerStart.getY());
        }
        toggleMusic(true);
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
    public void update(ExtendedGameData gameData, SpriteBatch spriteBatch) {
        // Update camera view
        Optional<Entity> player = getEntities(Player.class).stream().findFirst();
        if (player.isPresent()) {
            spriteBatch.setProjectionMatrix(gameData.getCamera().combined);

            Location loc = player.get().getLocation();
            Hitbox hb = player.get().getHitbox();

            gameData.getCamera().position.set(loc.getX() + (hb.getWidth() / 2F), loc.getY() + (hb.getHeight() / 2), 0);
            gameData.getCamera().update();
        }
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
