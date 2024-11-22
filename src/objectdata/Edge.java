package objectdata;

public class Edge {
    private int x1, y1, x2, y2; // Souřadnice dvou bodů, které tvoří hranu
    private float k, q; // Parametry přímky (směrnice a posun)

    // Konstruktor, který inicializuje souřadnice bodů
    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    // Getter pro směrnici přímky
    public float getK() {
        return k;
    }

    // Getter pro posun přímky
    public float getQ() {
        return q;
    }

    // Metoda, která kontroluje, zda je hrana horizontální
    public boolean isHorizontal() {
        return y1 == y2;
    }

    // Metoda, která orientuje hranu tak, aby y1 bylo menší nebo rovno y2
    public void orientate() {
        if (y1 > y2) {
            int _x1 = x1;
            int _y1 = y1;
            x1 = x2;
            y1 = y2;
            x2 = _x1;
            y2 = _y1;
        }
    }

    // Metoda, která vypočítá směrnici a posun přímky
    public void calculate() {
        k = (y2 - y1) / (float) (x2 - x1);
        q = y1 - k * x1;
    }

    // Metoda, která kontroluje, zda daná y-souřadnice protíná hranu
    public boolean isIntersection(int y) {
        return y > y1 && y <= y2;
    }

    // Metoda, která vrací x-souřadnici průsečíku hrany s danou y-souřadnicí
    public int getIntersection(int y) {
        return x1 == x2 ? x1 : (int) ((y - q) / k);
    }

    // Gettery pro souřadnice bodů
    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}
