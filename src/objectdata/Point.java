package objectdata;

// Třída reprezentující bod v 2D prostoru
public class Point {
    // Soukromé proměnné pro souřadnice x a y
    private int x, y;

    // Konstruktor přijímající celočíselné hodnoty pro x a y
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Konstruktor přijímající desetinné hodnoty pro x a y, které jsou převedeny na celé číslo
    public Point(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    // Metoda pro získání hodnoty x
    public int getX() {
        return x;
    }

    // Metoda pro získání hodnoty y
    public int getY() {
        return y;
    }

    // Metoda pro nastavení hodnoty x
    public void setX(int x) {
        this.x = x;
    }

    // Metoda pro nastavení hodnoty y
    public void setY(int y) {
        this.y = y;
    }

    // Metoda pro výpočet vzdálenosti mezi aktuálním bodem a bodem se zadanými souřadnicemi x a y
    public double countDistance(int x, int y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }
}
