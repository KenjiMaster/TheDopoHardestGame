package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    private CardLayout layout;
    private JPanel contenedor;
    private HardestGame game;
    private Cell[][] grid;
    private static final int CELL_SIZE = GameConfig.CELL_SIZE;

    // Game loop
    private Thread gameThread;
    private boolean running;
    private static final int TARGET_FPS = 60;
    private static final double NS_PER_FRAME = 1_000_000_000.0 / TARGET_FPS;

    // Input
    private boolean izquierda, derecha, arriba, abajo;

    public GamePanel(CardLayout layout, JPanel contenedor) {
        this.layout = layout;
        this.contenedor = contenedor;
        this.game = new HardestGame();
        this.grid = game.getLevel().getGrid();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  izquierda = true; break;
                    case KeyEvent.VK_RIGHT: derecha   = true; break;
                    case KeyEvent.VK_UP:    arriba    = true; break;
                    case KeyEvent.VK_DOWN:  abajo     = true; break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  izquierda = false; break;
                    case KeyEvent.VK_RIGHT: derecha   = false; break;
                    case KeyEvent.VK_UP:    arriba    = false; break;
                    case KeyEvent.VK_DOWN:  abajo     = false; break;
                }
            }
        });

        addHierarchyListener(e -> {
            if (isShowing()) requestFocusInWindow();
        });
    }

    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            double delta = (currentTime - lastTime) / NS_PER_FRAME;
            lastTime = currentTime;

            update(delta);
            SwingUtilities.invokeLater(this::repaint);

            long sleepTime = (long)((lastTime + NS_PER_FRAME - System.nanoTime()) / 1_000_000);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void update(double delta) {
        game.getLevel().updateTime(delta);
        game.getLevel().getPlayer().updateInvincibility();
        Direction direction = getDirectionFromInput();
        if (direction != null) {
            game.getLevel().getPlayer().move(direction, delta, grid);
        }

        for (Enemy enemy : game.getLevel().getEnemies()) {
            enemy.update(delta, grid);
        }

        checkCollisions();
        game.getLevel().cleanInactiveEntities();
    }

    private void checkCollisions() {
        Player player = game.getLevel().getPlayer();
        List<Entity> entities = game.getLevel().getEntities();

        // enemigos contra entidades interactuables (bombas)
        for (Enemy enemy : game.getLevel().getEnemies()) {
            if (!enemy.isAlive()) continue;
            for (Entity entity : entities) {
                if (entity instanceof Interactable && enemy.collition(entity)) {
                    ((Interactable) entity).interact(enemy);
                }
            }
        }

        if (player.isInvincible()) return;

        // jugador contra todas las entidades
        for (Entity entity : entities) {
            if (player.collition(entity) && entity instanceof Interactable) {
                ((Interactable) entity).interact(player);
            }
        }
    }

    private Direction getDirectionFromInput() {
        if (arriba && derecha)   return Direction.NORTH_EAST;
        if (arriba && izquierda) return Direction.NORTH_WEST;
        if (abajo && derecha)    return Direction.SOUTH_EAST;
        if (abajo && izquierda)  return Direction.SOUTH_WEST;
        if (arriba)              return Direction.NORTH;
        if (abajo)               return Direction.SOUTH;
        if (derecha)             return Direction.EAST;
        if (izquierda)           return Direction.WEST;
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. grilla (fondo)
        drawGrid(g2d);

        // 2. entidades sobre el fondo
        drawEntities(g2d, game.getLevel().getEntities());

        // 3. jugador encima de todo
        drawPlayer(g2d, game.getLevel().getPlayer());
    }

    private void drawGrid(Graphics2D g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                drawCell(g, grid[i][j], j * CELL_SIZE, i * CELL_SIZE);
            }
        }
    }

    private void drawCell(Graphics2D g, Cell cell, int x, int y) {
        switch (cell.getType()) {
            case WALL:         g.setColor(Color.BLACK); break;
            case INITIAL_ZONE: g.setColor(Color.GREEN); break;
            case WALKABLE:     g.setColor(Color.WHITE); break;
            case FINAL_ZONE:   g.setColor(new Color(0, 180, 0));  break;
            default:           g.setColor(Color.GRAY);  break;
        }
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    private void drawEntities(Graphics2D g, List<Entity> entities) {
        for (Entity entity : entities) {
            Color color = getEntityColor(entity);
            if (color == null) continue;

            Rectangle2D hitBox = entity.getHitBox();
            g.setColor(color);
            g.fill(hitBox);
            g.setColor(Color.DARK_GRAY);
            g.draw(hitBox);
        }
    }

    // Centraliza la lógica de color por tipo de entidad
    private Color getEntityColor(Entity entity) {
        if (entity instanceof Enemy)  return Color.BLUE;
        if (entity instanceof Bomb)   return ((Bomb) entity).isActive()   ? Color.ORANGE : null;
        if (entity instanceof Live)   return ((Live) entity).isActive()   ? Color.PINK   : null;
        if (entity instanceof CYellow) return ((CYellow) entity).isCollected() ? null : Color.YELLOW;
        return Color.YELLOW; // monedas u otros
    }

    private void drawPlayer(Graphics2D g, Player player) {
        Rectangle2D hitBox = player.getHitBox();
        g.setColor(player.getSkin().getColor());
        g.fill(hitBox);
        g.setColor(Color.DARK_GRAY);
        g.draw(hitBox);
    }
}