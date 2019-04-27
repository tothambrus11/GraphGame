package hu.johetajava;

import java.util.Objects;

public class Connection {
    Point point1;
    Point point2;

    public Connection(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public static boolean isConnectedTo(Point point1, Point point2) {
        for (Connection connection : World.connections) {
            if ((connection.point1.equals(point1) && connection.point2.equals(point2)) || (connection.point2.equals(point1) && connection.point1.equals(point2))) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return Objects.equals(point1, that.point1) &&
                Objects.equals(point2, that.point2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1, point2);
    }

    public static void connect(Point a, Point b) {
        if (isConnectedTo(a, b)) {
            System.err.println("Már connectelve vannak!");
            return;
        }
        if (a == null || b == null) {
            System.err.println("Nincs kijelölve 2 pont.");
            return;
        }
        World.connections.add(new Connection(a, b));
    }

    public float getLength() {
        return point1.getDistance(point2);
    }

}
