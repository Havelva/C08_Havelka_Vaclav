import fill.Clipper;
import fill.ScanLineFiller;
import fill.SeedFiller;
import fill.SeedFillerBorder;
import objectdata.*;
import objectdata.Point;
import objectdata.Polygon;
import objectdata.Pentagon;
import rasterdata.*;
import rasterdata.Control;
import rasterdata.Presentable;
import rasterops.FilledLineRasterizer;
import rasterops.LineRasterizer;
import rasterops.PolygonRasterizer;

import java.awt.*;
import java.awt.event.*;

public class Canvas {
    private final Control panel;
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private RasterBI raster;
    private Polygon polygon;
    private final Polygon polygonClipper;
    private int currentMouseButton = -1;
    private Point polygonClosestPoint;
    private int polygonClosestPointIndex;
    private Point startPoint;
    private double rotationAngle = 0;
    private boolean drawingLineMode = false; // Nový příznak režimu

    public Canvas(int width, int height) {
        Presentable window = new Presentable(width, height);
        panel = window.getPanel();
        raster = panel.getRaster();

        polygon = new Polygon();
        polygon.setColor(Color.green);
        polygonClipper = new Polygon();
        polygonClipper.setColor(Color.RED);
        lineRasterizer = new FilledLineRasterizer(raster);
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);
        window.setVisible(true);

        //lineListeners();
        polygonListeners();

