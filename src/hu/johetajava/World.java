package hu.johetajava;

import java.lang.reflect.Array;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;

public class World {

    public static Point car;
    public static Point target;
    static float width = 20;
    static float height = 20;

    static ArrayList<Point> crosses;
    static ArrayList<Connection> connections;

    World(ArrayList<Point> crosses, ArrayList<Connection> connections) {
        crosses = crosses;
        connections = connections;
    }

    World(){
        crosses = new ArrayList<>();
        connections = new ArrayList<>();
    }


    static float getDistance(Point start, ArrayList<Point> prev){

        if(start.equals(target)){
            return 0;
        }


        ArrayList<Point> prev2 = new ArrayList<>();
        for(Point point : prev){
            prev2.add(point.copy());
        }
        prev2.add(start);

        float bestDistance = Controller.infinity;
        Point bestNeighbour = null;

        ArrayList<Point> neighbours = start.getNeighbours();
        for(Point nextPoint : neighbours){
            boolean contains = false;
            for (Point p : prev2) {
                if(p.equals(nextPoint)){
                    contains = true;
                    break;
                }
            }

            if(!contains) {
                float distance = start.getDistance(nextPoint) + getDistance(nextPoint.copy(), prev2);
                if (distance <= bestDistance) {
                    bestDistance = distance;
                    bestNeighbour = nextPoint;
                }
            }
        }

        // Dead end road
        if(bestNeighbour == null){
            return Controller.infinity;
        }
        //System.err.println("Best neighbour: " + bestNeighbour + "    d=" + bestDistance);
        return bestDistance;
    }
}
