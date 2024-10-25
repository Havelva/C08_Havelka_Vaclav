package objectdata;

import java.awt.geom.Point2D;

/**
 * Třída Line reprezentuje úsečku definovanou dvěma body.
 * 
 * @author Vaclav Havelka
 */
public class Line {

    /**
     * První bod úsečky.
     */
    final Point p1;

    /**
     * Druhý bod úsečky.
     */
    final Point p2;

    /**
     * Konstruktor pro vytvoření úsečky se zadanými body.
     * 
     * @param p1 První bod úsečky.
     * @param p2 Druhý bod úsečky.
     */
    public Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Vrací první bod úsečky.
     * 
     * @return První bod úsečky.
     */
    public Point getP1() {
        return p1;
    }

    /**
     * Vrací druhý bod úsečky.
     * 
     * @return Druhý bod úsečky.
     */
    public Point getP2() {
        return p2;
    }
}
