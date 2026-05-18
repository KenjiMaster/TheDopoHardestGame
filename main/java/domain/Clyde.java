package domain;

import java.awt.*;

public class Clyde extends PlayerSkin{
    private boolean absorbedHit = false;

    public Clyde() {
        speedMultiplier = 1.0;
        sizeMultiplier  = 1.0;
        color = Color.GREEN;
    }

    @Override
    public void onHit(Player player) {
        if (!absorbedHit) {
            absorbedHit = true;
            player.reduceSpeed();
            activateInvincibility();
        } else {
            player.die();
            absorbedHit = false;
            player.resetSpeed();
            activateInvincibility();
        }
    }
}
