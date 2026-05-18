package domain;

public class Basic extends Enemy {

    public Basic(double x, double y, double size, double speed, Direction initialDirection) throws HardestGameException {
        super(x, y, size, speed, initialDirection);

        if (initialDirection != Direction.EAST && initialDirection != Direction.WEST &&
                initialDirection != Direction.NORTH && initialDirection != Direction.SOUTH) {
            throw new HardestGameException("Basic solo acepta direcciones cardinales");
        }
    }

    @Override
    public Direction calculateNextDirection(Cell[][] grid) {
        double nextX = XPosition;
        double nextY = YPosition;

        switch (currentDirection) {
            case EAST:  nextX += speed; break;
            case WEST:  nextX -= speed; break;
            case NORTH: nextY -= speed; break;
            case SOUTH: nextY += speed; break;
        }

        if (hitsWall(nextX, nextY, grid)) {
            return invertDirection(currentDirection);
        }

        return currentDirection;
    }
}
