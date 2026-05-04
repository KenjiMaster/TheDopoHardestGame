
import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HardestGameTest {

    private HardestGame game;

    @BeforeEach
    public void setUp() {
        game = new HardestGame();
    }

    @Test
    public void shouldStartWithZeroDeaths() {
        assertEquals(0, game.getDeaths());
    }

    @Test
    public void shouldNotStartWithDeaths() {
        assertFalse(game.getDeaths() > 0);
    }

    @Test
    public void shouldReturnNonNullPlayer() {
        assertNotNull(game.getPlayer());
    }

    @Test
    public void shouldNotReturnNullPlayer() {
        assertNotEquals(null, game.getPlayer());
    }

    @Test
    public void shouldReturnNonNullLevel() {
        assertNotNull(game.getLevel());
    }

    @Test
    public void shouldNotReturnNullLevel() {
        assertNotEquals(null, game.getLevel());
    }

    @Test
    public void shouldReturnNonEmptyEnemyList() {
        assertFalse(game.getEnemies().isEmpty());
    }

    @Test
    public void shouldNotReturnNullEnemyList() {
        assertNotNull(game.getEnemies());
    }

    @Test
    public void shouldMovePlayerRight() {
        int xInicial = game.getPlayer().getX();
        game.movePlayer(1, 0);
        assertTrue(game.getPlayer().getX() > xInicial);
    }

    @Test
    public void shouldNotMovePlayerIntoWall() {
        for (int i = 0; i < 200; i++) {
            game.movePlayer(-1, 0);
        }
        Cell celda = game.getLevel().getCellAtPixel(
            game.getPlayer().getX(),
            game.getPlayer().getY()
        );
        assertNotEquals(CellType.WALL, celda.getType());
    }

    @Test
    public void shouldNotBeCompletedAtStart() {
        assertFalse(game.isLevelComplete());
    }

    @Test
    public void shouldBeCompletedWhenPlayerReachesEnd() {
        Level level = game.getLevel();
        Cell[][] grid = level.getGrid();
        int endX = 0, endY = 0;
        outer:
        for (int fila = 0; fila < grid.length; fila++) {
            for (int col = 0; col < grid[fila].length; col++) {
                if (grid[fila][col].getType() == CellType.SAFE_ZONE_END) {
                    endX = col * Level.CELL_SIZE;
                    endY = fila * Level.CELL_SIZE;
                    break outer;
                }
            }
        }
        Player player = game.getPlayer();
        player.respawn(endX, endY);
        assertTrue(game.isLevelComplete());
    }

    @Test
    public void shouldUpdateEnemyPositions() {
        int xAntes = game.getEnemies().get(0).getX();
        game.update();
        int xDespues = game.getEnemies().get(0).getX();
        assertNotEquals(xAntes, xDespues);
    }

    @Test
    public void shouldNotKeepEnemiesStatic() {
        int xAntes = game.getEnemies().get(0).getX();
        game.update();
        assertFalse(game.getEnemies().get(0).getX() == xAntes
                 && game.getEnemies().get(1).getX() == game.getEnemies().get(1).getX());
    }
}
