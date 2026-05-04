package domain;

import java.awt.Rectangle;

public class Enemy {

    private int x;
    private int y;
    private int dx;
    public static final int SIZE = 20;
    private static final int SPEED = 3;

    /**
     * Crea un enemigo en la posicion dada moviendose en la direccion indicada.
     *
     * @param x posicion inicial en x
     * @param y posicion inicial en y
     * @param dx direccion horizontal (1 o -1)
     */
    public Enemy(int x, int y, int dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
    }

    /**
     * Mueve el enemigo y lo hace rebotar si choca con una pared.
     *
     * @param level nivel actual para validar paredes
     */
    public void move(Level level) {
        int nuevoX = x + dx * SPEED;

        Cell izq = level.getCellAtPixel(nuevoX, y);
        Cell der = level.getCellAtPixel(nuevoX + SIZE - 1, y);

        if (!izq.isWalkable() || !der.isWalkable()) {
            dx = -dx;
        } else {
            x = nuevoX;
        }
    }

    /**
     * Devuelve el area del enemigo como rectangulo para detectar colisiones.
     *
     * @return rectangulo del enemigo
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    /**
     * Devuelve la posicion x del enemigo.
     *
     * @return x en pixeles
     */
    public int getX() {
        return x;
    }

    /**
     * Devuelve la posicion y del enemigo.
     *
     * @return y en pixeles
     */
    public int getY() {
        return y;
    }
}
