package io.github.arkobat.semesterprojektF21;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import io.github.arkobat.semesterprojektF21.common.game.GamePostProcessingService;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.common.texture.TextureRenderService;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldLoader;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;


public class Game implements ApplicationListener {

    private static final List<GameProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<GamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static final List<TextureRenderService> textureRenderList = new CopyOnWriteArrayList<>();
    private static final List<WorldLoader> worldLoaders = new CopyOnWriteArrayList<>();
    private static List<GamePostProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private WorldTemp world;
    private boolean created = false;
    private SpriteBatch spriteBatch;
    private Supplier<GameData> gameDataSupplier;


    public Game() {
        init();
    }

    private void init() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Group 1 Semester Project";

        config.width = 480;
        config.height = 270;

        config.useGL30 = false;
        config.useHDPI = false;
        config.forceExit = true;
        config.vSyncEnabled = false;

        config.backgroundFPS = -1;
        config.foregroundFPS = -1;

        gameDataSupplier = () -> new GameData(
                Gdx.graphics.getDeltaTime(),
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );

        new LwjglApplication(this, config);
    }

    @Override
    public void create() {
        GameData gameData = gameDataSupplier.get();

        this.spriteBatch = new SpriteBatch();

        System.out.println("Created game!");

        worldLoaders.forEach(loader -> loader.start(gameData));
        Optional<WorldLoader> worldLoader = worldLoaders.stream().findFirst();
        if (!worldLoader.isPresent()) {
            throw new IllegalStateException("Could not load world");
        }
        world = worldLoader.get().start(gameData);

        for (GamePluginService gamePluginService : gamePluginList) {
            System.out.println("Starting plugin " + gamePluginService.getClass());
            gamePluginService.start(gameData, world);
        }
        created = true;
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0.054f, 0.027f, 0.105f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.world.update(this.spriteBatch);
        update();
    }

    private void update() {
        GameData gameData = gameDataSupplier.get();
        // Render
        for (TextureRenderService textureRenderService : textureRenderList) {
            spriteBatch.begin();
            textureRenderService.render(gameData, world, spriteBatch);
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
        this.world.resize(width, height);
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
        System.out.println("Added texture render service");
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
