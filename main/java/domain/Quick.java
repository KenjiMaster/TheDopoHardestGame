package domain;

public class Quick extends Enemy {

    public Quick(double x, double y, double size, double speed, Direction initialDirection) throws HardestGameException {
        super(x, y, size, speed * 2, initialDirection); // velocidad 2x directo en el constructor

        if (initialDirection != Direction.EAST && initialDirection != Direction.WEST &&
                initialDirection != Direction.NORTH && initialDirection != Direction.SOUTH) {
            throw new HardestGameException("Quick solo acepta direcciones cardinales");
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
