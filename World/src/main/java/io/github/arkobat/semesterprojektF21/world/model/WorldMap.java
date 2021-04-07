package io.github.arkobat.semesterprojektF21.world.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.texture.AssetLoader;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static io.github.arkobat.semesterprojektF21.world.WorldPlugin.MODULE_NAME;

@Getter
public class WorldMap implements World {

    private final @NotNull String mapId;
    private final List<Entity> entities = new LinkedList<>();

    private @Nullable WorldMap nextMap;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Music music;

    WorldMap(@NotNull String mapId, @NotNull String mapFileName, @Nullable String music) {
        this.mapId = mapId;
        //TODO: Fix
        String mapPath = AssetLoader.getInstance().getFilePath(MODULE_NAME, "map/" + mapFileName + ".tmx");
        this.map = new TmxMapLoader().load(mapPath);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (music != null) {
            String musicPath = AssetLoader.getInstance().getFilePath(MODULE_NAME, "sound/" + music);
            this.music = Gdx.audio.newMusic(new FileHandle(musicPath));
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
                if (clazz.isInstance(entity)) {
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

    @Override
    public void update() {
        // Update camera view
        Optional<Entity> player = getEntities(Player.class).stream().findFirst();
        if (player.isPresent()) {
            Location loc = player.get().getLocation();
            Hitbox hb = player.get().getHitbox();
            camera.position.set(loc.getX() + hb.getWidth() / 2, loc.getY() + hb.getHeight() / 2, 0);
            camera.update();

            renderer.render();
            renderer.setView(camera);
            System.out.println("Updated map");
        } else System.out.println("No player found");
    }

}
