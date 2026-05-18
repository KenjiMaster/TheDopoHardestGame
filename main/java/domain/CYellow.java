package domain;

public class CYellow extends Coin {

    public CYellow(double x, double y) {
        super(x, y);
    }

    @Override
    public void interact(Entity entity) {
        if (!collected && entity instanceof Player) {
            collected = true;
            ((Player) entity).collectCoin();
        }
    }
}
