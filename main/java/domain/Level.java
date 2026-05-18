package domain;

import java.util.List;
import java.util.stream.Collectors;

public class Level {
    private Cell[][] grid;
    private Player player;
    private List<Entity> entities;
    private double timeLimit;
    private double timeRemaining;
    private int finalZoneX;
    private int finalZoneY;

    public Level(Cell[][] grid, List<Entity> entities, Player player, double timeLimit, int finalZoneX, int finalZoneY) {
        this.grid = grid;
        this.entities = entities;
        this.player = player;
        this.timeLimit = timeLimit;
        this.timeRemaining = timeLimit;
        this.finalZoneX = finalZoneX;
        this.finalZoneY = finalZoneY;
    }

    // Se llama cada frame desde el game loop. delta ya es relativo al frame (≈1.0 a 60fps)
    // dividimos por TARGET_FPS una sola vez para convertir frames → segundos
    public void updateTime(double delta) {
        timeRemaining -= delta / GameConfig.TARGET_FPS;
    }

    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Enemy> getEnemies() {
        return entities.stream()
                .filter(e -> e instanceof Enemy)
                .map(e -> (Enemy) e)
                .collect(Collectors.toList());
    }

    public void cleanInactiveEntities() {
        entities.removeIf(e -> {
            if (e instanceof Bomb)  return !((Bomb) e).isActive();
            if (e instanceof Live)  return !((Live) e).isActive();
            if (e instanceof Enemy) return !((Enemy) e).isAlive();
            if (e instanceof Coin) return ((Coin) e).isCollected();
            return false;
        });
    }

    public int getTotalCoins() {
        return (int) entities.stream()
                .filter(e -> e instanceof Coin)
                .count();
    }

    public boolean allCoinsCollected() {
        return entities.stream()
                .filter(e -> e instanceof Coin)
                .allMatch(e -> ((Coin) e).isCollected());
    }

    public Cell[][] getGrid()        { return grid; }
    public Player getPlayer()        { return player; }
    public double getTimeRemaining() { return timeRemaining; }
    public double getTimeLimit()     { return timeLimit; }
    public int getFinalZoneX()       { return finalZoneX; }
    public int getFinalZoneY()       { return finalZoneY; }
}