        keyEventListeners();
        resizeListener();
    }

    // Metoda pro nastavení posluchačů myši pro práci s polygonem
    private void polygonListeners() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                Polygon p = e.isControlDown() ? polygonClipper : polygon;
                currentMouseButton = e.getButton();

                if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON1) {
                    startPoint = new Point(e.getX(), e.getY());
                } else if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON3) {
                    drawingLineMode = true; // Povolit režim kreslení čáry
                    startPoint = new Point(e.getX(), e.getY());
                } else if (currentMouseButton == MouseEvent.BUTTON3) {
                    // Pohybuje nejbližším bodem v polygonu
                    p.moveClosestPointInPolygon(mouseX, mouseY);
                    rasterizePolygons();

                    // Vyplňuje polygon od kurzoru
                    if (e.isControlDown()) {
                        new SeedFillerBorder(mouseX, mouseY, Color.ORANGE, polygon.getColor(), polygonClipper.getColor(), raster).fill();
                    } else {
                        new SeedFiller(mouseX, mouseY, Color.BLUE, raster).fill();
                    }
                } else if (currentMouseButton == MouseEvent.BUTTON1) {
                    // Ukládá nejbližší bod v polygonu pro jiný listener
                    if (p.getCount() < 1)
                        return;

                    polygonClosestPoint = p.getClosestPoint(mouseX, mouseY);
                    polygonClosestPointIndex = p.getPointIndex(polygonClosestPoint);
                } else if (currentMouseButton == MouseEvent.BUTTON2) {
                    // Odstraňuje nejbližší bod v polygonu
                    p.removeClosestPoint(mouseX, mouseY);
                    rasterizePolygons();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentMouseButton = e.getButton();
                Polygon p = e.isControlDown() ? polygonClipper : polygon;

                if (drawingLineMode && e.getButton() == MouseEvent.BUTTON3) {
                    Point endPoint = new Point(e.getX(), e.getY());
                    Line line = new Line(startPoint, endPoint);
                    line.setColor(Color.CYAN); // Nastavit barvu čáry
                    lineRasterizer.rasterize(line);
                    drawingLineMode = false; // Zakázat režim kreslení čáry
                } else if (e.isShiftDown() && e.getButton() == MouseEvent.BUTTON1) {
                    Point endPoint = new Point(e.getX(), e.getY());
                    int radius = (int) startPoint.countDistance(endPoint.getX(), endPoint.getY());
                    Pentagon pentagon = new Pentagon(startPoint, radius, rotationAngle);
                    polygon = pentagon;
                    rasterizePolygons();
                } else if (currentMouseButton == MouseEvent.BUTTON3) {
                    // Resetuje index bodu, který byl přesunut
                    p.setMovePointIndex(-1);
                } else if (currentMouseButton == MouseEvent.BUTTON1) {
                    // Přidává nový bod do polygonu
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    Point point = new Point(mouseX, mouseY);
                    p.addPoint(point, p.getCount() < 2 ? p.getCount() : polygonClosestPointIndex);
                    rasterizePolygons();
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                Polygon p = e.isControlDown() ? polygonClipper : polygon;

                if (e.isShiftDown() && currentMouseButton == MouseEvent.BUTTON1) {
                    Point endPoint = new Point(e.getX(), e.getY());
                    int radius = (int) startPoint.countDistance(endPoint.getX(), endPoint.getY());
                    rotationAngle = Math.atan2(endPoint.getY() - startPoint.getY(), endPoint.getX() - startPoint.getX());
                    Pentagon pentagon = new Pentagon(startPoint, radius, rotationAngle);
                    polygon = pentagon;
                    rasterizePolygons();
                } else if (drawingLineMode && currentMouseButton == MouseEvent.BUTTON3) {
                    Point endPoint = new Point(e.getX(), e.getY());
                    Line line = new Line(startPoint, endPoint);
                    line.setType("dashed"); // Nastavit typ čáry
                    line.setColor(Color.CYAN); // Nastavit barvu čáry
                    rasterizePolygons();
                    lineRasterizer.rasterize(line);
                } else if (currentMouseButton == MouseEvent.BUTTON3) {
                    // Pohybuje nejbližším bodem v polygonu
                    int polygonMovePointIndex = p.getMovePointIndex();

                    if (polygonMovePointIndex != -1) {
                        Point point = new Point(mouseX, mouseY);
                        p.replacePoint(point, polygonMovePointIndex);
                        rasterizePolygons();
                    }
                } else if (currentMouseButton == MouseEvent.BUTTON1) {
                    // Rasterizuje náhled přerušovaných čar k novému bodu v polygonu
                    if (p.getCount() < 1)
                        return;

                    Line line1 = new Line(polygonClosestPoint.getX(), polygonClosestPoint.getY(), mouseX, mouseY);
                    line1.setType("dashed");
                    line1.setColor(Color.CYAN);

                    rasterizePolygons();
                    lineRasterizer.rasterize(line1);

                    if (p.getCount() != 1) {
                        Point point = p.getPreviousPoint(polygonClosestPointIndex);

                        Line line2 = new Line(point.getX(), point.getY(), mouseX, mouseY);
                        line2.setType("dashed");
                        line2.setColor(Color.CYAN);
                        lineRasterizer.rasterize(line2);
                    }
                }
            }
        });
    }

    // Metoda pro nastavení posluchačů klávesnice
    private void keyEventListeners() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    clearAllStructures();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    new ScanLineFiller(polygon, lineRasterizer, polygonRasterizer).fill();
                } else if (e.getKeyCode() == KeyEvent.VK_Z) {
                    polygonClipper.clear();

                    for (Point point : polygon.getPoints()) {
                        polygonClipper.addPoint(point);
                    }

                    // Vymazat normální polygon pro začátek kreslení nového
                    polygon.clear();
                    rasterizePolygons();
                } else if (e.getKeyCode() == KeyEvent.VK_O) {
                    Polygon p = new Clipper(polygonClipper, polygon, true).getClip();

                    polygon.clear();
                    polygonClipper.clear();

                    for (Point point : p.getPoints())
                        polygon.addPoint(point);

                    rasterizePolygons();
                }
            }
        });
    }

    // Metoda pro nastavení posluchače změny velikosti okna
    private void resizeListener() {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                raster = panel.getRaster();
                lineRasterizer = new FilledLineRasterizer(raster);
                polygonRasterizer = new PolygonRasterizer(lineRasterizer);
            }
        });
    }

    // Metoda pro rasterizaci polygonů
    private void rasterizePolygons() {
        panel.clear();
        if (polygon != null) {
            polygonRasterizer.rasterize(polygon);
        }
        polygonRasterizer.rasterize(polygonClipper);
    }

    // Metoda pro vymazání všech struktur
    private void clearAllStructures() {
        currentMouseButton = -1;
        polygonClosestPoint = null;
        polygon.clear();
        polygonClipper.clear();
        panel.clear();
    }

    // Metoda pro spuštění aplikace
    public void start() {
        raster.clear(Color.BLACK);
        panel.repaint();
        panel.printLegend();
    }

    // Hlavní metoda pro spuštění aplikace
    public static void main(String[] args) {
        new Canvas(800, 600).start();
    }
}
