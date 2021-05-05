package io.github.arkobat.semesterprojektF21.core;

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
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.event.EventManager;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import io.github.arkobat.semesterprojektF21.common.game.GamePostProcessingService;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldLoader;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.core.listener.LevelChangeListener;
import lombok.Getter;
import lombok.Setter;

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
    private static List<GamePostProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    @Setter
    @Getter
    private WorldTemp world;
    private boolean created = false;
    private SpriteBatch spriteBatch;
    private Supplier<GameData> gameDataSupplier;
    private Supplier<ExtendedGameData> extendedGameDataSupplier;

    public Game() {
        init();
        EventManager.registerListener(new LevelChangeListener(this));
    }

    private void init() {
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

        gameDataSupplier = () -> new GameData(
                Gdx.graphics.getDeltaTime()
        );

        extendedGameDataSupplier = () -> new ExtendedGameData(
                Gdx.graphics.getDeltaTime(),
                renderer,
                camera,
                viewport
        );

        new LwjglApplication(this, config);
    }

    @Override
    public void create() {
        this.spriteBatch = new SpriteBatch();

        System.out.println("Created game!");

        Optional<WorldLoader> worldLoader = worldLoaders.stream().findFirst();
        if (!worldLoader.isPresent()) {
            throw new IllegalStateException("Could not load world");
        }
        GameData gameData = gameDataSupplier.get();
        world = worldLoader.get().start(gameData);

        this.renderer = new OrthogonalTiledMapRenderer(world.getMap());
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




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

        // Post Update
        for (GamePostProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }

    }


    @Override
    public void resize(int width, int height) {
        final float mapZoom = 5.0F;
        camera.viewportWidth = width / mapZoom;
        camera.viewportHeight = height / mapZoom;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @SuppressWarnings("unused")
    public void removeWorldLoaderService(WorldLoader eps) {
        worldLoaders.remove(eps);
    }

    @SuppressWarnings("unused")
    public void addWorldLoaderService(WorldLoader eps) {
        System.out.println("Added World Loader " + eps.getClass().getName());
        worldLoaders.add(eps);
        if (created) {
            eps.start(gameDataSupplier.get());
        }
    }

    @SuppressWarnings("unused")
    public void addTextureRenderService(TextureRenderService eps) {
        System.out.println("Added texture render service " + eps.getClass().getName());
        textureRenderList.add(eps);
    }

    @SuppressWarnings("unused")
    public void removeTextureRenderService(TextureRenderService eps) {
        textureRenderList.remove(eps);
    }

    @SuppressWarnings("unused")
    public void addEntityProcessingService(GameProcessingService eps) {
        entityProcessorList.add(eps);
    }

    @SuppressWarnings("unused")
    public void removeEntityProcessingService(GameProcessingService eps) {
        entityProcessorList.remove(eps);
    }

    @SuppressWarnings("unused")
    public void addPostEntityProcessingService(GamePostProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    @SuppressWarnings("unused")
    public void removePostEntityProcessingService(GamePostProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    @SuppressWarnings("unused")
    public void addGamePluginService(GamePluginService plugin) {
        gamePluginList.add(plugin);
        GameData gameData = gameDataSupplier.get();
        System.out.println("Started plugin from core scope: " + plugin);

        if (created) {
            plugin.start(gameData, world);
            System.out.println("Reloaded!");
        }
    }

    @SuppressWarnings("unused")
    public void removeGamePluginService(GamePluginService plugin) {
        gamePluginList.remove(plugin);
        GameData gameData = gameDataSupplier.get();
        plugin.stop(gameData, world);
    }

}
