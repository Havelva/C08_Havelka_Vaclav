package objectdata;

import java.awt.*;
import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Point> points; // Seznam bodů tvořících polygon
    private int movePointIndex = -1; // Index bodu, který se má přesunout
    private Color color = Color.WHITE; // Barva polygonu

    public Polygon() {
        this.points = new ArrayList<>(); // Inicializace seznamu bodů
    }

    public Point getPoint(int index) {
        if (index < 0)
            index = points.size() + index; // Pokud je index záporný, vrátí bod od konce seznamu

        return points.get(index); // Vrátí bod na daném indexu
    }

    public ArrayList<Point> getPoints() {
        return points; // Vrátí seznam všech bodů
    }

    public Color getColor() {
        return color; // Vrátí barvu polygonu
    }

    public void setColor(Color color) {
        this.color = color; // Nastaví barvu polygonu
    }

    public Point getNextPoint(int index) {
        return getCount() > 1 ? getCount() == index + 1 ? points.get(0) : points.get(index + 1) : null; // Vrátí následující bod v polygonu
    }

    public Point getPreviousPoint(int index) {
        return getCount() > 1 ? index == 0 ? points.get(getCount() - 1) : getPoint(index - 1) : getPoint(0); // Vrátí předchozí bod v polygonu
    }

    public int getCount() {
        return points.size(); // Vrátí počet bodů v polygonu
    }

    public int getMovePointIndex() {
        return movePointIndex; // Vrátí index bodu, který se má přesunout
    }

    public void setMovePointIndex(int movePointIndex) {
        this.movePointIndex = movePointIndex; // Nastaví index bodu, který se má přesunout
    }

    public void addPoint(Point point) {
        points.add(point); // Přidá bod do polygonu
    }

    public void addPoint(Point point, int index) {
        points.add(index, point); // Přidá bod na specifikovaný index
    }

    public void replacePoint(Point point, int index) {
        points.remove(index); // Odstraní bod na daném indexu
        points.add(index, point); // Přidá nový bod na daný index
    }

    public void removeLastPoint() {
        points.remove(getCount() - 1); // Odstraní poslední bod v polygonu
    }

    public void clear() {
        points.clear(); // Vymaže všechny body z polygonu
    }

    public void removeClosestPoint(int mouseX, int mouseY) {
        if (getCount() == 0)
            return;

        Point closestPoint = getClosestPoint(mouseX, mouseY); // Najde nejbližší bod k zadaným souřadnicím

        Line line = new Line(closestPoint.getX(), closestPoint.getY(), mouseX, mouseY); // Vytvoří čáru mezi nejbližším bodem a zadanými souřadnicemi

        if (line.getLineLength() < 20)
            points.remove(closestPoint); // Pokud je vzdálenost menší než 20, odstraní nejbližší bod
    }

    public Point getClosestPoint(int x, int y) {
        double distance = points.get(0).countDistance(x, y); // Vypočítá vzdálenost prvního bodu od zadaných souřadnic
        int pointIndex = 0;

        for (int i = 1; i < getCount(); i++) {
            double testDistance = points.get(i).countDistance(x, y); // Vypočítá vzdálenost dalších bodů

            if (distance > testDistance) {
                pointIndex = i;
                distance = testDistance; // Pokud je vzdálenost menší, aktualizuje nejbližší bod
            }
        }

        return points.get(pointIndex); // Vrátí nejbližší bod
    }

    public int getPointIndex(Point point) {
        for (int i = 0; i <= getCount(); i++) {
            if (getPoint(i).equals(point))
                return i; // Vrátí index bodu, pokud se shoduje
        }

        return -1; // Pokud bod nenajde, vrátí -1
    }

    public void moveClosestPointInPolygon(int mouseX, int mouseY) {
        if (getCount() == 0)
            return;

        Point closestPoint = getClosestPoint(mouseX, mouseY); // Najde nejbližší bod k zadaným souřadnicím

        Line polygonPointToMouse = new Line(closestPoint.getX(), closestPoint.getY(), mouseX, mouseY); // Vytvoří čáru mezi nejbližším bodem a zadanými souřadnicemi

        if (polygonPointToMouse.getLineLength() > 20)
            return;

        int closestPointIndex = getPointIndex(closestPoint); // Najde index nejbližšího bodu
        movePointIndex = closestPointIndex; // Nastaví index bodu, který se má přesunout

        Point point = new Point(mouseX, mouseY);
        replacePoint(point, closestPointIndex); // Nahradí nejbližší bod novým bodem
    }

    public Polygon setPoints(ArrayList<Point> points) {
        clear(); // Vymaže všechny body

        for (Point p : points)
            addPoint(p); // Přidá nové body

        return this;
    }

    public void spinPoints() {
        ArrayList<Point> points = new ArrayList<>();

        for (int i = getCount() - 1; i >= 0; i--)
            points.add(getPoint(i)); // Přidá body v opačném pořadí

        setPoints(points); // Nastaví nové body
    }
}