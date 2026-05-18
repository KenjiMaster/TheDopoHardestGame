package domain;

import java.awt.*;

public class Blinky extends PlayerSkin{
    public Blinky() {
        speedMultiplier = 1.0;
        sizeMultiplier = 1.0;
        color = Color.RED;
    }

    @Override
    public void onHit(Player player) {
        player.die();
        activateInvincibility();
    }
}
