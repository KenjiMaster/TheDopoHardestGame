package domain;

public class Sliding extends Enemy {

    public Sliding(double x, double y, double size, double speed, Direction initialDirection) throws HardestGameException {
        super(x, y, size, speed, initialDirection);

        // Sliding solo se mueve verticalmente
        if (initialDirection != Direction.NORTH && initialDirection != Direction.SOUTH) {
            throw new HardestGameException("Sliding solo acepta direcciones NORTH o SOUTH");
        }
    }

    @Override
    public Direction calculateNextDirection(Cell[][] grid) {
        double nextY = YPosition;

        switch (currentDirection) {
            case NORTH: nextY -= speed; break;
            case SOUTH: nextY += speed; break;
        }

        // solo verifica pared vertical — ignora paredes laterales
        if (hitsWall(XPosition, nextY, grid)) {
            return invertDirection(currentDirection);
        }

        return currentDirection;
    }

    // Sliding no cambia de dirección horizontal nunca — sobreescribe move()
    // para asegurar que X no cambia aunque calculateNextDirection retorne algo raro
    @Override
    public void move(Direction direction, double delta, Cell[][] grid) {
        this.currentDirection = direction;

        double nextY = YPosition;

        switch (direction) {
            case NORTH: nextY -= speed * delta; break;
            case SOUTH: nextY += speed * delta; break;
            default: break; // ignora cualquier dirección horizontal
        }

        YPosition = nextY;
        hitBox.setRect(XPosition, YPosition, Size, Size);
    }
}
