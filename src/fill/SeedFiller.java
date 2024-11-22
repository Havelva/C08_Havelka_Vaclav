package fill;

import rasterdata.RasterBI;

import java.awt.*;
import java.util.Stack;

public class SeedFiller implements Filler {
    private final int x;
    private final int y;
    private final Color fillColor, backgroundColor;
    private final RasterBI raster;

    // Konstruktor třídy SeedFiller, inicializuje počáteční souřadnice, barvu výplně a raster
    public SeedFiller(int x, int y, Color fillColor, RasterBI raster) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = raster.getBackgroundColor();
        this.raster = raster;
    }

    // Metoda pro spuštění výplně
    @Override
    public void fill() {
        seedFill(x, y);
    }

    // Metoda pro výplň pomocí seed fill algoritmu
    private void seedFill(int startX, int startY) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int x = p.x;
            int y = p.y;

            // Kontrola, zda je bod v rámci rastru
            if (!raster.isOnRaster(x, y)) {
                continue;
            }

            Color color = new Color(raster.getPixel(x, y));

            // Pokud je barva bodu stejná jako barva pozadí, vyplníme bod a přidáme sousední body do zásobníku
            if (color.equals(backgroundColor)) {
                raster.setPixel(x, y, getPatternColor(x, y));

                stack.push(new Point(x + 1, y));
                stack.push(new Point(x - 1, y));
                stack.push(new Point(x, y + 1));
                stack.push(new Point(x, y - 1));
            }
        }
    }

    // Metoda pro získání barvy vzoru (např. šachovnice)
    private Color getPatternColor(int x, int y) {
        int patternSize = 10;
        boolean isEven = ((x / patternSize) % 2 == (y / patternSize) % 2);
        return isEven ? fillColor : fillColor.darker();
    }
}
