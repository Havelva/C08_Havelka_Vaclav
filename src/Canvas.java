import objectdata.Point;
import objectdata.Polygon;
import rasterdata.RasterBI;
import rasterops.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Canvas {

    private JFrame frame;
    private JPanel panel;
    private RasterBI img;
    LinerTrivial linerTrivial = new LinerTrivial();
    LinerDotted linerDotted = new LinerDotted(5);
    LinerDashed linerDashed = new LinerDashed(10,10);
    LinerAligned linerAligned = new LinerAligned();
    Polygon polygon = new Polygon();
    Polygoner polygoner = new Polygoner(linerTrivial, linerDotted);
    double x1;
    double y1;
    double x2;
    double y2;

    boolean lineMode = true;
    boolean polygonMode = false;
    boolean dashedLineMode = false;
    boolean alignMode = false;
    boolean thickLineMode = false; // Nový režim pro tlusté čáry

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("PGRF1 : Task1");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new RasterBI(width, height);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.requestFocusInWindow();
        /*
            OVLÁDÁNÍ:
            Polygon: P
            Úsečka: L
            Čárkovaná úsečka: D
            Zarovnaná úsečka: SHIFT
            Tlustá úsečka: T
            Mazání: C
            */
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_C) {
                    polygon.clearPoints();
                    clear();
                }
                if(e.getKeyCode() == KeyEvent.VK_L) {
                    lineMode = true;
                    polygonMode = false;
                    alignMode = false;
                    thickLineMode = false;
                    clear();
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_P) {
                    polygon.clearPoints();
                    lineMode = false;
                    polygonMode = true;
                    alignMode = false;
                    thickLineMode = false;
                    clear();
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_D) {
                    dashedLineMode = !dashedLineMode;
                }
                if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    clear();
                    alignMode = !alignMode;
                    polygonMode = false;
                    lineMode = false;
                    thickLineMode = false;
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_T) { // Nový posluchač kláves pro režim tlusté čáry
                    thickLineMode = true;
                    lineMode = false;
                    polygonMode = false;
                    alignMode = false;
                    clear();
                    panel.repaint();
                }
            }
        });

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                // Zadání prvního bodu u úseček
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    if (lineMode || thickLineMode) {
                        x1 = mouseEvent.getX();
                        y1 = mouseEvent.getY();
                    }
                    if (alignMode) {
                        x1 = mouseEvent.getX();
                        y1 = mouseEvent.getY();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                // Potvrzení posledního bodu
                if (lineMode){
                    if (dashedLineMode){
                        clear();
                        x2 = mouseEvent.getX();
                        y2 = mouseEvent.getY();
                        linerDashed.drawLine(img, x1, y1, x2, y2, 0xff00ff);
                        panel.repaint();
                    }else {
                        clear();
                        x2 = mouseEvent.getX();
                        y2 = mouseEvent.getY();
                        linerTrivial.drawLine(img, x1, y1, x2, y2, 0xff00ff);
                        panel.repaint();
                    }
                }

                if (polygonMode){
                    if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        clear();
                        polygon.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
                        if (polygon.getCount() > 1) {
                            polygoner.rasterizePolygon(img, polygon, 0xffffff, mouseEvent);
                            panel.repaint();
                        }
                    }
                    if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        clear();
                        polygoner.updatePolygon(polygon, mouseEvent);
                        polygoner.rasterizePolygon(img, polygon, 0xffffff, mouseEvent);
                        panel.repaint();
                    }
                }
                if (alignMode){
                    x2 = mouseEvent.getX();
                    y2 = mouseEvent.getY();
                    linerAligned.drawLine(img, x1, y1, x2, y2, 0xff00ff);
                    panel.repaint();
                }
                if (thickLineMode) { // Nová podmínka pro režim tlusté čáry
                    clear();
                    x2 = mouseEvent.getX();
                    y2 = mouseEvent.getY();
                    drawThickLine(x1, y1, x2, y2, 0xff00ff, 5); // Příklad tloušťky 5
                    panel.repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                // Tažení při zadávání posledního bodu
                    if (lineMode) {
                        clear();
                        linerDotted.drawLine(img, x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
                        panel.repaint();
                    }
                    if (polygonMode && polygon.getCount() > 0) {
                        clear();
                        polygoner.rasterizePolygonDotted(img, polygon, 0xffffff, mouseEvent);
                        panel.repaint();
                    }
                    if (alignMode){
                        clear();
                        linerAligned.drawLine(img, x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
                        panel.repaint();
                    }
                    if (thickLineMode) { // Nová podmínka pro režim tlusté čáry
                        clear();
                        drawThickLine(x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff, 5); // Příklad tloušťky 5
                        panel.repaint();
                    }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void draw() {
        img.clear(0x2f2f2f);
    }

    public void start() {
        draw();
        panel.repaint();
    }

    public void clear(){
        img.clear(0x2f2f2f);
        panel.repaint();
    }

    // Nová metoda pro kreslení tlustých čar
    public void drawThickLine(double x1, double y1, double x2, double y2, int color, int thickness) {
        // Iterace přes tloušťku a kreslení více čar posunutých o hodnotu tloušťky
        for (int i = -thickness/2; i <= thickness/2; i++) {
            linerTrivial.drawLine(img, x1 + i, y1, x2 + i, y2, color);
            linerTrivial.drawLine(img, x1, y1 + i, x2, y2 + i, color);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}