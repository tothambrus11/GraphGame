package hu.johetajava;

import java.util.Objects;

public class Connection {
    Point point1;
    Point point2;

    public Connection(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
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
}
