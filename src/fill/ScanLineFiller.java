package fill;

import objectdata.Edge;
import objectdata.Line;
import objectdata.Point;
import objectdata.Polygon;
import rasterops.LineRasterizer;
import rasterops.PolygonRasterizer;

import java.util.ArrayList;
import java.util.List;

public class ScanLineFiller implements Filler {
    private final LineRasterizer lineRasterizer;
    private final PolygonRasterizer polygonRasterizer;
    private final Polygon polygon;
    private final ArrayList<Point> points;

    private static final int CHECKER_SIZE = 20;

    public ScanLineFiller(Polygon polygon, LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
        this.points = polygon.getPoints();
    }

    @Override
    public void fill() {
        scanLine();
    }

    private void scanLine() {
        if (points.size() < 1)
            return;

        List<Edge> edges = new ArrayList<>();

        int yMin = points.get(0).getY();
        int yMax = yMin;

        for (int i = 0; i < points.size(); i++) {
            Point point1 = points.get(i);
            Point point2 = points.get((i + 1) % points.size());

            Edge edge = new Edge(point1.getX(), point1.getY(), point2.getX(), point2.getY());

            if (edge.isHorizontal())
                continue;

            edge.orientate();
            edge.calculate();
            edges.add(edge);

            //find yMin and yMax
            if (edge.getY1() < yMin)
                yMin = edge.getY1();

            if (edge.getY2() > yMax)
                yMax = edge.getY2();
        }

        for (int y = yMin; y < yMax; y++) {
            List<Integer> intersections = new ArrayList<>();

            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    intersections.add(edge.getIntersection(y));
                }
            }

            intersections.sort(Integer::compareTo);

            for (int i = 0; i < intersections.size(); i += 2) {
                int x1 = intersections.get(i);
                int x2 = intersections.get(i + 1);
                for (int x = x1; x < x2; x++) {
                    if (isCheckerboard(x, y)) {
                        lineRasterizer.rasterize(new Line(x, y, x + 1, y));
                    }
                }
            }
        }
        polygonRasterizer.rasterize(polygon);
    }

    private boolean isCheckerboard(int x, int y) {
        return ((x / CHECKER_SIZE) % 2 == (y / CHECKER_SIZE) % 2);
    }
}
