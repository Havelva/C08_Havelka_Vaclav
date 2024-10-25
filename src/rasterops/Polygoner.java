package rasterops;

import objectdata.Point;
import objectdata.Polygon;
import rasterdata.Raster;

import java.awt.event.MouseEvent;

/**
 * Třída Polygoner slouží k rasterizaci polygonů pomocí dvou různých metod:
 * - Triviální metoda (linerTrivial)
 * - Tečkovaná metoda (linerDotted)
 */
public class Polygoner {
    private LinerTrivial linerTrivial; // Instance třídy LinerTrivial pro triviální rasterizaci
    private LinerDotted linerDotted;   // Instance třídy LinerDotted pro tečkovanou rasterizaci

    /**
     * Konstruktor třídy Polygoner.
     *
     * @param linerTrivial Instance třídy LinerTrivial
     * @param linerDotted Instance třídy LinerDotted
     */
    public Polygoner(LinerTrivial linerTrivial, LinerDotted linerDotted) {
        this.linerTrivial = linerTrivial;
        this.linerDotted = linerDotted;
    }

    /**
     * Metoda pro rasterizaci polygonu pomocí triviální metody.
     *
     * @param img Instance třídy Raster, která reprezentuje obraz
     * @param polygon Instance třídy Polygon, která reprezentuje polygon
     * @param color Barva, kterou bude polygon rasterizován
     * @param mouseEvent Instance třídy MouseEvent, která reprezentuje událost myši
     */
    public void rasterizePolygon(Raster img, Polygon polygon, int color, MouseEvent mouseEvent){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            // Kreslení hrany mezi dvěma sousedními body polygonu
            linerTrivial.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            // Kreslení hrany mezi předposledním bodem a aktuální pozicí myši
            linerTrivial.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
        }
        // Kreslení hrany mezi prvním bodem a aktuální pozicí myši
        linerTrivial.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
    }

    /**
     * Metoda pro rasterizaci polygonu pomocí tečkované metody.
     *
     * @param img Instance třídy Raster, která reprezentuje obraz
     * @param polygon Instance třídy Polygon, která reprezentuje polygon
     * @param color Barva, kterou bude polygon rasterizován
     * @param mouseEvent Instance třídy MouseEvent, která reprezentuje událost myši
     */
    public void rasterizePolygonDotted(Raster img, Polygon polygon, int color, MouseEvent mouseEvent){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            // Kreslení tečkované hrany mezi dvěma sousedními body polygonu
            linerDotted.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            // Kreslení tečkované hrany mezi předposledním bodem a aktuální pozicí myši
            linerDotted.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
        }
        // Kreslení tečkované hrany mezi prvním bodem a aktuální pozicí myši
        linerDotted.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
    }

    /**
     * Metoda pro aktualizaci polygonu na základě události myši.
     *
     * @param polygon Instance třídy Polygon, která reprezentuje polygon
     * @param mouseEvent Instance třídy MouseEvent, která reprezentuje událost myši
     */
    public void updatePolygon(Polygon polygon, MouseEvent mouseEvent){
        Point tempPoint = new Point(0,0); // Dočasný bod pro porovnání vzdáleností
        double tempX;
        double tempY;
        double tempValue;
        double tempPointX;
        double tempPointY;
        double tempPointValue;

        for (int i = 0; i < polygon.getCount(); i++) {
            // Výpočet vzdálenosti aktuálního bodu od pozice myši
            tempX = polygon.getPoint(i).getX() - mouseEvent.getX();
            tempY = polygon.getPoint(i).getY() - mouseEvent.getY();
            tempValue = tempX + tempY;

            // Výpočet vzdálenosti dočasného bodu od pozice myši
            tempPointX = tempPoint.getX() - mouseEvent.getX();
            tempPointY = tempPoint.getY() - mouseEvent.getY();
            tempPointValue = tempPointX + tempPointY;

            // Porovnání vzdáleností a aktualizace bodu polygonu, pokud je blíže k pozici myši
            if (tempValue < 0 && tempValue > tempPointValue){
                tempPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                polygon.getPoint(i).setX(mouseEvent.getX());
                polygon.getPoint(i).setY(mouseEvent.getY());
            } else if (tempValue > 0 && tempValue < tempPointValue) {
                tempPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                polygon.getPoint(i).setX(mouseEvent.getX());
                polygon.getPoint(i).setY(mouseEvent.getY());
            }
        }
    }
}
