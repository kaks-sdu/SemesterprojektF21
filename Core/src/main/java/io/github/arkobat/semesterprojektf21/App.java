package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.arkobat.semesterprojektf21.temp.PlayerHandler;

public class App implements ApplicationListener {

    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;
    private PlayerHandler playerHandler;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public void create() {
        System.out.println("create");
        //    background = new Texture(Gdx.files.internal("background.background"));

        //map = new TideMapLoader().load("background.png");
        //     listeners.add(new PlayerHandler());
        playerHandler = new PlayerHandler();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        // direct loading
    }

    public void resize(int i, int i1) {
        camera.viewportWidth = i / 3F;
        camera.viewportHeight = i1 / 3F;
    }

    public void render() {
        ScreenUtils.clear(0.5F, 0.0F, 0.5F, 1);

        camera.position.set(playerHandler.getPlayer().getLocation().getX() / 2, playerHandler.getPlayer().getLocation().getY() / 2, 0);
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        playerHandler.render();
    }

    public void pause() {
        System.out.println("pause");
    }

    public void resume() {
        System.out.println("resume");
    }

    public void dispose() {
        System.out.println("dispose");
    }

}
