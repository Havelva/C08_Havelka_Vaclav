package rasterdata;

import java.awt.*;
import java.awt.image.BufferedImage;

// Třída RasterBI implementuje rozhraní Raster a pracuje s BufferedImage
public class RasterBI implements Raster {
    private final BufferedImage img; // BufferedImage pro uložení rastrových dat
    private Color color; // Barva pro vyčištění rastru

    // Konstruktor inicializuje BufferedImage s danou šířkou a výškou
    public RasterBI(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    // Nastaví pixel na dané souřadnici na zadanou barvu, pokud je souřadnice v rámci rastru
    @Override
    public void setPixel(int x, int y, Color color) {
        if (isOnRaster(x, y))
            img.setRGB(x, y, color.getRGB());
    }

    // Vrátí barvu pixelu na dané souřadnici
    @Override
    public int getPixel(int x, int y) {
        return img.getRGB(x, y);
    }

    // Vykreslí obraz na danou grafiku
    public void present(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    // Vyčistí celý raster černou barvou
    @Override
    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, getWidth(), getHeight());
    }

    // Vyčistí celý raster zadanou barvou
    public void clear(Color color) {
        Graphics gr = img.getGraphics();
        gr.setColor(color);
        gr.fillRect(0, 0, getWidth(), getHeight());
    }

    // Vrátí BufferedImage
    public BufferedImage getImg() {
        return img;
    }

    // Vrátí grafiku pro BufferedImage
    public Graphics getGraphics() {
        return img.getGraphics();
    }

    // Překreslí obraz na danou grafiku
    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    // Vykreslí obsah jiného rastru na tento raster
    public void draw(RasterBI raster) {
        Graphics graphics = getGraphics();
        graphics.setColor(color);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(raster.img, 0, 0, null);
    }

    // Nastaví barvu pro vyčištění rastru
    @Override
    public void setClearColor(Color color) {
        this.color = color;
    }

    // Vrátí šířku rastru
    @Override
    public int getWidth() {
        return img.getWidth();
    }

    // Vrátí výšku rastru
    @Override
    public int getHeight() {
        return img.getHeight();
    }

    // Zkontroluje, zda jsou souřadnice v rámci rastru
    public boolean isOnRaster(int x, int y) {
        return x > -1 && x < getWidth() && y > -1 && y < getHeight();
    }

    // Vrátí aktuální barvu pozadí
    public Color getBackgroundColor() {
        return this.color;
    }
}
