package rasterops;
import objectdata.Edge;
import objectdata.Line;
import rasterdata.Raster;

import java.awt.*;

// Abstraktní třída pro rasterizaci čar
public abstract class LineRasterizer {
    // Odkaz na objekt Raster, který bude použit pro vykreslování
    protected Raster raster;

    // Konstruktor, který inicializuje raster
    public LineRasterizer(Raster raster) {
        this.raster = raster;
    }

    // Metoda pro rasterizaci čáry z objektu Line
    public void rasterize(Line line) {
        // Volá metodu drawLine s parametry z objektu Line
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2(), line.getColor(), line.getType());
    }

    // Metoda pro rasterizaci hrany z objektu Edge
    public void rasterize(Edge edge) {
        // Volá metodu drawLine s parametry z objektu Edge a pevně nastavenými barvou a typem
        drawLine(edge.getX1(), edge.getY1(), edge.getX2(), edge.getY2(), Color.BLUE, "solids");
    }

    // Abstraktní metoda pro vykreslení čáry, která musí být implementována v podtřídách
    public void drawLine(int x1, int y1, int x2, int y2, Color color, String type) {
    }
}
