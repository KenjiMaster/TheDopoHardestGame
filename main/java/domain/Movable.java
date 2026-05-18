package domain;

public interface Movable {
    void move(Direction direction, double delta, Cell[][] grid);
    Direction getCurrentDirection();
}
