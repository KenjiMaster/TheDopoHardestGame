package domain;

public class Level {

    private Cell[][] grid;
    private int startX;
    private int startY;
    public static final int CELL_SIZE = 40;

    /**
     * Crea el nivel con un mapa predefinido.
     * W = pared, S = zona inicio, E = zona final, . = caminable
     */
    public Level() {
        String[] mapa = {
            "WWWWWWWWWWWWWWWWWWWW",
            "WSSSW.............WW",
            "WSSSW.............EW",
            "WSSSW.............WW",
            "WSSSW.............WW",
            "WSSS..............WW",
            "WWWWWWWWWWWWWWWWWWWW"
        };
        grid = new Cell[mapa.length][mapa[0].length()];
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int col = 0; col < mapa[fila].length(); col++) {
                char c = mapa[fila].charAt(col);
                CellType tipo;
                if (c == 'W') tipo = CellType.WALL;
                else if (c == 'S') tipo = CellType.SAFE_ZONE_START;
                else if (c == 'E') tipo = CellType.SAFE_ZONE_END;
                else tipo = CellType.WALKABLE;
                grid[fila][col] = new Cell(tipo, col, fila);
            }
        }
        startX = 1 * CELL_SIZE;
        startY = 2 * CELL_SIZE;
    }

    /**
     * Devuelve la celda en la posicion del grid indicada.
     *
     * @param col columna
     * @param fila fila
     * @return celda en esa posicion, o una pared si esta fuera del mapa
     */
    public Cell getCell(int col, int fila) {
        if (fila < 0 || fila >= grid.length || col < 0 || col >= grid[0].length) {
            return new Cell(CellType.WALL, col, fila);
        }
        return grid[fila][col];
    }

    /**
     * Devuelve la celda que corresponde a una posicion en pixeles.
     *
     * @param px posicion x en pixeles
     * @param py posicion y en pixeles
     * @return celda en esa posicion
     */
    public Cell getCellAtPixel(int px, int py) {
        return getCell(px / CELL_SIZE, py / CELL_SIZE);
    }

    /**
     * Devuelve el ancho del mapa en pixeles.
     *
     * @return ancho en pixeles
     */
    public int getWidthPixels() {
        return grid[0].length * CELL_SIZE;
    }

    /**
     * Devuelve el alto del mapa en pixeles.
     *
     * @return alto en pixeles
     */
    public int getHeightPixels() {
        return grid.length * CELL_SIZE;
    }

    /**
     * Devuelve el grid completo de celdas.
     *
     * @return matriz de celdas
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Devuelve la posicion inicial del jugador en x (pixeles).
     *
     * @return x inicial
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Devuelve la posicion inicial del jugador en y (pixeles).
     *
     * @return y inicial
     */
    public int getStartY() {
        return startY;
    }
}
