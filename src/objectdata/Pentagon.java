package objectdata;

import java.awt.*;
import java.util.ArrayList;

public class Pentagon extends Polygon {
    private Point center; // Střed pětiúhelníku
    private int radius; // Poloměr pětiúhelníku
    private double rotationAngle; // Úhel rotace pětiúhelníku

    // Konstruktor třídy Pentagon
    public Pentagon(Point center, int radius, double rotationAngle) {
        super();
        this.center = center;
        this.radius = radius;
        this.rotationAngle = rotationAngle;
        setColor(Color.GREEN); // Nastavení barvy pětiúhelníku na zelenou
        calculateVertices(); // Výpočet vrcholů pětiúhelníku
    }

    // Metoda pro výpočet vrcholů pětiúhelníku
    private void calculateVertices() {
        ArrayList<Point> vertices = new ArrayList<>();
        double angle = 2 * Math.PI / 5; // Úhel mezi vrcholy pětiúhelníku

        for (int i = 0; i < 5; i++) {
            double rotatedAngle = i * angle + rotationAngle; // Výpočet úhlu s rotací
            int x = (int) (center.getX() + radius * Math.cos(rotatedAngle)); // Výpočet x souřadnice vrcholu
            int y = (int) (center.getY() + radius * Math.sin(rotatedAngle)); // Výpočet y souřadnice vrcholu
            vertices.add(new Point(x, y)); // Přidání vrcholu do seznamu
        }

        setPoints(vertices); // Nastavení vrcholů pětiúhelníku
    }

    // Metoda pro nastavení úhlu rotace pětiúhelníku
    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
        calculateVertices(); // Přepočítání vrcholů po změně úhlu rotace
    }
}
