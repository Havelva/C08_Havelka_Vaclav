package objectdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída Polygon reprezentuje mnohoúhelník složený z bodů.
 */
public class Polygon {
    private List<Point> points; // Seznam bodů, které tvoří mnohoúhelník

    /**
     * Konstruktor třídy Polygon, který inicializuje prázdný seznam bodů.
     */
    public Polygon() {
        this.points = new ArrayList<>();
    }

    /**
     * Přidá bod do mnohoúhelníku.
     * 
     * @param p Bod, který má být přidán.
     */
    public void addPoint(Point p) {
        points.add(p);
    }

    /**
     * Vrátí bod na zadaném indexu.
     * 
     * @param index Index bodu, který má být vrácen.
     * @return Bod na zadaném indexu.
     */
    public Point getPoint(int index) {
        return points.get(index);
    }

    /**
     * Vymaže všechny body z mnohoúhelníku.
     */
    public void clearPoints() {
        points.clear();
    }

    /**
     * Vrátí počet bodů v mnohoúhelníku.
     * 
     * @return Počet bodů.
     */
    public int getCount() {
        return points.size();
    }

    /**
     * Vrátí seznam všech bodů v mnohoúhelníku.
     * 
     * @return Seznam bodů.
     */
    public List<Point> getPoints() {
        return points;
    }
}
