package hu.johetajava;

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


    static float getDistance(Point start, Point target){

        System.err.println("START: " + start);
        System.err.println("TARGET: " + target);

        if(start.equals(target)){
            return 0;
        }

        float bestDistance = Controller.infinity;
        Point bestNeighbour = null;

        ArrayList<Point> neighbours = start.getNeighbours();
        System.err.println(Arrays.toString(neighbours.toArray()));

        for(Point nextPoint : neighbours){
            if(nextPoint.equals(start)) continue;
            float distance = start.getDistance(nextPoint);
            if(distance <= bestDistance){
                bestDistance = distance;
                bestNeighbour = nextPoint;
            }
        }

        return bestDistance + getDistance(bestNeighbour, target);
    }
}
