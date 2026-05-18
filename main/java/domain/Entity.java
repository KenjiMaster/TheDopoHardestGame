package domain;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected double XPosition;
    protected double YPosition;
    protected double Size;
    protected Rectangle2D hitBox;

    public boolean collition(Entity entity){
        return this.hitBox.intersects(entity.hitBox);
    }
    public abstract void onEnemyContact();
    public abstract void onBombContact();
    public Rectangle2D getHitBox(){
        return hitBox;
    }
}
