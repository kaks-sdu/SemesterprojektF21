package io.github.arkobat.semesterprojektF21;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import io.github.arkobat.semesterprojektF21.common.game.GamePostProcessingService;
import io.github.arkobat.semesterprojektF21.common.game.GameProcessingService;
import io.github.arkobat.semesterprojektF21.common.texture.ITextureRenderService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;


public class Game implements ApplicationListener {

    private static final List<GameProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<GamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static final List<ITextureRenderService> textureRenderList = new CopyOnWriteArrayList<>();
    private static OrthographicCamera cam;
    private static World world = new TempWorld();
    private static List<GamePostProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    private boolean created = false;
    private ShapeRenderer sr;
    private SpriteBatch spriteBatch;
    private Supplier<GameData> gameDataSupplier;

    public Game() {
        init();
    }

    private void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Group 1 Semester Project";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        gameDataSupplier = new Supplier<GameData>() {
            @Override
            public GameData get() {
                return new GameData(
                        Gdx.graphics.getDeltaTime(),
                        Gdx.graphics.getWidth(),
                        Gdx.graphics.getHeight(),
                        KeyController.getPressedKeys()
                );
            }
        };

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        this.spriteBatch = new SpriteBatch();
        GameData gameData = gameDataSupplier.get();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2F, gameData.getDisplayHeight() / 2F);
        cam.update();

        sr = new ShapeRenderer();

    //    Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
        System.out.println("Created game!");
        for (GamePluginService gamePluginService : gamePluginList) {
            System.out.println("Starting plugin " + gamePluginService.getClass());
            gamePluginService.start(gameData, world);
        }
        created = true;
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
    }

    private void update() {
        GameData gameData = gameDataSupplier.get();
        // Render
        for (ITextureRenderService textureRenderService : textureRenderList) {
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

    public void addTextureRenderService(ITextureRenderService eps) {
        System.out.println("Added texture render service");
        textureRenderList.add(eps);
    }

    public void removeTextureRenderService(ITextureRenderService eps) {
        textureRenderList.remove(eps);
    }

    public void addEntityProcessingService(GameProcessingService eps) {
        entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(GameProcessingService eps) {
        entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(GamePostProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(GamePostProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(GamePluginService plugin) {
        gamePluginList.add(plugin);
        GameData gameData = gameDataSupplier.get();
        System.out.println("Started plugin from core scope: " + plugin);

        if (created) {
            plugin.start(gameData, world);
            System.out.println("Reloaded!");
        }
    }

    public void removeGamePluginService(GamePluginService plugin) {
        gamePluginList.remove(plugin);
        GameData gameData = gameDataSupplier.get();
        plugin.stop(gameData, world);
    }

}
