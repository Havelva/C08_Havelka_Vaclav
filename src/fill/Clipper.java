package fill;

import objectdata.Edge;
import objectdata.Point;
import objectdata.Polygon;

public class Clipper {
    private Polygon polygon; // Polygon, který bude ořezáván
    private final Polygon originalPolygon; // Původní polygon před ořezáváním
    private final Polygon polygonClipper; // Polygon, který slouží jako ořezávací maska
    private final boolean secondTry; // Indikátor, zda se jedná o druhý pokus o ořezání

    // Konstruktor třídy Clipper
    public Clipper(Polygon polygonClipper, Polygon polygon, Boolean secondTry) {
        this.polygonClipper = polygonClipper; // Nastavení ořezávací masky
        this.polygon = polygon; // Nastavení polygonu, který bude ořezáván
        this.originalPolygon = new Polygon().setPoints(polygon.getPoints()); // Uložení původního polygonu
        this.secondTry = secondTry; // Nastavení indikátoru druhého pokusu
    }

    // Metoda pro získání ořezaného polygonu
    public Polygon getClip() {
        Polygon newPolygon = new Polygon(); // Nový polygon po ořezání

        // Pro každý bod v ořezávací masce
        for (int i = 0; i < polygonClipper.getCount(); i++) {
            if (polygon.getCount() > 0) {
                newPolygon = new Polygon(); // Inicializace nového polygonu

                Point point1 = polygonClipper.getPoint(i); // První bod hrany ořezávací masky
                Point point2 = polygonClipper.getPoint((i + 1) % polygonClipper.getCount()); // Druhý bod hrany ořezávací masky

                Edge edge = new Edge(point1.getX(), point1.getY(), point2.getX(), point2.getY()); // Vytvoření hrany z bodů

                Point v1 = polygon.getPoint(-1); // Poslední bod polygonu

                // Pro každý bod v polygonu
                for (Point v2 : polygon.getPoints()) {
                    if (isInLine(v2, edge)) { // Pokud je bod v polygonu na správné straně hrany
                        if (!isInLine(v1, edge))
                            newPolygon.addPoint(getIntersection(v1, v2, edge)); // Přidání průsečíku hrany a polygonu

                        newPolygon.addPoint(v2); // Přidání bodu do nového polygonu
                    } else {
                        if (isInLine(v1, edge))
                            newPolygon.addPoint(getIntersection(v1, v2, edge)); // Přidání průsečíku hrany a polygonu
                    }
                    v1 = v2; // Posun na další bod
                }
                polygon = newPolygon; // Aktualizace polygonu
            }
        }

        // Pokud nový polygon nemá žádné body a jedná se o druhý pokus
        if (newPolygon.getCount() == 0 && this.secondTry) {
            System.out.println(originalPolygon.getPoints()); // Výpis původních bodů polygonu
            polygonClipper.spinPoints(); // Otočení bodů ořezávací masky
            return new Clipper(polygonClipper, originalPolygon, false).getClip(); // Nový pokus o ořezání
        }

        return newPolygon; // Vrácení nového polygonu
    }

    // Metoda pro získání průsečíku dvou hran
    private Point getIntersection(Point v1, Point v2, Edge edge) {
        double division = (v1.getX() - v2.getX()) * (edge.getY1() - edge.getY2()) - (v1.getY() - v2.getY()) * (edge.getX1() - edge.getX2());
        double x1 = (((v1.getX() * v2.getY() - v2.getX() * v1.getY()) * (edge.getX1() - edge.getX2()) - (edge.getX1() * edge.getY2() - edge.getX2() * edge.getY1()) * (v1.getX() - v2.getX())) / division);
        double y1 = (((v1.getX() * v2.getY() - v2.getX() * v1.getY()) * (edge.getY1() - edge.getY2()) - (edge.getX1() * edge.getY2() - edge.getX2() * edge.getY1()) * (v1.getY() - v2.getY())) / division);

        return new Point(x1, y1); // Vrácení průsečíku jako nový bod
    }

    // Metoda pro kontrolu, zda je bod na správné straně hrany
    private boolean isInLine(Point v2, Edge edge) {
        return v2.getX() * (edge.getY2() - edge.getY1()) - v2.getY() * (edge.getX2() - edge.getX1()) + edge.getX2() * edge.getY1() - edge.getY2() * edge.getX1() > 0;
    }
}
