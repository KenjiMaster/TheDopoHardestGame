package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private CardLayout layout;
    private JPanel contenedor;
    private HardestGame game;
    private Timer gameTimer;

    private boolean izquierda, derecha, arriba, abajo;

    public GamePanel(CardLayout layout, JPanel contenedor) {
        this.layout = layout;
        this.contenedor = contenedor;
        this.game = new HardestGame();
        prepareElements();
        prepareActions();
        prepareTimer();
    }

    private void prepareElements() {
        setPreferredSize(new Dimension(800, 500));
        setFocusable(true);
    }

    private void prepareActions() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT,  KeyEvent.VK_A -> izquierda = true;
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> derecha   = true;
                    case KeyEvent.VK_UP,    KeyEvent.VK_W -> arriba    = true;
                    case KeyEvent.VK_DOWN,  KeyEvent.VK_S -> abajo     = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT,  KeyEvent.VK_A -> izquierda = false;
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> derecha   = false;
                    case KeyEvent.VK_UP,    KeyEvent.VK_W -> arriba    = false;
                    case KeyEvent.VK_DOWN,  KeyEvent.VK_S -> abajo     = false;
                }
            }
        });
    }

    private void prepareTimer() {
        gameTimer = new Timer(16, e -> {
            int dx = 0, dy = 0;
            if (izquierda) dx -= 1;
            if (derecha)   dx += 1;
            if (arriba)    dy -= 1;
            if (abajo)     dy += 1;

            game.movePlayer(dx, dy);
            game.update();

            if (game.isLevelComplete()) {
                gameTimer.stop();
                layout.show(contenedor, "MENU");
            }

            repaint();
        });
    }

    public void iniciar() {
        game = new HardestGame();
        izquierda = derecha = arriba = abajo = false;
        gameTimer.start();
        requestFocusInWindow();
    }

    public void detener() {
        gameTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Level level = game.getLevel();
        int cellSize = Level.CELL_SIZE;

        int offsetX = (getWidth()  - level.getWidthPixels())  / 2;
        int offsetY = (getHeight() - level.getHeightPixels()) / 2;

        g2.translate(offsetX, offsetY);

        dibujarFondo(g2, level, cellSize);
        dibujarCeldas(g2, level, cellSize);
        dibujarEnemigos(g2);
        dibujarJugador(g2);
        dibujarHUD(g2, level);

        g2.translate(-offsetX, -offsetY);
    }

    private void dibujarFondo(Graphics2D g2, Level level, int cellSize) {
        g2.setColor(new Color(173, 216, 230));
        g2.fillRect(0, 0, level.getWidthPixels(), level.getHeightPixels());
    }

    private void dibujarCeldas(Graphics2D g2, Level level, int cellSize) {
        Cell[][] grid = level.getGrid();
        for (int fila = 0; fila < grid.length; fila++) {
            for (int col = 0; col < grid[fila].length; col++) {
                Cell cell = grid[fila][col];
                int px = col * cellSize;
                int py = fila * cellSize;

                switch (cell.getType()) {
                    case WALL -> {
                        g2.setColor(new Color(50, 50, 80));
                        g2.fillRect(px, py, cellSize, cellSize);
                    }
                    case SAFE_ZONE_START, SAFE_ZONE_END -> {
                        g2.setColor(new Color(144, 238, 144));
                        g2.fillRect(px, py, cellSize, cellSize);
                        g2.setColor(new Color(100, 200, 100));
                        g2.drawRect(px, py, cellSize, cellSize);
                    }
                    case WALKABLE -> {
                        g2.setColor(Color.WHITE);
                        g2.fillRect(px, py, cellSize, cellSize);
                        g2.setColor(new Color(200, 200, 210));
                        g2.drawRect(px, py, cellSize, cellSize);
                    }
                }
            }
        }
    }

    private void dibujarEnemigos(Graphics2D g2) {
        g2.setColor(new Color(30, 100, 220));
        for (Enemy enemy : game.getEnemies()) {
            int ex = enemy.getX();
            int ey = enemy.getY();
            int s  = Enemy.SIZE;
            g2.fillOval(ex, ey, s, s);
            g2.setColor(new Color(10, 60, 180));
            g2.drawOval(ex, ey, s, s);
            g2.setColor(new Color(30, 100, 220));
        }
    }

    private void dibujarJugador(Graphics2D g2) {
        Player player = game.getPlayer();
        int px = player.getX();
        int py = player.getY();
        int s  = Player.SIZE;

        g2.setColor(new Color(220, 30, 30));
        g2.fillRect(px, py, s, s);
        g2.setColor(new Color(150, 10, 10));
        g2.drawRect(px, py, s, s);
    }

    private void dibujarHUD(Graphics2D g2, Level level) {
        g2.setColor(new Color(20, 20, 20));
        g2.fillRect(0, -30, level.getWidthPixels(), 30);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2.drawString("DEATHS: " + game.getDeaths(), 10, -10);
    }
}