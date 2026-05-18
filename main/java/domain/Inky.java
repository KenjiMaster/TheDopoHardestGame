package domain;

import java.awt.*;

public class Inky extends PlayerSkin{
    public Inky() {
        speedMultiplier = 1.5;
        sizeMultiplier  = 1.5;
        color = Color.BLUE;
    }
    @Override
    public void onHit(Player player) {
        player.die();
        activateInvincibility();
    }
}
