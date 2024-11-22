package fill;

import objectdata.Edge;
import objectdata.Line;
import objectdata.Point;
import objectdata.Polygon;
import rasterops.LineRasterizer;
import rasterops.PolygonRasterizer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ScanLineFiller implements Filler {
    private final LineRasterizer lineRasterizer; // Rasterizér pro vykreslování čar
    private final PolygonRasterizer polygonRasterizer; // Rasterizér pro vykreslování polygonů
    private final Polygon polygon; // Polygon, který se má vyplnit
    private final ArrayList<Point> points; // Body definující polygon

    private static final int CHECKER_SIZE = 20; // Velikost jednoho čtverce v šachovnicovém vzoru

    public ScanLineFiller(Polygon polygon, LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
        this.points = polygon.getPoints(); // Načtení bodů polygonu
    }

    @Override
    public void fill() {
        scanLine(); // Spuštění algoritmu scan-line pro vyplnění polygonu
    }

    private void scanLine() {
        if (points.size() < 1)
            return; // Pokud polygon nemá žádné body, ukončíme metodu

        List<Edge> edges = new ArrayList<>(); // Seznam hran polygonu

        int yMin = points.get(0).getY(); // Počáteční minimální hodnota y
        int yMax = yMin; // Počáteční maximální hodnota y

        for (int i = 0; i < points.size(); i++) {
            Point point1 = points.get(i);
            Point point2 = points.get((i + 1) % points.size());

            Edge edge = new Edge(point1.getX(), point1.getY(), point2.getX(), point2.getY());

            if (edge.isHorizontal())
                continue; // Přeskočíme horizontální hrany

            edge.orientate(); // Orientace hrany
            edge.calculate(); // Výpočet parametrů hrany
            edges.add(edge); // Přidání hrany do seznamu

            // Najdeme minimální a maximální hodnotu y
            if (edge.getY1() < yMin)
                yMin = edge.getY1();

            if (edge.getY2() > yMax)
                yMax = edge.getY2();
        }

        for (int y = yMin; y < yMax; y++) {
            List<Integer> intersections = new ArrayList<>(); // Seznam průsečíků s aktuální scan-line

            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    intersections.add(edge.getIntersection(y)); // Přidání průsečíku do seznamu
                }
            }

            intersections.sort(Integer::compareTo); // Seřazení průsečíků podle x

            for (int i = 0; i < intersections.size(); i += 2) {
                int x1 = intersections.get(i);
                int x2 = intersections.get(i + 1);
                for (int x = x1; x < x2; x++) {
                    Color color = isCheckerboard(x, y) ? Color.BLACK : Color.WHITE; // Určení barvy podle šachovnicového vzoru
                    Line line = new Line(x, y, x + 1, y); // Vytvoření čáry
                    line.setColor(color); // Nastavení barvy čáry
                    lineRasterizer.rasterize(line); // Vykreslení čáry
                }
            }
        }
        polygonRasterizer.rasterize(polygon); // Vykreslení polygonu
    }

    private boolean isCheckerboard(int x, int y) {
        return ((x / CHECKER_SIZE) % 2 == (y / CHECKER_SIZE) % 2); // Určení, zda je daný bod v černém nebo bílém čtverci šachovnicového vzoru
    }
}
