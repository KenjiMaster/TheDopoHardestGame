package domain;

import java.awt.geom.Rectangle2D;

public class Live extends EspecialElement {

    public Live(double x, double y) {
        this.XPosition = x;
        this.YPosition = y;
        this.Size = GameConfig.CELL_SIZE;
        this.active = true;
        this.hitBox = new Rectangle2D.Double(x, y, Size, Size);
    }

    @Override
    public void interact(Entity player) {
        if (active) {
            ((Player)player).addLife();    // da una vida extra
            this.active = false; // desaparece después de usarse
        }
    }

    @Override
    public void onEnemyContact() {

    }

    @Override
    public void onBombContact() {

    }
}
