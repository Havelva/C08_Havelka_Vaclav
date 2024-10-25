package rasterdata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * Třída RasterBI implementuje rozhraní Raster a Presentable.
 * Poskytuje metody pro práci s rastrovými daty pomocí BufferedImage.
 */
public class RasterBI implements Raster, Presentable {

    /**
     * BufferedImage objekt, který uchovává rastrová data.
     */
    private final BufferedImage img;

    /**
     * Konstruktor, který inicializuje BufferedImage s danou šířkou a výškou.
     *
     * @param width  Šířka obrázku.
     * @param height Výška obrázku.
     */
    public RasterBI(int width, int height) {
        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Vrací šířku obrázku.
     *
     * @return Šířka obrázku.
     */
    @Override
    public int getWidth() {
        return img.getWidth();
    }

    /**
     * Vrací výšku obrázku.
     *
     * @return Výška obrázku.
     */
    @Override
    public int getHeight() {
        return img.getHeight();
    }

    /**
     * Nastaví barvu pixelu na dané pozici.
     *
     * @param c     Sloupec (x-ová souřadnice).
     * @param r     Řádek (y-ová souřadnice).
     * @param color Barva pixelu.
     * @return true, pokud byla barva úspěšně nastavena, jinak false.
     */
    @Override
    public boolean setColor(int c, int r, int color) {
        // Řešení boundaries
        if (c > this.getWidth() - 1 || c < 0 || r > this.getHeight() - 1 || r < 0) {
            return false;
        } else {
            img.setRGB(c, r, color);
            return true;
        }
    }

    /**
     * Vrací barvu pixelu na dané pozici.
     *
     * @param c Sloupec (x-ová souřadnice).
     * @param r Řádek (y-ová souřadnice).
     * @return Optional obsahující barvu pixelu, pokud je pozice platná, jinak prázdný Optional.
     */
    @Override
    public Optional<Integer> getColor(int c, int r) {
        // Řešení boundaries
        if (c > this.getWidth() - 1 || c < 0 || r > this.getHeight() - 1 || r < 0) {
            return Optional.empty();
        } else {
            return Optional.of(img.getRGB(r, c));
        }
    }

    /**
     * Vymaže obrázek a nastaví pozadí na danou barvu.
     *
     * @param background Barva pozadí.
     */
    @Override
    public void clear(int background) {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(background));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    /**
     * Prezentuje obrázek na daném grafickém kontextu.
     *
     * @param graphics Grafický kontext, na který se obrázek vykreslí.
     */
    @Override
    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }
}
