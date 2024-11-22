package rasterops;

import objectdata.Line;
import objectdata.Point;
import objectdata.Polygon;

public class PolygonRasterizer {
    private final LineRasterizer lineRasterizer;

    // Konstruktor třídy PolygonRasterizer, který přijímá objekt LineRasterizer
    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    // Metoda pro rasterizaci polygonu
    public void rasterize(Polygon polygon) {
        // Prochází všechny body polygonu
        for (int i = 0; i < polygon.getCount(); i++) {
            // Získá aktuální bod a následující bod (s cyklickým propojením)
            Point point1 = polygon.getPoint(i);
            Point point2 = polygon.getPoint((i + 1) % polygon.getCount());

            // Vytvoří čáru mezi dvěma body
            Line line = new Line(point1, point2);
            // Nastaví barvu čáry podle barvy polygonu
            line.setColor(polygon.getColor());

            // Rasterizuje čáru pomocí lineRasterizer
            lineRasterizer.rasterize(line);
        }
    }
}
