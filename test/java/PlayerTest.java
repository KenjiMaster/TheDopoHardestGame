
import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Level level;

    @BeforeEach
    public void setUp() {
        level = new Level();
        player = new Player(level.getStartX(), level.getStartY());
    }

    @Test
    public void shouldMoveRightWhenPathIsClear() {
        int xInicial = player.getX();
        player.move(1, 0, level);
        assertTrue(player.getX() > xInicial);
    }

    @Test
    public void shouldNotMoveIntoWall() {
        Player playerEnPared = new Player(1 * Level.CELL_SIZE - 3, 2 * Level.CELL_SIZE);
        int xInicial = playerEnPared.getX();
        playerEnPared.move(-1, 0, level);
        assertEquals(xInicial, playerEnPared.getX());
    }

    @Test
    public void shouldMoveDownWhenPathIsClear() {
        int yInicial = player.getY();
        player.move(0, 1, level);
        assertTrue(player.getY() > yInicial);
    }

    @Test
    public void shouldNotMoveUpIntoWall() {
        Player playerEnPared = new Player(2 * Level.CELL_SIZE, 1 * Level.CELL_SIZE - 3);
        int yInicial = playerEnPared.getY();
        playerEnPared.move(0, -1, level);
        assertEquals(yInicial, playerEnPared.getY());
    }

    @Test
    public void shouldMoveDiagonally() {
        int xInicial = player.getX();
        int yInicial = player.getY();
        player.move(1, 1, level);
        assertTrue(player.getX() > xInicial && player.getY() > yInicial);
    }

    @Test
    public void shouldNotChangePosWhenDirIsZero() {
        int xInicial = player.getX();
        int yInicial = player.getY();
        player.move(0, 0, level);
        assertEquals(xInicial, player.getX());
        assertEquals(yInicial, player.getY());
    }

    @Test
    public void shouldRespawnAtGivenPosition() {
        player.move(1, 0, level);
        player.respawn(level.getStartX(), level.getStartY());
        assertEquals(level.getStartX(), player.getX());
        assertEquals(level.getStartY(), player.getY());
    }

    @Test
    public void shouldNotKeepPositionAfterRespawn() {
        player.move(1, 0, level);
        int xMovido = player.getX();
        player.respawn(level.getStartX(), level.getStartY());
        assertNotEquals(xMovido, player.getX());
    }

    @Test
    public void shouldReturnNonNullBounds() {
        assertNotNull(player.getBounds());
    }

    @Test
    public void shouldNotReturnWrongBoundsSize() {
        assertEquals(Player.SIZE, player.getBounds().width);
        assertEquals(Player.SIZE, player.getBounds().height);
    }

    @Test
    public void shouldReturnCorrectX() {
        assertEquals(level.getStartX(), player.getX());
    }

    @Test
    public void shouldNotReturnWrongX() {
        assertNotEquals(999, player.getX());
    }

    @Test
    public void shouldReturnCorrectY() {
        assertEquals(level.getStartY(), player.getY());
    }

    @Test
    public void shouldNotReturnWrongY() {
        assertNotEquals(999, player.getY());
    }
}
