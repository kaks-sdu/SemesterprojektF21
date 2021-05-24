package io.github.arkobat.kolorkarl.player;

import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.Location;
import io.github.arkobat.kolorkarl.common.Vector;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PlayerControlSystemTest {

    @Mock
    private World world;

    private Entity player;

    @Before
    public void setup(){
        player = new PlayerImpl(world, new Color[]{ Color.BLUE, Color.GREEN, Color.ORANGE }, new Location(20, 20));
    }

    @After
    public void tearDown(){
        player = null;
    }

    // method, expected, current condition
    @Test
    public void getLocation_x20y20_spawned() {
        // Test player has spawned at correct location
        assertEquals(20, player.getLocation().getX(), 0.001);
        assertEquals(20, player.getLocation().getY(), 0.001);
    }

    @Test
    public void getLocation_xOver20_velocity20(){
        // Make player move
        Vector velocity = player.getVelocity();

        long start = System.currentTimeMillis();
        long end = start + 1000;
        while(System.currentTimeMillis() < end){
            velocity.setX(20);
        }
        assertTrue(player.getLocation().getX() > 20);
    }
}
