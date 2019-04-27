package hu.johetajava;

import processing.core.PApplet;

public class Drawer extends PApplet {

    private static int backgroundColor;
    private int roadColor;

    private static float unit = 30;

    private static String title = "Hello World!";
    private Point connectFrom;
    private Point connectTo;
    private Mode mode = Mode.createPoints;
    private float pointDiameter = 30;
    private int pointColor;
    private int selectedPointColor;
    private float messagePadding;


    public void setup() {
        Controller.drawer = this;
        surface.setTitle(title);

    }

    public void settings() {
        size(800, 600);

        messagePadding = 15;

        // Colors:
        backgroundColor = color(20, 60, 30); // dark green
        roadColor = color(200);
        pointColor = color(66, 134, 244);
        selectedPointColor = color(65, 102, 163);


        Controller.init();
    }


    public void draw() {

        if (mode == Mode.play) {
            Controller.onTick();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        background(backgroundColor);
        fill(100);

        drawMap();

        drawPoints();

        drawConnections();

        drawCar();

        drawMessage();
    }

    private void drawCar() {
        if (World.car != null) drawAPoint(World.car, color(255), 12);
    }

    private void drawMessage() {
        fill(130);
        textSize(11);
        text(getMessage(), toPixels(World.width) + messagePadding, messagePadding, width - toPixels(World.width) - 2 * messagePadding, height - 2 * messagePadding);
    }

    private String getMessage() {
        String message = "";
        message += "MODE: " + mode.name();
        return message;
    }

    private void drawConnections() {
        for (Connection connection : World.connections) {
            stroke(roadColor);
            strokeWeight(2);
            line(toPixels(connection.point1.getX()), toPixels(connection.point1.getY()), toPixels(connection.point2.getX()), toPixels(connection.point2.getY()));
            fill(255);
            textSize(20);
            text(connection.getLength(),
                    toPixels(Math.min(connection.point1.getX(), connection.point2.getX()) + (Math.abs(connection.point1.getX() - connection.point2.getX()) / 2)) + 5,
                    toPixels(Math.min(connection.point1.getY(), connection.point2.getY()) + (Math.abs(connection.point1.getY() - connection.point2.getY()) / 2)) + 5
            );
        }
    }

    private void drawPoints() {
        for (Point point : World.crosses) {
            if (point == connectFrom || point == connectTo) {
                drawAPoint(point, selectedPointColor, pointDiameter);
            } else {
                drawAPoint(point);
            }

        }
    }


    public void keyTyped() {
        switch (key) {
            case 'c':
                mode = Mode.createPoints;
                break;
            case 'p':
                mode = Mode.play;
                break;
        }
        switch (mode.name()) {
            case "play":
                switch (key) {
                    case 'f':
                        World.car = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                        break;
                    case 't':
                        World.target = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                        break;
                }
                break;
            case "createPoints":
                switch (key) {
                    case 'f':

                        Point nearestPoint = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                        if (connectFrom != null && connectTo != null && nearestPoint == connectTo) {
                            System.err.println("Nem lehet ugyanaz a to és a from");
                        } else {
                            connectFrom = nearestPoint;
                        }
                        break;
                    case ' ':
                        nearestPoint = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                        if (connectTo != null && connectFrom != null && connectFrom == nearestPoint) {
                            System.err.println("Nem lehet ugyanaz a to és a from");
                        } else {
                            connectTo = nearestPoint;
                        }

                        Connection.connect(connectFrom, connectTo);
                        connectTo = null;
                        connectFrom = null;
                        break;
                    case 'z':
                        if (World.crosses.size() > 0) {
                            Point lastCross = World.crosses.get(World.crosses.size() - 1);

                            World.crosses.remove(lastCross);
                            World.connections.removeIf(connection -> connection.point1 == lastCross || connection.point2 == lastCross);
                        }

                }
        }

    }

    private void drawAPoint(Point point) {
        drawAPoint(point, pointColor, pointDiameter);
    }

    private void drawAPoint(Point point, int color, float diameter) {
        fill(color);
        noStroke();
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
