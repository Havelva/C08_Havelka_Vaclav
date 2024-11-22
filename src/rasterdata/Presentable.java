package rasterdata;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Presentable extends JFrame {
    private final Control panel; // Deklarace proměnné panel typu Control

    public Presentable(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Nastavení akce při zavření okna
        setTitle("PGRF1 Task2"); // Nastavení titulku okna

        panel = new Control(width, height); // Inicializace panelu s danou šířkou a výškou

        add(panel, BorderLayout.CENTER); // Přidání panelu do středu okna
        pack(); // Nastavení velikosti okna podle obsahu

        setLocationRelativeTo(null); // Nastavení okna na střed obrazovky

        panel.setFocusable(true); // Nastavení panelu jako fokusovatelného
        panel.grabFocus(); // Získání fokusu pro panel

        panel.addComponentListener(new ComponentAdapter() { // Přidání posluchače změny velikosti komponenty
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize(); // Volání metody resize() při změně velikosti panelu
            }
        });
    }

    public Control getPanel() {
        return panel; // Metoda vrací panel
    }
}
