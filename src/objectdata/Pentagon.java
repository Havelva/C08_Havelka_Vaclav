package objectdata;

import java.awt.*;
import java.util.ArrayList;

public class Pentagon extends Polygon {
    private Point center;
    private int radius;
    private double rotationAngle;

    public Pentagon(Point center, int radius, double rotationAngle) {
        super();
        this.center = center;
        this.radius = radius;
        this.rotationAngle = rotationAngle;
        setColor(Color.GREEN);
        calculateVertices();
    }

    private void calculateVertices() {
        ArrayList<Point> vertices = new ArrayList<>();
        double angle = 2 * Math.PI / 5;

        for (int i = 0; i < 5; i++) {
            double rotatedAngle = i * angle + rotationAngle;
            int x = (int) (center.getX() + radius * Math.cos(rotatedAngle));
            int y = (int) (center.getY() + radius * Math.sin(rotatedAngle));
            vertices.add(new Point(x, y));
        }

        setPoints(vertices);
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
        calculateVertices();
    }
}
