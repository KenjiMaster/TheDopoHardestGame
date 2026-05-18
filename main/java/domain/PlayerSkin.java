package domain;

import java.awt.*;

public abstract class PlayerSkin {
    protected double speedMultiplier;
    protected double sizeMultiplier;
    protected Color color;
    protected int invincibleFrames = 0;
    private static final int DEFAULT_INVINCIBLE_DURATION = 60;

    public void updateInvincibility() {
        if (invincibleFrames > 0) invincibleFrames--;
    }

    public boolean isInvincible() {
        return invincibleFrames > 0;
    }

    protected void activateInvincibility() {
        invincibleFrames = DEFAULT_INVINCIBLE_DURATION;
    }

    public double getSpeedMultiplier() { return speedMultiplier; }
    public double getSizeMultiplier() { return sizeMultiplier; }
    public Color getColor(){ return color; }

    public abstract void onHit(Player player);
}
