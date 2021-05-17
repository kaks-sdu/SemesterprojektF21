package io.github.arkobat.kolorkarl.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.arkobat.kolorkarl.assetmanager.TextureRenderService;
import io.github.arkobat.kolorkarl.assetmanager.model.ExtendedGameData;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GamePluginService;
import io.github.arkobat.kolorkarl.common.game.GameProcessingService;
import io.github.arkobat.kolorkarl.commonWorld.WorldLoader;
import io.github.arkobat.kolorkarl.commonWorld.WorldTemp;
import io.github.arkobat.kolorkarl.core.listener.LevelChangeListener;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;


public class Game implements ApplicationListener {

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    private static final List<GameProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<GamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static final List<TextureRenderService> textureRenderList = new CopyOnWriteArrayList<>();
    private static final List<WorldLoader> worldLoaders = new CopyOnWriteArrayList<>();
    @Getter
    private WorldTemp world;
    private boolean created = false;
    private SpriteBatch spriteBatch;
    private Supplier<GameData> gameDataSupplier;
    private Supplier<ExtendedGameData> extendedGameDataSupplier;

    public Game() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Group 1 Semester Project";

        config.width = 800;
        config.height = 600;

        config.useGL30 = false;
        config.useHDPI = false;
        config.forceExit = true;
        config.vSyncEnabled = false;

        config.backgroundFPS = -1;
        config.foregroundFPS = -1;

        new LwjglApplication(this, config);
    }

    @Override
    public void create() {
        System.out.println("Created game!");

        gameDataSupplier = () -> new GameData(
                Gdx.graphics.getDeltaTime()
        );

        extendedGameDataSupplier = () -> new ExtendedGameData(
                Gdx.graphics.getDeltaTime(),
                renderer,
                camera,
                viewport
        );

        EventManager.registerListener(new LevelChangeListener(this));

        Optional<WorldLoader> worldLoader = worldLoaders.stream().findFirst();
        if (!worldLoader.isPresent()) {
            throw new IllegalStateException("Could not load world");
        }
        GameData gameData = gameDataSupplier.get();
        world = worldLoader.get().start(gameData);

        this.renderer = new OrthogonalTiledMapRenderer(world.getMap());
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.spriteBatch = new SpriteBatch();
        this.spriteBatch.setProjectionMatrix(camera.combined);

        for (GamePluginService gamePluginService : gamePluginList) {
            System.out.println("Starting plugin " + gamePluginService.getClass());
            gamePluginService.start(gameData, world);
        }
        created = true;
        world.startMap();
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0.054f, 0.027f, 0.105f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.world.update(extendedGameDataSupplier.get(), this.spriteBatch);
        this.renderer.render();
        this.renderer.setView(camera);
        update();
    }

    private void update() {
        if (!created) {
            return;
        }
        GameData gameData = gameDataSupplier.get();
        ExtendedGameData extendedGameData = extendedGameDataSupplier.get();
        // Render
        for (TextureRenderService textureRenderService : textureRenderList) {
            spriteBatch.begin();
            textureRenderService.render(extendedGameData, world, spriteBatch);
            spriteBatch.end();
        }

        // Update
        for (GameProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }
    }

    @Override
    public void resize(int width, int height) {
        final float mapZoom = 5.0F;
        camera.viewportWidth = width / mapZoom;
        camera.viewportHeight = height / mapZoom;

        for (TextureRenderService service : textureRenderList) {
            service.resize(width, height);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        for (TextureRenderService service : textureRenderList) {
            service.dispose();
        }
    }

    public void setWorld(WorldTemp world) {
        this.world = world;
        this.renderer.setMap(world.getMap());
    }

    @SuppressWarnings("unused")
    public void removeWorldLoaderService(WorldLoader eps) {
        worldLoaders.remove(eps);
        System.out.println("Removed World Loader " + eps.getClass().getName());
    }

    @SuppressWarnings("unused")
    public void addWorldLoaderService(WorldLoader eps) {
        worldLoaders.add(eps);
        if (created) {
            eps.start(gameDataSupplier.get());
            System.out.println("Reloaded World Loader " + eps.getClass().getName());
        } else {
            System.out.println("Started World Loader " + eps.getClass().getName());
        }

    }

    @SuppressWarnings("unused")
    public void addTextureRenderService(TextureRenderService eps) {
        textureRenderList.add(eps);
        System.out.println("Added TextureRenderService plugin: " + eps.getClass().getName());
    }

    @SuppressWarnings("unused")
    public void removeTextureRenderService(TextureRenderService eps) {
        textureRenderList.remove(eps);
        System.out.println("Removed TextureRenderService plugin: " + eps.getClass().getName());
    }

    @SuppressWarnings("unused")
    public void addEntityProcessingService(GameProcessingService eps) {
        entityProcessorList.add(eps);
        System.out.println("Added EntityProcessingService plugin: " + eps.getClass().getName());
    }

    @SuppressWarnings("unused")
    public void removeEntityProcessingService(GameProcessingService eps) {
        entityProcessorList.remove(eps);
        System.out.println("Removed EntityProcessingService plugin: " + eps.getClass().getName());
    }



    @SuppressWarnings("unused")
    public void addGamePluginService(GamePluginService plugin) {
        gamePluginList.add(plugin);

        if (created) {
            GameData gameData = gameDataSupplier.get();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    plugin.start(gameData, world);
                }
            });
            System.out.println("Reloaded plugin: " + plugin.getClass().getName());
        } else {
            System.out.println("Started plugin: " + plugin.getClass().getName());
        }
    }

    @SuppressWarnings("unused")
    public void removeGamePluginService(GamePluginService plugin) {
        gamePluginList.remove(plugin);
        GameData gameData = gameDataSupplier.get();
        plugin.stop(gameData, world);
        System.out.println("Removed plugin: " + plugin.getClass().getName());
    }

}
