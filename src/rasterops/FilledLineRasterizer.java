package rasterops;

import java.awt.*;

import rasterdata.Raster;

public class FilledLineRasterizer extends LineRasterizer {
    private Color color; // Barva čáry
    private String type; // Typ čáry (plná, tečkovaná, čárkovaná)
    private int pixelCounter; // Počítadlo pixelů

    public FilledLineRasterizer(Raster raster) {
        super(raster); // Volání konstruktoru nadtřídy
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color, String type) {
        this.color = color; // Nastavení barvy čáry
        this.type = type; // Nastavení typu čáry

        float k = (y2 - y1) / (float) (x2 - x1); // Výpočet směrnice čáry
        float q = y1 - k * x1; // Výpočet posunu na ose y

        // Pokud je x2 <= x1, prohodí se hodnoty x1 a x2
        if (x2 <= x1) {
            int _x1 = x1;
            x1 = x2;
            x2 = _x1;
        }

        // Pokud je y2 <= y1, prohodí se hodnoty y1 a y2
        if (y2 <= y1) {
            int _y1 = y1;
            y1 = y2;
            y2 = _y1;
        }

        // Pokud je vzdálenost mezi x1 a x2 větší než mezi y1 a y2, vykresluje se podle osy X, jinak podle osy Y
        if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
            // Pro všechny hodnoty x mezi x1 a x2 se vypočítají odpovídající hodnoty y
            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                setPixel(x, Math.round(y)); // Nastavení pixelu
            }
        } else {
            // Pro všechny hodnoty y mezi y1 a y2 se vypočítají odpovídající hodnoty x
            for (int y = y1; y <= y2; y++) {
                float x;

                // Pokud je x1 stejné jako x2, nastaví se x na hodnotu x1, jinak se vypočítá
                if (x1 == x2) {
                    x = x1;
                } else {
                    x = (y - q) / k;
                }

                setPixel(Math.round(x), y); // Nastavení pixelu
            }
        }
    }

    private void setPixel(int x, int y) {
        // Nastavení pixelu podle typu čáry
        switch (type) {
            case "solids" -> raster.setPixel(x, y, this.color); // Plná čára
            case "dotted" -> {
                if (pixelCounter % 10 == 0) { // Tečkovaná čára
                    raster.setPixel(x, y, this.color);
                }
            }
            case "dashed" -> {
                if (pixelCounter % 20 > 10) { // Čárkovaná čára
                    raster.setPixel(x, y, this.color);
                }
            }
        }

        pixelCounter++; // Zvýšení počítadla pixelů
    }
}
