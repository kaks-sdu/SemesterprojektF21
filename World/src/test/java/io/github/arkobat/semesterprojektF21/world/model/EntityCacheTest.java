package io.github.arkobat.semesterprojektF21.world.model;

import io.github.arkobat.semesterprojektF21.common.entity.LivingEntity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.entity.Projectile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EntityCacheTest {

    private EntityCache cache;

    @Mock
    Player player, player2, player3;
    @Mock
    Projectile projectile;
    @Mock
    LivingEntity livingEntity;


    @Before
    public void setup() {
        cache = new EntityCache();
    }

    @Test
    public void get_one_addedOneEntity() {
        this.cache.add(projectile);

        int entities = this.cache.get().size();

        assertEquals(entities, 1);
    }

    @Test
    public void get_three_addedThreeEntities() {
        this.cache.add(player);
        this.cache.add(projectile);
        this.cache.add(livingEntity);

        int entities = this.cache.get().size();

        assertEquals(entities, 3);
    }

    @Test
    public void getPlayer_zero_addedOneProjectile() {
        this.cache.add(projectile);

        int players = this.cache.get(Player.class).size();

        assertEquals(players, 0);
    }

    @Test
    public void getPlayer_three_manyBulletsThreePlayers() {
        for (int i = 0; i < 10; i++) {
            this.cache.add(projectile);
        }
        this.cache.add(player);
        this.cache.add(player2);
        this.cache.add(player3);

        int players = this.cache.get(Player.class).size();

        assertEquals(players, 3);
    }

    @Test(expected = NullPointerException.class)
    public void getNull_nullPointerException_addedOneProjectile() {
        this.cache.add(projectile);
        this.cache.get(null);
    }

    @Test
    public void getLivingEntity_two_addedThreeEntities() {
        this.cache.add(player);
        this.cache.add(projectile);
        this.cache.add(livingEntity);

        int entities = this.cache.get(LivingEntity.class).size();

        assertEquals(entities, 2);
    }

    @Test
    public void remove_true_addedThreeEntitiesRemovedOne() {
        this.cache.add(player);
        this.cache.add(projectile);
        this.cache.add(player);

        int beforeRemove = this.cache.get().size();
        this.cache.remove(player);
        int afterRemove = this.cache.get().size();

        assertEquals(afterRemove, 2);
        assertEquals(afterRemove, beforeRemove - 1);
    }

    @Test
    public void remove_true_addedThreeSameTypeEntitiesRemovedOne() {
        this.cache.add(player);
        this.cache.add(player2);
        this.cache.add(player3);

        int beforeRemove = this.cache.get().size();
        this.cache.remove(player);
        int afterRemove = this.cache.get().size();

        assertEquals(afterRemove, 2);
        assertEquals(afterRemove, beforeRemove - 1);
    }

}
