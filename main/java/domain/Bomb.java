package domain;

import java.awt.geom.Rectangle2D;

public class Bomb extends EspecialElement{

    public Bomb(double x, double y) {
        this.XPosition = x;
        this.YPosition = y;
        this.Size = GameConfig.CELL_SIZE;
        this.active = true;
        this.hitBox = new Rectangle2D.Double(x, y, Size, Size);
    }

    @Override
    public void interact(Entity entity) {
        if (!active) return;
        entity.onBombContact();  // la entidad decide qué le pasa
        this.active = false;
    }

    @Override
    public void onEnemyContact() {

    }

    @Override
    public void onBombContact() {

    }
}
