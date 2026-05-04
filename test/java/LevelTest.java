
import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {

    private Level level;

    @BeforeEach
    public void setUp() {
        level = new Level();
    }

    @Test
    public void shouldReturnWallCellAtBorder() {
        Cell cell = level.getCell(0, 0);
        assertEquals(CellType.WALL, cell.getType());
    }

    @Test
    public void shouldNotReturnWalkableAtBorder() {
        Cell cell = level.getCell(0, 0);
        assertNotEquals(CellType.WALKABLE, cell.getType());
    }

    @Test
    public void shouldReturnWallWhenOutOfBounds() {
        Cell cell = level.getCell(-1, -1);
        assertEquals(CellType.WALL, cell.getType());
    }

    @Test
    public void shouldNotReturnWalkableWhenOutOfBounds() {
        Cell cell = level.getCell(999, 999);
        assertNotEquals(CellType.WALKABLE, cell.getType());
    }

    @Test
    public void shouldReturnCorrectCellAtPixel() {
        Cell cell = level.getCellAtPixel(0, 0);
        assertEquals(CellType.WALL, cell.getType());
    }

    @Test
    public void shouldNotReturnWallAtWalkablePixel() {
        int px = 5 * Level.CELL_SIZE + 5;
        int py = 2 * Level.CELL_SIZE + 5;
        Cell cell = level.getCellAtPixel(px, py);
        assertNotEquals(CellType.WALL, cell.getType());
    }

    @Test
    public void shouldReturnPositiveWidthInPixels() {
        assertTrue(level.getWidthPixels() > 0);
    }

    @Test
    public void shouldNotReturnZeroWidthInPixels() {
        assertNotEquals(0, level.getWidthPixels());
    }

    @Test
    public void shouldReturnPositiveHeightInPixels() {
        assertTrue(level.getHeightPixels() > 0);
    }

    @Test
    public void shouldNotReturnZeroHeightInPixels() {
        assertNotEquals(0, level.getHeightPixels());
    }

    @Test
    public void shouldReturnNonNullGrid() {
        assertNotNull(level.getGrid());
    }

    @Test
    public void shouldNotReturnEmptyGrid() {
        assertTrue(level.getGrid().length > 0);
    }

    @Test
    public void shouldReturnValidStartX() {
        assertTrue(level.getStartX() >= 0);
    }

    @Test
    public void shouldNotReturnNegativeStartX() {
        assertFalse(level.getStartX() < 0);
    }

    @Test
    public void shouldReturnValidStartY() {
        assertTrue(level.getStartY() >= 0);
    }

    @Test
    public void shouldNotReturnNegativeStartY() {
        assertFalse(level.getStartY() < 0);
    }
}
