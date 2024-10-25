package rasterops;

import objectdata.Line;
import objectdata.Point;
import rasterdata.Raster;

/**
 * Rozhraní Liner poskytuje metody pro kreslení čar na rastrovém obrázku.
 */
public interface Liner {

    /**
     * Kreslí čáru na rastrovém obrázku mezi dvěma body se zadanou barvou.
     *
     * @param img   Rastrový obrázek, na který se čára kreslí.
     * @param x1    X souřadnice počátečního bodu čáry.
     * @param y1    Y souřadnice počátečního bodu čáry.
     * @param x2    X souřadnice koncového bodu čáry.
     * @param y2    Y souřadnice koncového bodu čáry.
     * @param color Barva čáry.
     */
    void drawLine(Raster img, double x1, double y1, double x2, double y2, int color);

    /**
     * Kreslí čáru na rastrovém obrázku mezi dvěma body se zadanou barvou.
     *
     * @param img   Rastrový obrázek, na který se čára kreslí.
     * @param p1    Počáteční bod čáry.
     * @param p2    Koncový bod čáry.
     * @param color Barva čáry.
     */
    default void drawLine(Raster img, Point p1, Point p2, int color){
    }

    /**
     * Kreslí čáru na rastrovém obrázku podle zadaného objektu Line se zadanou barvou.
     *
     * @param img   Rastrový obrázek, na který se čára kreslí.
     * @param line  Objekt Line, který definuje čáru.
     * @param color Barva čáry.
     */
    default void drawLine(Raster img, Line line, int color){
    }
}
