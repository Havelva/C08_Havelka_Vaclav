package rasterdata;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Control extends JPanel {
    private RasterBI raster;

    private String mode = "polygon";

    private static final int FPS = 30;

    Control(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        raster = new RasterBI(width, height);

        raster.setClearColor(Color.BLACK);
        setLoop();
    }

    public RasterBI getRaster() {
        return raster;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public boolean isNotMode(String mode) {
        return !this.mode.equals(mode);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.repaint(g);
    }

    public void resize() {
        if (this.getWidth() < 1 || this.getHeight() < 1)
            return;
        if (this.getWidth() <= raster.getWidth() && this.getHeight() <= raster.getHeight())
            return;
        RasterBI newRaster = new RasterBI(this.getWidth(), this.getHeight());
        newRaster.draw(raster);
        raster = newRaster;
    }

    private void setLoop() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, FPS);
    }

    public void clear() {
        raster.clear();
        printLegend();
    }

    public void printLegend() {
        Graphics gr = raster.getGraphics();

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
