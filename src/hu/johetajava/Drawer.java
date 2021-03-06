package hu.johetajava;

import processing.core.PApplet;

public class Drawer extends PApplet {

    private static int backgroundColor;
    private int roadColor;

    private static float unit = 30;

    private static String title = "Gráfgém";
    private Point connectFrom;
    private Point connectTo;
    private float pointDiameter = 30;
    private int pointColor;
    private int selectedPointColor;
    private float messagePadding;
    private boolean moving;
    private float percent = 0;
    public static Point target;


    public void setup() {
        Controller.drawer = this;
        surface.setTitle(title);

    }

    public void settings() {
        size(850, 600);

        messagePadding = 15;

        // Colors:
        backgroundColor = color(9, 24, 48); // dark green
        roadColor = color(200);
        pointColor = color(66, 134, 244);
        selectedPointColor = color(65, 102, 163);


        Controller.init();
    }


    public void draw() {


        background(backgroundColor);
        fill(100);

        drawMap();
        drawConnections();

        if (keyPressed && keyCode == 16 && connectFrom != null && connectTo == null) {
            drawConnection(new Connection(connectFrom, new Point(fromPixels(mouseX), fromPixels(mouseY))), 0.6f);
        }

        drawPoints();


        drawMessage();

        if(World.target != null){
            drawAPoint(World.target, color(40,250,40), 12);
        }

        if (moving) {
            percent += 3 / World.car.getDistance(target);
            drawMovingCar();

            System.err.println(percent);
            if (percent >= 100) {
                percent = 0;
                World.car = target;
                target = null;
                moving = false;
            }
        } else {
            Controller.onTick();

            drawCar();
        }
        if (moving) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    private void drawCar() {
        if (World.car != null) drawCar(World.car);
    }

    private void drawCar(Point point) {
        drawAPoint(point, color(255), 12);
    }

    private void drawMessage() {
        fill(130);
        textSize(11);
        text(getMessage(), toPixels(World.width) + messagePadding, messagePadding, width - toPixels(World.width) - 2 * messagePadding, height - 2 * messagePadding);
    }

    private String getMessage() {
        String message = "";
        message += "Irányítás:";
        message += "\n   kattintás: új pont létrehozása";
        message += "\n    shift + kattintás: pontok összekötése";
        message += "\n    F: kiindulási hely kijelölése";
        message += "\n    T: cél kijelölése";

        return message;
    }

    private void drawConnections() {
        for (Connection connection : World.connections) {
            drawConnection(connection, 2);
        }
    }

    private void drawConnection(Connection connection, float width) {
        stroke(roadColor);
        strokeWeight(width);
        line(toPixels(connection.point1.getX()), toPixels(connection.point1.getY()), toPixels(connection.point2.getX()), toPixels(connection.point2.getY()));

        float x = toPixels(Math.min(connection.point1.getX(), connection.point2.getX()) + (Math.abs(connection.point1.getX() - connection.point2.getX()) / 2));
        float y = toPixels(Math.min(connection.point1.getY(), connection.point2.getY()) + (Math.abs(connection.point1.getY() - connection.point2.getY()) / 2));

        fill(0, 0, 0, 100);
        rect(x, y + 2, 40, -14);

        fill(255);
        textSize(11);
        text(connection.getLength(), x, y);
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
            case 'f':
                World.car = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                break;
            case 't':
                World.target = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                break;
            case 'z':
                if (World.crosses.size() > 0) {
                    Point lastCross = World.crosses.get(World.crosses.size() - 1);

                    World.crosses.remove(lastCross);
                    World.connections.removeIf(connection -> connection.point1 == lastCross || connection.point2 == lastCross);
                }
                break;

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
        if (!keyPressed) {
            World.crosses.add(new Point(fromPixels(mouseX), fromPixels(mouseY)));
        } else if (keyCode == 16) {
            if (connectFrom == null) {
                connectFrom = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
            } else {
                connectTo = Point.getNearestPoint(new Point(fromPixels(mouseX), fromPixels(mouseY)), fromPixels(pointDiameter / 2.0f));
                if (connectTo == null) {
                    System.err.println("Félreklikkeltél");
                } else if (connectTo.equals(connectFrom)) {
                    System.err.println("Nem lehet ugyanaz a to és a from");
                } else {
                    Connection.connect(connectFrom, connectTo);
                    connectTo = null;
                    connectFrom = null;
                }

            }
        }
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

    private void drawMovingCar() {
        Point from = World.car.copy();

        drawCar(new Point(
                from.getX() + (target.getX() - from.getX()) * (percent / 100.0f),
                from.getY() + (target.getY() - from.getY()) * (percent / 100.0f)
        ));
    }

    public void moveCarTo(Point to) {
        moving = true;
        target = to.copy();
    }
}
