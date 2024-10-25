package rasterops;

import rasterdata.Raster;

/**
 * Třída LinerAligned implementuje rozhraní Liner a poskytuje metodu pro vykreslení úsečky
 * na rastrovém obrázku s určitým zarovnáním.
 */
public class LinerAligned implements Liner {
    /**
     * Vykreslí úsečku na rastrovém obrázku s danou barvou.
     * 
     * @param img   Rastrový obrázek, na který se úsečka vykreslí.
     * @param x1    X souřadnice počátečního bodu úsečky.
     * @param y1    Y souřadnice počátečního bodu úsečky.
     * @param x2    X souřadnice koncového bodu úsečky.
     * @param y2    Y souřadnice koncového bodu úsečky.
     * @param color Barva úsečky.
     */
    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {
        // Výpočet sklonu a posunu úsečky
        double k = (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;
        double temp;

        // Zarovnání sklonu podle jeho hodnoty
        if (k < 1 && k >= 0.5) {
            k = 1;
        } else if (k > 0 && k < 0.5) {
            k = 0;
        } else if (k < 0 && k > -0.5) {
            k = 0; // vodorovná úsečka
        } else if (k > -1 && k <= -0.5) {
            k = -1;
        } else {
            x2 = x1; // svislá úsečka
        }

        // Zajištění, že x1 je menší než x2
        if (x2 < x1) {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }

        // Zajištění, že y1 je menší než y2
        if (y2 < y1) {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Vykreslení svislé úsečky
        if (x2 - x1 == 0) {
            for (double y = y1; y <= y2; y++) {
                double x = x1;
                img.setColor((int) x, (int) y, color);
            }
        }
        // Vykreslení úsečky s abs(k) <= 1
        else if (Math.abs(k) <= 1) {
            for (double x = x1; x <= x2; x++) {
                int y = (int) Math.round(k * x + q);
                img.setColor((int) x, y, color);
            }
        }
        // Vykreslení úsečky s abs(k) > 1
        else if (Math.abs(k) > 1) {
            for (double y = y1; y <= y2; y++) {
                double x = (int) Math.round((y - q) / k);
                img.setColor((int) x, (int) y, color);
            }
        }
    }
}
