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
        System.out.println("============ getDistance");
        System.out.println("START: " + start);
        System.out.println("TARGET: " + target);
        System.out.println("prev: " + Arrays.toString(prev.toArray()));

        if(start.equals(target)){
            System.out.println("Találtam utat!");
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
        System.out.println("Neighbours: " + Arrays.toString(neighbours.toArray()));

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

        if(bestNeighbour == null){
            System.out.println("Ez egy zsákutca");
            return Controller.infinity;
        }
        System.out.println("Best neighbour: " + bestNeighbour + "    d=" + bestDistance);
        return bestDistance;
    }
}
