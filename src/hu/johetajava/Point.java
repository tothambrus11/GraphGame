package hu.johetajava;

import java.util.Objects;

public class Point {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    static Point getNearestPoint(Point point, float threshold) {

        float bestDistance = threshold;
        Point bestPoint = null;

        float distance;
        for(Point cross : World.crosses){
            distance = (float) Math.sqrt((cross.x - point.x)*(cross.x - point.x) + (cross.x - point.x)*(cross.y - point.y));
            if(distance <= bestDistance){
                bestDistance = distance;
                bestPoint = cross;
            }
        }
        return bestPoint;
    }

    public float getDistance(Point otherPonint) {
        return getDistance(this, otherPonint);
    }

    public static float getDistance(Point point1, Point point2) {
        float deltaX = point1.getX() - point2.getX();
        float deltaY = point1.getY() - point2.getY();
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }


    public float getX() {
        if (x < 0) x = 0;
        else if (x > World.width) x = World.width;
        return x;
    }

    public void setX(float x) {
        this.x = x;
        getX();
    }

    public float getY() {
        if (y < 0) y = 0;
        else if (y > World.height) y = World.height;
        return y;
    }

    public void setY(float y) {
        this.y = y;
        getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 &&
                Float.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
