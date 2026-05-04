import domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private Level level;

    @BeforeEach
    public void setUp() {
        level = new Level();
    }

    @Test
    public void shouldMoveRightWhenDirectionIsPositive() {
        Enemy enemy = new Enemy(5 * Level.CELL_SIZE, 2 * Level.CELL_SIZE, 1);
        int xInicial = enemy.getX();
        enemy.move(level);
        assertTrue(enemy.getX() > xInicial);
    }

    @Test
    public void shouldNotPassThroughWall() {
        Enemy enemy = new Enemy(18 * Level.CELL_SIZE - Enemy.SIZE - 1, 2 * Level.CELL_SIZE, 1);
        int xAntes = enemy.getX();
        enemy.move(level);
        enemy.move(level);
        assertTrue(enemy.getX() <= xAntes + 4);
    }

    @Test
    public void shouldMoveLeftWhenDirectionIsNegative() {
        Enemy enemy = new Enemy(10 * Level.CELL_SIZE, 2 * Level.CELL_SIZE, -1);
        int xInicial = enemy.getX();
        enemy.move(level);
        assertTrue(enemy.getX() < xInicial);
    }

    @Test
    public void shouldNotMoveRightWhenDirectionIsNegative() {
        Enemy enemy = new Enemy(10 * Level.CELL_SIZE, 2 * Level.CELL_SIZE, -1);
        int xInicial = enemy.getX();
        enemy.move(level);
        assertFalse(enemy.getX() > xInicial);
    }

    @Test
    public void shouldReturnNonNullBounds() {
        Enemy enemy = new Enemy(5 * Level.CELL_SIZE, 2 * Level.CELL_SIZE, 1);
        assertNotNull(enemy.getBounds());
    }

    @Test
    public void shouldNotReturnWrongBoundsSize() {
        Enemy enemy = new Enemy(5 * Level.CELL_SIZE, 2 * Level.CELL_SIZE, 1);
        assertEquals(Enemy.SIZE, enemy.getBounds().width);
        assertEquals(Enemy.SIZE, enemy.getBounds().height);
    }

    @Test
    public void shouldReturnCorrectX() {
        Enemy enemy = new Enemy(200, 120, 1);
        assertEquals(200, enemy.getX());
    }

    @Test
    public void shouldNotReturnWrongX() {
        Enemy enemy = new Enemy(200, 120, 1);
        assertNotEquals(999, enemy.getX());
    }

    @Test
    public void shouldReturnCorrectY() {
        Enemy enemy = new Enemy(200, 120, 1);
        assertEquals(120, enemy.getY());
    }

    @Test
    public void shouldNotReturnWrongY() {
        Enemy enemy = new Enemy(200, 120, 1);
        assertNotEquals(999, enemy.getY());
    }
}
