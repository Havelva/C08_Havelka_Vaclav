package rasterdata;

import java.awt.*;

// Rozhraní Raster, které definuje metody pro práci s rastrovými daty
public interface Raster {
    // Nastaví barvu pixelu na daných souřadnicích (x, y)
    void setPixel(int x, int y, Color color);
    
    // Vrátí barvu pixelu na daných souřadnicích (x, y)
    int getPixel(int x, int y);
    
    // Vymaže obsah rastru
    void clear();
    
    // Nastaví barvu, kterou se bude rastr vymazávat
    void setClearColor(Color color);
    
    // Vrátí šířku rastru
    int getWidth();
    
    // Vrátí výšku rastru
    int getHeight();
}
