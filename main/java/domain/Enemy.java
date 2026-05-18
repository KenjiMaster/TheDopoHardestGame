package domain;

import java.awt.geom.Rectangle2D;

public abstract class Enemy extends Entity implements Movable, Interactable {

    protected double speed;
    protected Direction currentDirection;
    private boolean alive;

    public Enemy(double x, double y, double size, double speed, Direction initialDirection) {
        this.XPosition = x;
        this.YPosition = y;
        this.alive = true;
        this.Size = size;
        this.speed = speed;
        this.currentDirection = initialDirection;
        this.hitBox = new Rectangle2D.Double(x, y, size, size);
    }

    public void destroy() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public abstract Direction calculateNextDirection(Cell[][] grid);

    public void update(double delta, Cell[][] grid) {
        Direction next = calculateNextDirection(grid);
        move(next, delta, grid);
    }

    @Override
    public void move(Direction direction, double delta, Cell[][] grid) {
        this.currentDirection = direction;

        double nextX = XPosition;
        double nextY = YPosition;

        switch (direction) {
            case NORTH: nextY -= speed * delta; break;
            case SOUTH: nextY += speed * delta; break;
            case EAST:  nextX += speed * delta; break;
            case WEST:  nextX -= speed * delta; break;
        }

        XPosition = nextX;
        YPosition = nextY;
        hitBox.setRect(XPosition, YPosition, Size, Size);
    }

    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    @Override
    public void onBombContact() { destroy(); }

    @Override
    public void onEnemyContact() { }


    @Override
    public void interact(Entity entity) {
        entity.onEnemyContact();
    }

    // Compartidos para todas las subclases
    protected boolean hitsWall(double nextX, double nextY, Cell[][] grid) {
        double[] cornersX = { nextX, nextX + Size, nextX, nextX + Size };
        double[] cornersY = { nextY, nextY, nextY + Size, nextY + Size };

        for (int i = 0; i < 4; i++) {
            int col = (int)(cornersX[i] / GameConfig.CELL_SIZE);
            int row = (int)(cornersY[i] / GameConfig.CELL_SIZE);

            if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
                return true;
            }

            if (grid[row][col].getType() == CellType.WALL) {
                return true;
            }
        }
        return false;
    }

    protected Direction invertDirection(Direction direction) {
        switch (direction) {
            case EAST:  return Direction.WEST;
            case WEST:  return Direction.EAST;
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            default:    return direction;
        }
    }
}
