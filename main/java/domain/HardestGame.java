package domain;

import java.util.ArrayList;
import java.util.List;

public class HardestGame {

    private Level level;
    private Player player;
    private List<Enemy> enemies;
    private int deaths;

    /**
     * Crea el juego, el nivel y los enemigos en sus posiciones iniciales.
     */
    public HardestGame() {
        level = new Level();
        player = new Player(level.getStartX(), level.getStartY());
        deaths = 0;
        enemies = new ArrayList<>();
        enemies.add(new Enemy(6 * Level.CELL_SIZE, 1 * Level.CELL_SIZE + 10, 1));
        enemies.add(new Enemy(10 * Level.CELL_SIZE, 2 * Level.CELL_SIZE + 10, -1));
        enemies.add(new Enemy(8 * Level.CELL_SIZE, 3 * Level.CELL_SIZE + 10, 1));
        enemies.add(new Enemy(13 * Level.CELL_SIZE, 4 * Level.CELL_SIZE + 10, -1));
    }

    /**
     * Actualiza el estado del juego en cada tick: mueve enemigos y revisa colisiones.
     */
    public void update() {
        for (Enemy enemy : enemies) {
            enemy.move(level);
        }
        if (playerTocaEnemigo()) {
            deaths++;
            player.respawn(level.getStartX(), level.getStartY());
        }
    }

    /**
     * Mueve al jugador en la direccion indicada.
     *
     * @param dx direccion horizontal (-1, 0 o 1)
     * @param dy direccion vertical (-1, 0 o 1)
     */
    public void movePlayer(int dx, int dy) {
        player.move(dx, dy, level);
    }

    /**
     * Dice si el jugador llego a la zona final.
     *
     * @return true si el jugador esta en la zona de llegada
     */
    public boolean isLevelComplete() {
        Cell celda = level.getCellAtPixel(
            player.getX() + Player.SIZE / 2,
            player.getY() + Player.SIZE / 2
        );
        return celda.getType() == CellType.SAFE_ZONE_END;
    }

    /**
     * Revisa si el jugador esta tocando algun enemigo.
     *
     * @return true si hay colision con algun enemigo
     */
    private boolean playerTocaEnemigo() {
        for (Enemy enemy : enemies) {
            if (player.getBounds().intersects(enemy.getBounds())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el nivel actual.
     *
     * @return nivel
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Devuelve el jugador.
     *
     * @return jugador
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Devuelve la lista de enemigos.
     *
     * @return lista de enemigos
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Devuelve el numero de muertes del jugador.
     *
     * @return muertes
     */
    public int getDeaths() {
        return deaths;
    }
}
