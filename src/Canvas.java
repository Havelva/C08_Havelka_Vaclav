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

/**
 * Třída Canvas představuje plátno pro kreslení různých typů čar a polygonů.
 * Obsahuje grafické komponenty a logiku pro zpracování uživatelských vstupů.
 */
public class Canvas {

    private JFrame frame; // Hlavní okno aplikace
    private JPanel panel; // Panel pro vykreslování
    private RasterBI img; // Objekt pro práci s rastrovým obrázkem
    LinerTrivial linerTrivial = new LinerTrivial(); // Objekt pro kreslení jednoduchých čar
    LinerDotted linerDotted = new LinerDotted(5); // Objekt pro kreslení tečkovaných čar
    LinerDashed linerDashed = new LinerDashed(10,10); // Objekt pro kreslení čárkovaných čar
    LinerAligned linerAligned = new LinerAligned(); // Objekt pro kreslení zarovnaných čar
    Polygon polygon = new Polygon(); // Objekt pro práci s polygonem
    Polygoner polygoner = new Polygoner(linerTrivial, linerDotted); // Objekt pro rasterizaci polygonů
    double x1; // X souřadnice prvního bodu čáry
    double y1; // Y souřadnice prvního bodu čáry
    double x2; // X souřadnice druhého bodu čáry
    double y2; // Y souřadnice druhého bodu čáry

    boolean lineMode = true; // Režim kreslení čar
    boolean polygonMode = false; // Režim kreslení polygonů
    boolean dashedLineMode = false; // Režim kreslení čárkovaných čar
    boolean alignMode = false; // Režim kreslení zarovnaných čar
    boolean thickLineMode = false; // Režim kreslení tlustých čar

