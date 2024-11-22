package rasterdata;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Control extends JPanel {
    private RasterBI raster; // Objekt pro práci s rastrovými daty

    private String mode = "polygon"; // Výchozí režim kreslení

    private static final int FPS = 30; // Počet snímků za sekundu

    Control(int width, int height) {
        setPreferredSize(new Dimension(width, height)); // Nastavení preferované velikosti panelu
        raster = new RasterBI(width, height); // Inicializace rastrového objektu

        raster.setClearColor(Color.BLACK); // Nastavení výchozí barvy pozadí
        setLoop(); // Spuštění smyčky pro překreslování
    }

    public RasterBI getRaster() {
        return raster; // Vrací aktuální raster
    }

    public void setMode(String mode) {
        this.mode = mode; // Nastavení režimu kreslení
    }

    public String getMode() {
        return mode; // Vrací aktuální režim kreslení
    }

    public boolean isNotMode(String mode) {
        return !this.mode.equals(mode); // Kontroluje, zda aktuální režim není shodný s předaným
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Volání metody předka pro vykreslení komponenty
        raster.repaint(g); // Překreslení rastrového objektu
    }

    public void resize() {
        if (this.getWidth() < 1 || this.getHeight() < 1)
            return; // Pokud je šířka nebo výška menší než 1, nedělat nic
        if (this.getWidth() <= raster.getWidth() && this.getHeight() <= raster.getHeight())
            return; // Pokud je nová velikost menší nebo rovna aktuální, nedělat nic
        RasterBI newRaster = new RasterBI(this.getWidth(), this.getHeight()); // Vytvoření nového rastrového objektu s novou velikostí
        newRaster.draw(raster); // Překreslení starého rastrového objektu do nového
        raster = newRaster; // Nahrazení starého rastrového objektu novým
    }

    private void setLoop() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint(); // Pravidelné překreslování panelu
            }
        }, 0, FPS); // Nastavení intervalu překreslování
    }

    public void clear() {
        raster.clear(); // Vymazání rastrového objektu
        printLegend(); // Vykreslení legendy
    }

    public void printLegend() {
        Graphics gr = raster.getGraphics(); // Získání grafického kontextu rastrového objektu

        // Vykreslení legendy s popisem ovládání
        gr.drawString("Kreslení bežného polygonu (LMB)", 5, 15);
        gr.drawString("Kreslení ořezového polygonu (CTRL + LMB)", 5, 30);
        gr.drawString("SeedFill algoritmus - barva pozadí (RBM)", 5, 45);
        gr.drawString("SeedFill algoritmus - barva hranice (CTRL + RBM)", 5, 60);
        gr.drawString("Kreslení pravidelného pětiúhelníku (SHIFT + LMB)", 5, 75);
        gr.drawString("Kreslení samotné úsečky (SHIFT + RMB)", 5, 90);
        gr.drawString("Změnit normální polygon na ořezový (Z)", 5, 105);
        gr.drawString("ScanLine algoritmus (S)", 5, 120);
        gr.drawString("Ořezání polygonu (O)", 5, 135);
        gr.drawString("Vymazat plochu (C)", 5, 150);
    }
}
