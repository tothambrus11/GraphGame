package hu.johetajava;

import processing.core.PApplet;

public class Drawer extends PApplet {

    private static int backgroundColor;
    private int roadColor;

    private static float unit = 30;

    private static String title = "Hello World!";
    private Point connectFrom;
    private Point connectTo;

    public void setup() {
        Controller.drawer = this;
        surface.setTitle(title);

    }

    public void settings() {
        size(800, 600);

        // Colors:
        backgroundColor = color(20, 60, 30); // dark green
        roadColor = color(200);
        Controller.init();
    }


    public void draw() {
        background(backgroundColor);
        fill(100);

        drawMap();

        drawPoints();

        drawConnections();
    }

    private void drawConnections() {
        for(Connection connection : World.connections){
            stroke(roadColor);
            strokeWeight(2);
            line(toPixels(connection.point1.getX()),toPixels(connection.point1.getY()), toPixels(connection.point2.getX()), toPixels(connection.point2.getY()));
        }
    }

    private void drawPoints() {
        for (Point point : World.crosses) {
            if(point == connectFrom || point == connectTo){
                drawAPoint(point, color(255, 0, 0, 80), 10);
            }
            else{
                drawAPoint(point);
            }

        }
    }


    public void keyTyped() {
        switch (key) {
            case 'f':
                connectFrom = Point.getNearestPoint(new Point(fromPixels(mouseX),fromPixels(mouseY)), fromPixels(7.5f));
                break;
            case 't':
                connectTo = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(7.5f));
                break;
            case 'c':
                World.connections.add(new Connection(connectFrom, connectTo));
                connectTo = null;
                connectFrom = null;
                break;

        }
    }

    private void drawAPoint(Point point) {
        drawAPoint(point, color(200, 200, 255), 15);
    }
    private void drawAPoint(Point point, int color, float diameter){
        fill(color);
        ellipse(toPixels(point.getX()), toPixels(point.getY()), diameter, diameter);
    }


        public void mouseClicked() {
        World.crosses.add(new Point(fromPixels(mouseX), fromPixels(mouseY)));

    }

    void drawMap() {
        for (int x = 1; x <= World.width; x++) {
            drawVerticalLine(x);
        }
        for (int y = 1; y <= World.height; y++) {
            drawHorizontalLine(y);
        }
    }

    void drawHorizontalLine(float y) {
        strokeWeight(0.2f);
        stroke(255);
        line(toPixels(0), toPixels(y), toPixels(World.width), toPixels(y));
    }

    void drawVerticalLine(float x) {
        stroke(255);
        strokeWeight(0.2f);
        line(toPixels(x), toPixels(0), toPixels(x), toPixels(World.height));
    }


    float toPixels(float unitValue) {
        return unitValue * unit;
    }

    float fromPixels(float pixelValue) {
        return pixelValue / unit;
    }

}