    /**
     * Konstruktor třídy Canvas.
     * Inicializuje okno, panel a nastavuje posluchače událostí.
     *
     * @param width  Šířka plátna
     * @param height Výška plátna
     */
    public Canvas(int width, int height) {
        frame = new JFrame(); // Vytvoření hlavního okna

        frame.setLayout(new BorderLayout()); // Nastavení rozložení okna
        frame.setTitle("PGRF1 : Task1"); // Nastavení titulku okna
        frame.setResizable(false); // Zakázání změny velikosti okna
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Nastavení akce při zavření okna

        img = new RasterBI(width, height); // Inicializace rastrového obrázku

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g); // Vykreslení obsahu na panel
            }
        };

        panel.setPreferredSize(new Dimension(width, height)); // Nastavení preferované velikosti panelu

        frame.add(panel, BorderLayout.CENTER); // Přidání panelu do okna
        frame.pack(); // Nastavení velikosti okna podle obsahu
        frame.setVisible(true); // Zobrazení okna

        panel.requestFocusInWindow(); // Nastavení zaměření na panel

        // Přidání posluchače klávesových událostí
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_C) {
                    polygon.clearPoints(); // Vymazání bodů polygonu
                    clear(); // Vymazání plátna
                }
                if(e.getKeyCode() == KeyEvent.VK_L) {
                    lineMode = true; // Aktivace režimu čar
                    polygonMode = false;
                    alignMode = false;
                    thickLineMode = false;
                    clear();
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_P) {
                    polygon.clearPoints(); // Vymazání bodů polygonu
                    lineMode = false;
                    polygonMode = true; // Aktivace režimu polygonů
                    alignMode = false;
                    thickLineMode = false;
                    clear();
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_D) {
                    dashedLineMode = !dashedLineMode; // Přepnutí režimu čárkovaných čar
                }
                if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    clear();
                    alignMode = !alignMode; // Přepnutí režimu zarovnaných čar
                    polygonMode = false;
                    lineMode = false;
                    thickLineMode = false;
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_T) { // Nový posluchač kláves pro režim tlusté čáry
                    thickLineMode = true; // Aktivace režimu tlustých čar
                    lineMode = false;
                    polygonMode = false;
                    alignMode = false;
                    clear();
                    panel.repaint();
                }
            }
        });

        // Přidání posluchače událostí myši
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}

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
                        linerDashed.drawLine(img, x1, y1, x2, y2, 0xff00ff); // Kreslení čárkované čáry
                        panel.repaint();
                    }else {
                        clear();
                        x2 = mouseEvent.getX();
                        y2 = mouseEvent.getY();
                        linerTrivial.drawLine(img, x1, y1, x2, y2, 0xff00ff); // Kreslení jednoduché čáry
                        panel.repaint();
                    }
                }

                if (polygonMode){
                    if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        clear();
                        polygon.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY())); // Přidání bodu do polygonu
                        if (polygon.getCount() > 1) {
                            polygoner.rasterizePolygon(img, polygon, 0xffffff, mouseEvent); // Rasterizace polygonu
                            panel.repaint();
                        }
                    }
                    if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        clear();
                        polygoner.updatePolygon(polygon, mouseEvent); // Aktualizace polygonu
                        polygoner.rasterizePolygon(img, polygon, 0xffffff, mouseEvent); // Rasterizace polygonu
                        panel.repaint();
                    }
                }
                if (alignMode){
                    x2 = mouseEvent.getX();
                    y2 = mouseEvent.getY();
                    linerAligned.drawLine(img, x1, y1, x2, y2, 0xff00ff); // Kreslení zarovnané čáry
                    panel.repaint();
                }
                if (thickLineMode) { // Nová podmínka pro režim tlusté čáry
                    clear();
                    x2 = mouseEvent.getX();
                    y2 = mouseEvent.getY();
                    drawThickLine(x1, y1, x2, y2, 0xff00ff, 5); // Kreslení tlusté čáry
                    panel.repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        // Přidání posluchače událostí pohybu myši
        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                // Tažení při zadávání posledního bodu
                if (lineMode) {
                    clear();
                    linerDotted.drawLine(img, x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff); // Kreslení tečkované čáry
                    panel.repaint();
                }
                if (polygonMode && polygon.getCount() > 0) {
                    clear();
                    polygoner.rasterizePolygonDotted(img, polygon, 0xffffff, mouseEvent); // Rasterizace tečkovaného polygonu
                    panel.repaint();
                }
                if (alignMode){
                    clear();
                    linerAligned.drawLine(img, x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff); // Kreslení zarovnané čáry
                    panel.repaint();
                }
                if (thickLineMode) { // Nová podmínka pro režim tlusté čáry
                    clear();
                    drawThickLine(x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff, 5); // Kreslení tlusté čáry
                    panel.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

    }

    /**
     * Metoda pro vykreslení obsahu na panel.
     *
     * @param graphics Grafický kontext
     */
    public void present(Graphics graphics) {
        img.present(graphics);
    }

    /**
     * Metoda pro vymazání plátna a nastavení výchozí barvy.
     */
    public void draw() {
        img.clear(0x2f2f2f);
    }

    /**
     * Metoda pro spuštění kreslení na plátně.
     */
    public void start() {
        draw();
        panel.repaint();
    }

    /**
     * Metoda pro vymazání plátna.
     */
    public void clear(){
        img.clear(0x2f2f2f);
        panel.repaint();
    }

    /**
     * Metoda pro kreslení tlustých čar.
     *
     * @param x1        X souřadnice prvního bodu
     * @param y1        Y souřadnice prvního bodu
     * @param x2        X souřadnice druhého bodu
     * @param y2        Y souřadnice druhého bodu
     * @param color     Barva čáry
     * @param thickness Tloušťka čáry
     */
    public void drawThickLine(double x1, double y1, double x2, double y2, int color, int thickness) {
        // Iterace přes tloušťku a kreslení více čar posunutých o hodnotu tloušťky
        for (int i = -thickness/2; i <= thickness/2; i++) {
            linerTrivial.drawLine(img, x1 + i, y1, x2 + i, y2, color);
            linerTrivial.drawLine(img, x1, y1 + i, x2, y2 + i, color);
        }
    }

    /**
     * Hlavní metoda pro spuštění aplikace.
     *
     * @param args Argumenty příkazového řádku
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}