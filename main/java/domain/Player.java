package domain;

import java.awt.Rectangle;

public class Player {

    private int x;
    private int y;
    public static final int SIZE = 20;
    private static final int SPEED = 3;

    /**
     * Crea el jugador en la posicion indicada.
     *
     * @param x posicion inicial en x
     * @param y posicion inicial en y
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Mueve al jugador si la nueva posicion es caminable.
     *
     * @param dx direccion horizontal (-1, 0 o 1)
     * @param dy direccion vertical (-1, 0 o 1)
     * @param level nivel actual para validar paredes
     */
    public void move(int dx, int dy, Level level) {
        int nuevoX = x + dx * SPEED;
        int nuevoY = y + dy * SPEED;

        Cell esquinaTopIzq  = level.getCellAtPixel(nuevoX, nuevoY);
        Cell esquinaTopDer  = level.getCellAtPixel(nuevoX + SIZE - 1, nuevoY);
        Cell esquinaBotIzq  = level.getCellAtPixel(nuevoX, nuevoY + SIZE - 1);
        Cell esquinaBotDer  = level.getCellAtPixel(nuevoX + SIZE - 1, nuevoY + SIZE - 1);

        boolean puedeX = esquinaTopIzq.isWalkable() && esquinaTopDer.isWalkable()
                      && esquinaBotIzq.isWalkable() && esquinaBotDer.isWalkable();

        if (puedeX) {
            x = nuevoX;
            y = nuevoY;
        }
    }

    /**
     * Regresa al jugador a la posicion de inicio.
     *
     * @param startX x de inicio
     * @param startY y de inicio
     */
    public void respawn(int startX, int startY) {
        x = startX;
        y = startY;
    }

    /**
     * Devuelve el area del jugador como rectangulo para detectar colisiones.
     *
     * @return rectangulo del jugador
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    /**
     * Devuelve la posicion x del jugador.
     *
     * @return x en pixeles
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la posicion y del jugador.
     *
     * @return y en pixeles
     */
    public int getY() {
        return y;
    }
}
