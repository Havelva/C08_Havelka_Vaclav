package objectdata;

import java.awt.*;

public class Line {
    // Souřadnice počátečního bodu čáry
    private final int x1, y1;
    // Souřadnice koncového bodu čáry
    private final int x2, y2;
    // Barva čáry, výchozí je bílá
    private Color color = Color.WHITE;
    // Středový bod čáry
    private Point centerPoint;
    // Typ čáry, výchozí je "solids"
    private String type = "solids";

    // Konstruktor, který přijímá souřadnice počátečního a koncového bodu
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        // Výpočet středového bodu čáry
        sumLineCenter();
    }

    // Konstruktor, který přijímá dva body jako počáteční a koncový bod
    public Line(Point point1, Point point2) {
        this.x1 = point1.getX();
        this.y1 = point1.getY();
        this.x2 = point2.getX();
        this.y2 = point2.getY();
        // Výpočet středového bodu čáry
        sumLineCenter();
    }

    // Getter pro souřadnici x počátečního bodu
    public int getX1() {
        return x1;
    }

    // Getter pro souřadnici y počátečního bodu
    public int getY1() {
        return y1;
    }

    // Getter pro souřadnici x koncového bodu
    public int getX2() {
        return x2;
    }

    // Getter pro souřadnici y koncového bodu
    public int getY2() {
        return y2;
    }

    // Getter pro barvu čáry
    public Color getColor() {
        return color;
    }

    // Setter pro barvu čáry
    public void setColor(Color color) {
        this.color = color;
    }

    // Getter pro středový bod čáry
    public Point getCenter() {
        return this.centerPoint;
    }

    // Metoda pro výpočet středového bodu čáry
    private void sumLineCenter() {
        this.centerPoint = new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    // Metoda pro výpočet délky čáry
    public double getLineLength() {
        return Math.sqrt(Math.pow(this.x2 - this.x1, 2) + Math.pow(this.y2 - this.y1, 2));
    }

    // Getter pro typ čáry
    public String getType() {
        return type;
    }

    // Setter pro typ čáry
    public void setType(String type) {
        this.type = type;
    }
}
