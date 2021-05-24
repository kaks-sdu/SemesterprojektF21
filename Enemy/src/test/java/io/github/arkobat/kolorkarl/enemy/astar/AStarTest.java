package io.github.arkobat.kolorkarl.enemy.astar;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.commonWorld.WorldTemp;
import io.github.arkobat.kolorkarl.enemy.Enemy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.Mockito.when;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AStarTest {

    @Mock
    private Enemy enemy;

    @Mock
    private WorldTemp world;

    @Mock
    TiledMapTileLayer tiledMapTileLayer;

    @Mock
    TiledMapTileLayer.Cell noneCell;
    @Mock
    TiledMapTileLayer.Cell spikesCell;
    @Mock
    TiledMapTileLayer.Cell collisionCell;
    @Mock
    TiledMapTile noneTile;
    @Mock
    TiledMapTile spikesTile;
    @Mock
    TiledMapTile collisionTile;
    @Mock
    MapProperties noneMapProperties;
    @Mock
    MapProperties spikesMapProperties;
    @Mock
    MapProperties collisionMapProperties;



    @Before
    public void setup(){
        when(enemy.getLocation()).thenReturn(new Location(0, 1));
    }

    private void setupMap(int[][] map) {
        when(enemy.getWorld()).thenReturn(world);
        when(world.getCollisionLayer()).thenReturn(tiledMapTileLayer);
        when(tiledMapTileLayer.getHeight()).thenReturn(10);
        when(tiledMapTileLayer.getWidth()).thenReturn(6);


        _mock(noneCell, noneTile, noneMapProperties, false, false);
        _mock(spikesCell, spikesTile, spikesMapProperties, false, true);
        _mock(collisionCell, collisionTile, collisionMapProperties, true, false);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 6; y++) {
                int value = map[y][x];
                if (value == 200) {
                    when(tiledMapTileLayer.getCell(x, y)).thenReturn(spikesCell);
                } else if (value == 100) {
                    when(tiledMapTileLayer.getCell(x, y)).thenReturn(collisionCell);
                } else
                    when(tiledMapTileLayer.getCell(x, y)).thenReturn(noneCell);
            }
        }
    }

    @Test
    public void gotoLocation_() throws NoSuchFieldException, IllegalAccessException {
        int[][] emptyMap = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {100, 100, 100, 100, 100, 100, 100, 100, 100, 100},
        };

        setupMap(emptyMap);

        AStar aStar = new AStar(enemy);
        aStar.gotoLocation(new Location(80, 40));

        Field pathField = aStar.getClass().getDeclaredField("path");
        pathField.setAccessible(true);
        List<Node> path = (List<Node>) pathField.get(aStar);

        Node lastNode = path.get(path.size()-1);
        Assert.assertTrue(lastNode.getLocation().getX() == 80);
    }

    public void _mock(TiledMapTileLayer.Cell cell, TiledMapTile tile, MapProperties properties, boolean collision, boolean spikes) {
        when(cell.getTile()).thenReturn(tile);

        when(tile.getProperties()).thenReturn(properties);
        when(properties.containsKey("collision")).thenReturn(collision);
        when(properties.containsKey("spikes")).thenReturn(spikes);

    }
}
