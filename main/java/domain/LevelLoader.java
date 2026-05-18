package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    private static final int GRID_ROWS = 10;
    private static final int GRID_COLS = 20;

    public Level load(String fileName) throws HardestGameException {
        List<String> lines = readFile(fileName);

        Cell[][] grid          = parseGrid(lines);
        List<Entity> entities  = new ArrayList<>();
        Player player          = parseEntities(lines, entities);
        double timeLimit       = parseTimeLimit(lines);
        int[] finalZone        = parseFinalZone(lines);

        return new Level(grid, entities, player, timeLimit, finalZone[0], finalZone[1]);
    }

    // Lee todas las líneas del archivo
    private List<String> readFile(String fileName) throws HardestGameException {
        List<String> lines = new ArrayList<>();
        InputStream is = LevelLoader.class.getResourceAsStream("/" + fileName);

        if (is == null) throw new HardestGameException("Archivo no encontrado: " + fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new HardestGameException("Error al leer el archivo: " + fileName);
        }

        return lines;
    }

    // Parsea las primeras 10 líneas como grilla
    private Cell[][] parseGrid(List<String> lines) throws HardestGameException {
        if (lines.size() < GRID_ROWS) {
            throw new HardestGameException("El archivo no tiene las " + GRID_ROWS + " filas requeridas");
        }

        Cell[][] grid = new Cell[GRID_ROWS][GRID_COLS];

        for (int i = 0; i < GRID_ROWS; i++) {
            String line = lines.get(i);

            if (line.length() != GRID_COLS) {
                throw new HardestGameException("La fila " + i + " no tiene " + GRID_COLS + " columnas");
            }

            for (int j = 0; j < GRID_COLS; j++) {
                grid[i][j] = parseCell(line.charAt(j));
            }
        }

        return grid;
    }

    // Parsea las líneas después de la grilla — llena entities y retorna el player
    private Player parseEntities(List<String> lines, List<Entity> entities) throws HardestGameException {
        Player player = null;

        for (int i = GRID_ROWS; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            String type = parts[0];

            switch (type) {
                case "PLAYER":  player = parsePlayer(parts);         break;
                case "BASIC":   entities.add(parseBasic(parts));     break;
                case "PATROL":  entities.add(parsePatrol(parts));    break;
                case "BOMB":    entities.add(parseBomb(parts));      break;
                case "LIVE":    entities.add(parseLive(parts));      break;
                case "QUICK":   entities.add(parseQuick(parts));   break;
                case "SLIDING": entities.add(parseSliding(parts)); break;
                case "CYELLOW": entities.add(parseCYellow(parts)); break;
                case "TIME":    break; // se procesa en parseTimeLimit
                case "FINAL":   break; // se procesa en parseFinalZone
                default:
                    throw new HardestGameException("Tipo desconocido: " + type);
            }
        }

        if (player == null) {
            throw new HardestGameException("El archivo no tiene definido el PLAYER");
        }

        return player;
    }

    // Busca la línea TIME xx y retorna el valor en segundos
    // formato: TIME 60
    private double parseTimeLimit(List<String> lines) throws HardestGameException {
        for (int i = GRID_ROWS; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.startsWith("TIME")) {
                String[] parts = line.split(" ");
                return Double.parseDouble(parts[1]);
            }
        }
        throw new HardestGameException("El archivo no tiene definido el TIME");
    }

    // Busca la línea FINAL x y y retorna la posición en píxeles
    // formato: FINAL 18 1
    private int[] parseFinalZone(List<String> lines) throws HardestGameException {
        for (int i = GRID_ROWS; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.startsWith("FINAL")) {
                String[] parts = line.split(" ");
                int x = (int)(Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE);
                int y = (int)(Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE);
                return new int[]{ x, y };
            }
        }
        throw new HardestGameException("El archivo no tiene definido el FINAL");
    }

    // formato: CYELLOW x y
    private CYellow parseCYellow(String[] parts) {
        double x = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;
        return new CYellow(x, y);
    }

    // Parsea una celda según su caracter
    private Cell parseCell(char c) throws HardestGameException {
        switch (c) {
            case '#': return new Cell(CellType.WALL);
            case 'S': return new Cell(CellType.INITIAL_ZONE);
            case 'W': return new Cell(CellType.WALKABLE);
            case 'F': return new Cell(CellType.FINAL_ZONE);
            case 'C': return new Cell(CellType.CHECK_ZONE);
            default:  throw new HardestGameException("Caracter desconocido en grilla: " + c);
        }
    }

    // formato: PLAYER x y SKIN
    private Player parsePlayer(String[] parts) throws HardestGameException {
        int x = (int)(Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE);
        int y = (int)(Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE);
        PlayerSkin skin = parseSkin(parts[3]);
        return new Player(skin, x, y);
    }

    private PlayerSkin parseSkin(String skinName) throws HardestGameException {
        switch (skinName.toUpperCase()) {
            case "BLINKY": return new Blinky();
            case "INKY":   return new Inky();
            case "CLYDE":  return new Clyde();
            default: throw new HardestGameException("Skin desconocido: " + skinName);
        }
    }

    // formato: BASIC x y DIRECTION
    private Basic parseBasic(String[] parts) throws HardestGameException {
        double x   = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y   = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;
        Direction dir = Direction.valueOf(parts[3]);
        return new Basic(x, y, GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SPEED, dir);
    }

    // formato: PATROL x y wp1x wp1y wp2x wp2y ...
    private Patrol parsePatrol(String[] parts) throws HardestGameException {
        double x = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;

        if (parts.length < 7 || (parts.length - 3) % 2 != 0) {
            throw new HardestGameException("PATROL necesita pares de waypoints: PATROL x y wp1x wp1y wp2x wp2y ...");
        }

        List<double[]> waypoints = new ArrayList<>();
        for (int i = 3; i < parts.length; i += 2) {
            double wpX = Double.parseDouble(parts[i])     * GameConfig.CELL_SIZE;
            double wpY = Double.parseDouble(parts[i + 1]) * GameConfig.CELL_SIZE;
            waypoints.add(new double[]{ wpX, wpY });
        }

        return new Patrol(x, y, GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SPEED, waypoints);
    }

    // formato: QUICK x y DIRECTION
    private Quick parseQuick(String[] parts) throws HardestGameException {
        double x      = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y      = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;
        Direction dir = Direction.valueOf(parts[3]);
        return new Quick(x, y, GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SPEED, dir);
    }

    // formato: SLIDING x y DIRECTION
    private Sliding parseSliding(String[] parts) throws HardestGameException {
        double x      = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y      = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;
        Direction dir = Direction.valueOf(parts[3]);
        return new Sliding(x, y, GameConfig.ENEMY_SIZE, GameConfig.ENEMY_SPEED, dir);
    }

    // formato: BOMB x y
    private Bomb parseBomb(String[] parts) {
        double x = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;
        return new Bomb(x, y);
    }

    // formato: LIVE x y
    private Live parseLive(String[] parts) {
        double x = Double.parseDouble(parts[1]) * GameConfig.CELL_SIZE;
        double y = Double.parseDouble(parts[2]) * GameConfig.CELL_SIZE;
        return new Live(x, y);
    }
}
