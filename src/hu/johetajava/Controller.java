package hu.johetajava;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;

import static hu.johetajava.World.*;

public class Controller {

    public static float infinity = 1000000.0f;
    static Drawer drawer;
    private static World world;

    public static void main(String[] args) {

        world = new World();
        PApplet.main("hu.johetajava.Drawer", args);

    }


    public static void init() {

    }

    public static void onTick() {
        if(car == null || World.target == null){
            return;
        }


        if(car.equals(target)){
            System.out.println("Megerkeztünk");
            return;
        }
        float bestDistance = Controller.infinity;
        Point bestNeighbour = null;

        ArrayList<Point> neighbours = car.getNeighbours();
        System.err.println(Arrays.toString(neighbours.toArray()));

        for(Point nextPoint : neighbours){
            float distance = car.getDistance(nextPoint);
            if(distance <= bestDistance){
                bestDistance = distance;
                bestNeighbour = nextPoint;
            }
        }
        if (bestNeighbour != null) {
            car = bestNeighbour;
        }

    }
}
