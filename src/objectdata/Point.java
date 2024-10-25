package objectdata;

/**
 * Třída představuje bod v rovině s dvojicí souřadnic x a y.
 */
public class Point {

    /**
     * Souřadnice x bodu.
     */
    public double x;

    /**
     * Souřadnice y bodu.
     */
    public double y;

    /**
     * Konstruktor pro vytvoření bodu s danými souřadnicemi x a y.
     *
     * @param x Souřadnice x bodu.
     * @param y Souřadnice y bodu.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Vrací souřadnici x bodu.
     *
     * @return Souřadnice x bodu.
     */
    public double getX() {
        return x;
    }

    /**
     * Vrací souřadnici y bodu.
     *
     * @return Souřadnice y bodu.
     */
    public double getY() {
        return y;
    }

    /**
     * Nastaví novou hodnotu souřadnice x bodu.
     *
     * @param x Nová hodnota souřadnice x.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Nastaví novou hodnotu souřadnice y bodu.
     *
     * @param y Nová hodnota souřadnice y.
     */
    public void setY(double y) {
        this.y = y;
    }
}
