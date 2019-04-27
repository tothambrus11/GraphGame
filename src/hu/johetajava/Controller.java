package hu.johetajava;

import processing.core.PApplet;

import java.lang.reflect.Array;
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
        System.err.println("TICK ============================");
        if(car == null || World.target == null){
            return;
        }


        if(car.equals(target)){
            System.err.println("Megjöttünk!");
            return;
        }
        float bestDistance = Controller.infinity;
        Point bestNeighbour = null;

        ArrayList<Point> neighbours = car.getNeighbours();

        ArrayList<Point> prev  = new ArrayList<>();
        prev.add(car);
        for(Point nextPoint : neighbours){
            ArrayList<Point> prev2 = new ArrayList<>();
            for(Point p : prev){
                prev2.add(p.copy());
            }

            float distance = car.getDistance(nextPoint) + getDistance(nextPoint, prev2);
            if(distance <= bestDistance){
                bestDistance = distance;
                bestNeighbour = nextPoint;
            }
        }
        if (bestNeighbour != null) {
            drawer.moveCarTo(bestNeighbour);
        }
        else{
            System.err.println("Nincs út a célpont felé");
        }

    }
}
