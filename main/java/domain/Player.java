package domain;

import java.awt.geom.Rectangle2D;

public class Player extends Entity implements Movable {
    private double baseSpeed;
    private double baseSize;
    private int lives;
    private double XCheck;
    private double YCheck;
    private int coinsCollected;

    private double currentSpeed;
    private double currentSize;
    private Direction currentDirection;
    private PlayerSkin skin;

    public Player(PlayerSkin skin, int XCheck, int YCheck) {
        coinsCollected = 0;
        this.skin = skin;
        this.baseSpeed = 3.0;
        this.baseSize = 20.0;
        this.lives = 1;
        this.currentSpeed = baseSpeed * skin.getSpeedMultiplier();
        this.currentSize = baseSize * skin.getSizeMultiplier();
        this.XPosition = XCheck;
        this.YPosition = YCheck;
        this.XCheck = XCheck;
        this.YCheck = YCheck;
        this.hitBox = new Rectangle2D.Double(XPosition, YPosition, currentSize, currentSize);
    }

    @Override
    public void move(Direction direction, double delta, Cell[][] grid) {
        this.currentDirection = direction;
        double nextX = XPosition;
        double nextY = YPosition;

        switch (direction) {
            case NORTH:      nextY -= currentSpeed * delta; break;
            case SOUTH:      nextY += currentSpeed * delta; break;
            case EAST:       nextX += currentSpeed * delta; break;
            case WEST:       nextX -= currentSpeed * delta; break;
            case NORTH_EAST: nextX += currentSpeed * delta; nextY -= currentSpeed * delta; break;
            case NORTH_WEST: nextX -= currentSpeed * delta; nextY -= currentSpeed * delta; break;
            case SOUTH_EAST: nextX += currentSpeed * delta; nextY += currentSpeed * delta; break;
            case SOUTH_WEST: nextX -= currentSpeed * delta; nextY += currentSpeed * delta; break;
        }

        if (canMoveTo(nextX, nextY, grid)) {
            XPosition = nextX;
            YPosition = nextY;
            hitBox.setRect(XPosition, YPosition, currentSize, currentSize);
            checkZone(grid);  // verificar zona después de mover
        }
    }

    public void collectCoin() {
        coinsCollected++;
    }

    private void checkZone(Cell[][] grid) {
        // celda del centro del jugador
        int col = (int)((XPosition + currentSize / 2) / GameConfig.CELL_SIZE);
        int row = (int)((YPosition + currentSize / 2) / GameConfig.CELL_SIZE);

        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return;

        CellType type = grid[row][col].getType();

        switch (type) {
            case CHECK_ZONE:
                setCheckpoint(XPosition, YPosition);
                break;
            case FINAL_ZONE:
                setCheckpoint(XPosition, YPosition); // por ahora igual que checkpoint
                break;
            default:
                break;
        }
    }

    private boolean canMoveTo(double nextX, double nextY, Cell[][] grid) {
        double[] cornersX = { nextX, nextX + currentSize, nextX, nextX + currentSize };
        double[] cornersY = { nextY, nextY, nextY + currentSize, nextY + currentSize };

        for (int i = 0; i < 4; i++) {
            int col = (int)(cornersX[i] / GameConfig.CELL_SIZE);
            int row = (int)(cornersY[i] / GameConfig.CELL_SIZE);

            if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return false;
            if (grid[row][col].getType() == CellType.WALL) return false;
        }
        return true;
    }

    // El skin decide qué pasa al recibir golpe
    public void hit() {
        skin.onHit(this);
    }
    @Override
    public void onBombContact() { die(); }

    @Override
    public void onEnemyContact() { hit(); }

    // Regresa al checkpoint
    public void die() {
        if (lives > 1){
            lives--;
        } else {
            this.XPosition = XCheck;
            this.YPosition = YCheck;
            hitBox.setRect(XPosition, YPosition, currentSize, currentSize);
        }
    }

    // Llamado por Live
    public void addLife() {
        this.lives++;
        skin.activateInvincibility();
    }

    public boolean isInvincible() {
        return skin.isInvincible();
    }

    public void updateInvincibility() {
        skin.updateInvincibility();
    }

    // Llamado por Clyde al absorber golpe
    public void reduceSpeed() {
        this.currentSpeed = baseSpeed * 0.7;
    }

    public void  resetSpeed(){
        this.currentSpeed = baseSpeed*skin.getSpeedMultiplier();
    }

    // Actualiza checkpoint al entrar zona intermedia
    public void setCheckpoint(double x, double y) {
        this.XCheck = x;
        this.YCheck = y;
    }

    // Cambia el skin y recalcula atributos
    public void setSkin(PlayerSkin newSkin) {
        this.skin = newSkin;
        this.currentSpeed = baseSpeed * newSkin.getSpeedMultiplier();
        this.currentSize = baseSize * newSkin.getSizeMultiplier();
        hitBox.setRect(XPosition, YPosition, currentSize, currentSize);
    }

    @Override
    public Direction getCurrentDirection() { return currentDirection; }
    public PlayerSkin getSkin() { return skin; }
    public int getLives() { return lives; }
    public double getCurrentSize() { return currentSize; }
}
