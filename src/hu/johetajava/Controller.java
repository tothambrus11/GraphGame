package hu.johetajava;

import processing.core.PApplet;

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
}
