package rasterops;

import rasterdata.Raster;

/**
 * Třída LinerDotted implementuje rozhraní Liner a slouží k vykreslování tečkovaných čar.
 */
public class LinerDotted implements Liner {
    private int spaceLength;

    /**
     * Konstruktor třídy LinerDotted.
     * 
     * @param spaceLength Délka mezery mezi tečkami.
     */
    public LinerDotted(int spaceLength) {
        this.spaceLength = spaceLength;
    }

    /**
     * Nastaví délku mezery mezi tečkami.
     * 
     * @param spaceLength Délka mezery mezi tečkami.
     */
    public void setSpaceLength(int spaceLength) {
        this.spaceLength = spaceLength;
    }

    /**
     * Vykreslí tečkovanou čáru na zadaný raster.
     * 
     * @param img   Raster, na který se čára vykreslí.
     * @param x1    Počáteční x-ová souřadnice.
     * @param y1    Počáteční y-ová souřadnice.
     * @param x2    Koncová x-ová souřadnice.
     * @param y2    Koncová y-ová souřadnice.
     * @param color Barva čáry.
     */
    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {
        int space = 0;
        int length = 0;

        // Výpočet směrnice přímky a posunutí na ose y
        double k = (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;
        double temp;

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

        // Vykreslení svislé čáry
        if (x2 - x1 == 0) {
            for (double y = y1; y <= y2; y++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= 1) {
                    double x = x1;
                    img.setColor((int) x, (int) y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
        // Vykreslení čáry s menším nebo rovným sklonem než 1
        else if (Math.abs(k) <= 1) {
            for (double x = x1; x <= x2; x++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= 1) {
                    int y = (int) Math.round(k * x + q);
                    img.setColor((int) x, y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
        // Vykreslení čáry s větším sklonem než 1
        else if (Math.abs(k) > 1) {
            for (double y = y1; y <= y2; y++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= 1) {
                    double x = (int) Math.round((y - q) / k);
                    img.setColor((int) x, (int) y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
    }
}
