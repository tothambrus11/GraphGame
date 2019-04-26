package hu.johetajava;

import java.util.ArrayList;

public class World {

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


}
