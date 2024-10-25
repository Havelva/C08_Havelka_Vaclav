package rasterops;

import rasterdata.Raster;

/**
 * Třída LinerDashed implementuje rozhraní Liner a slouží k vykreslování čar s přerušovaným (čárkovaným) vzorem.
 */
public class LinerDashed implements Liner {

    private int dashLength; // Délka čárky
    private int spaceLength; // Délka mezery

    /**
     * Konstruktor třídy LinerDashed.
     * 
     * @param dashLength Délka čárky
     * @param spaceLength Délka mezery
     */
    public LinerDashed(int dashLength, int spaceLength) {
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
    }

    /**
     * Nastaví délku čárky.
     * 
     * @param dashLength Nová délka čárky
     */
    public void setDashLength(int dashLength) {
        this.dashLength = dashLength;
    }

    /**
     * Nastaví délku mezery.
     * 
     * @param spaceLength Nová délka mezery
     */
    public void setSpaceLength(int spaceLength) {
        this.spaceLength = spaceLength;
    }

    /**
     * Vykreslí čárkovanou čáru na daný raster.
     * 
     * @param img Raster, na který se bude kreslit
     * @param x1  Počáteční x-ová souřadnice
     * @param y1  Počáteční y-ová souřadnice
     * @param x2  Koncová x-ová souřadnice
     * @param y2  Koncová y-ová souřadnice
     * @param color Barva čáry
     */
    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {
        int space = 0; // Počítadlo mezery
        int length = 0; // Počítadlo délky čárky

        double k = (y2 - y1) / (x2 - x1); // Sklon čáry
        double q = y1 - k * x1; // Posun čáry
        double temp;

        // Pokud je x2 menší než x1, prohodíme hodnoty
        if(x2 < x1) {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }

        // Pokud je y2 menší než y1, prohodíme hodnoty
        if(y2 < y1) {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Pokud je čára vertikální
        if(x2 - x1 == 0) {
            for (double y = y1; y <= y2; y++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= dashLength) {
                    double x = x1;
                    img.setColor((int) x, (int) y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
        // Pokud je sklon čáry menší nebo roven 1
        else if(Math.abs(k) <= 1) {
            for (double x = x1; x <= x2; x++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= dashLength) {
                    int y = (int) Math.round(k * x + q);
                    img.setColor((int) x, y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
        // Pokud je sklon čáry větší než 1
        else if(Math.abs(k) > 1) {
            for (double y = y1; y <= y2; y++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= dashLength) {
                    double x = (int) Math.round((y - q)/k);
                    img.setColor((int) x, (int) y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
    }
}
