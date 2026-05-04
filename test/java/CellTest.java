import domain.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void shouldBeWalkableWhenTypeIsWalkable() {
        Cell cell = new Cell(CellType.WALKABLE, 0, 0);
        assertTrue(cell.isWalkable());
    }

    @Test
    public void shouldNotBeWalkableWhenTypeIsWall() {
        Cell cell = new Cell(CellType.WALL, 0, 0);
        assertFalse(cell.isWalkable());
    }

    @Test
    public void shouldReturnCorrectType() {
        Cell cell = new Cell(CellType.SAFE_ZONE_END, 2, 3);
        assertEquals(CellType.SAFE_ZONE_END, cell.getType());
    }

    @Test
    public void shouldNotReturnWrongType() {
        Cell cell = new Cell(CellType.SAFE_ZONE_START, 2, 3);
        assertNotEquals(CellType.WALL, cell.getType());
    }

    @Test
    public void shouldReturnCorrectX() {
        Cell cell = new Cell(CellType.WALKABLE, 5, 3);
        assertEquals(5, cell.getX());
    }

    @Test
    public void shouldNotReturnWrongX() {
        Cell cell = new Cell(CellType.WALKABLE, 5, 3);
        assertNotEquals(99, cell.getX());
    }

    @Test
    public void shouldReturnCorrectY() {
        Cell cell = new Cell(CellType.WALKABLE, 5, 3);
        assertEquals(3, cell.getY());
    }

    @Test
    public void shouldNotReturnWrongY() {
        Cell cell = new Cell(CellType.WALKABLE, 5, 3);
        assertNotEquals(99, cell.getY());
    }

    @Test
    public void shouldBeWalkableWhenTypeIsSafeZoneStart() {
        Cell cell = new Cell(CellType.SAFE_ZONE_START, 0, 0);
        assertTrue(cell.isWalkable());
    }

    @Test
    public void shouldNotBeWalkableWhenTypeIsSafeZoneEnd() {
        Cell cell = new Cell(CellType.SAFE_ZONE_END, 0, 0);
        assertTrue(cell.isWalkable());
    }
}
