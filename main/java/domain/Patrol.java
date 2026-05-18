package domain;

import java.util.List;
import java.util.ArrayList;

public class Patrol extends Enemy {

    private List<double[]> waypoints; // lista de puntos [x, y] que patrulla
    private int currentWaypoint;
    private static final double WAYPOINT_THRESHOLD = 2.0; // distancia para considerar que llegó

    public Patrol(double x, double y, double size, double speed, List<double[]> waypoints) throws HardestGameException {
        super(x, y, size, speed, Direction.EAST);

        if (waypoints == null || waypoints.size() < 2) {
            throw new HardestGameException("Patrol necesita al menos 2 waypoints");
        }

        this.waypoints = waypoints;
        this.currentWaypoint = 0;
    }

    @Override
    public Direction calculateNextDirection(Cell[][] grid) {
        double[] target = waypoints.get(currentWaypoint);
        double targetX = target[0];
        double targetY = target[1];

        // si llegó al waypoint actual, avanzar al siguiente
        double distX = Math.abs(XPosition - targetX);
        double distY = Math.abs(YPosition - targetY);

        if (distX < WAYPOINT_THRESHOLD && distY < WAYPOINT_THRESHOLD) {
            currentWaypoint = (currentWaypoint + 1) % waypoints.size(); // vuelve al inicio al terminar
            target = waypoints.get(currentWaypoint);
            targetX = target[0];
            targetY = target[1];
        }

        // calcular dirección hacia el waypoint actual
        return directionToTarget(targetX, targetY);
    }

    // Calcula la dirección cardinal hacia el target
    private Direction directionToTarget(double targetX, double targetY) {
        double dx = targetX - XPosition;
        double dy = targetY - YPosition;

        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? Direction.EAST : Direction.WEST;
        } else {
            return dy > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    // Método de utilidad para crear waypoints desde el loader
    public static List<double[]> buildWaypoints(int[][] cellCoords) {
        List<double[]> waypoints = new ArrayList<>();
        for (int[] coord : cellCoords) {
            waypoints.add(new double[]{
                    coord[0] * GameConfig.CELL_SIZE,
                    coord[1] * GameConfig.CELL_SIZE
            });
        }
        return waypoints;
    }
}
