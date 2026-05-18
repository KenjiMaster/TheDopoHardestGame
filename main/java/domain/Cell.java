package domain;

public class Cell {
    private CellType type;
    public Cell(CellType cellType) {
        this.type = cellType;
    }

    public CellType getType() {
        return type;
    }
}
