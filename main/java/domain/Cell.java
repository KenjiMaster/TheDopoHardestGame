package domain;

public class Cell {

    private CellType type;
    private int x;
    private int y;

    /**
     * Crea una celda en la posicion dada con el tipo indicado.
     *
     * @param type tipo de celda
     * @param x columna en el grid
     * @param y fila en el grid
     */
    public Cell(CellType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Dice si el jugador o enemigo puede pasar por esta celda.
     *
     * @return true si no es pared
     */
    public boolean isWalkable() {
        return type != CellType.WALL;
    }

    /**
     * Devuelve el tipo de esta celda.
     *
     * @return tipo de celda
     */
    public CellType getType() {
        return type;
    }

    /**
     * Devuelve la columna de esta celda en el grid.
     *
     * @return columna
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la fila de esta celda en el grid.
     *
     * @return fila
     */
    public int getY() {
        return y;
    }
}
