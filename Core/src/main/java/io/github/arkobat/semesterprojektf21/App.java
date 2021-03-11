package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.arkobat.semesterprojektf21.temp.PlayerHandler;

public class App implements ApplicationListener {

    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;
    private PlayerHandler playerHandler;
    private OrthographicCamera camera;
    private Viewport viewport;

    public void create() {


        Music music = Gdx.audio.newMusic(Gdx.files.internal("sound/3_chill_8bit_loops.mp3"));
        music.setVolume(0.1F);
        music.setLooping(true);
        music.play();

        map = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16F);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        camera.update();

        viewport = new ExtendViewport(100, 100, camera);

        playerHandler = new PlayerHandler((TiledMapTileLayer) map.getLayers().get(0));
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width / 3F;
        camera.viewportHeight = height / 3F;

        viewport.update(width, height, false);
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
