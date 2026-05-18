package domain;

import java.awt.geom.Rectangle2D;

public abstract class Coin extends Entity implements Interactable {
    protected boolean collected;

    public Coin(double x, double y) {
        this.XPosition = x;
        this.YPosition = y;
        this.Size = GameConfig.COIN_SIZE;
        this.collected = false;
        this.hitBox = new Rectangle2D.Double(x, y, Size, Size);
    }

    public boolean isCollected() { return collected; }

    @Override
    public void onEnemyContact() { } // enemigos no recogen monedas

    @Override
    public void onBombContact() { } // bombas no destruyen monedas
}