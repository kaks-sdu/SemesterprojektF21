package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.arkobat.semesterprojektf21.temp.PlayerImpl;

public class App implements ApplicationListener {

    private TiledMap map;
    private TiledMapRenderer tiledMapRenderer;
    private Texture jonathan;
    private PlayerImpl player;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public void create() {
        System.out.println("create");
        //    background = new Texture(Gdx.files.internal("background.background"));

        //map = new TideMapLoader().load("background.png");
        jonathan = new Texture(Gdx.files.internal("player.png"));
        player = new PlayerImpl();
        player.getLocation().setX(Gdx.graphics.getWidth() / 2F);
        player.getLocation().setX(Gdx.graphics.getHeight() / 2F);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        // direct loading
    }

    public void resize(int i, int i1) {
        System.out.println("resize " + i + ", " + i1);
    }

    public void render() {
        ScreenUtils.clear(0.5F, 0.0F, 0.5F, 1);
        // System.out.println("render");

        //camera.position.set(player.getLocation().getX(), 0, 0);
        camera.position.set(player.getLocation().getX(), player.getLocation().getY(), 0);
        camera.update();

        batch.begin();
        // batch.draw(map, -500, 0);
        batch.draw(jonathan, player.getLocation().getX(), player.getLocation().getY());
        batch.end();

        final Location location = player.getLocation();
        if (Key.LEFT.isPressed()) {
            location.setX(Math.max(0, location.getX() - 100 * Gdx.graphics.getDeltaTime()));
        }
        if (Key.RIGHT.isPressed()) {
            location.setX(location.getX() + 100 * Gdx.graphics.getDeltaTime());
        }

        if (Key.JUMP.isPressed()) {
            location.setY(location.getY() + 100 * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            location.setY(Math.max(0, location.getY() - 100 * Gdx.graphics.getDeltaTime()));
        }
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
