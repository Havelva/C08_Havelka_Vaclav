package rasterdata;

import java.util.Optional;

/**
 * Rozhraní Raster představuje základní operace pro práci s rastrovými daty.
 */
public interface Raster {

    /**
     * Vrací šířku rastru.
     *
     * @return šířka rastru
     */
    int getWidth();

    /**
     * Vrací výšku rastru.
     *
     * @return výška rastru
     */
    int getHeight();

    /**
     * Nastaví barvu pixelu na dané pozici.
     *
     * @param c sloupec pixelu
     * @param r řádek pixelu
     * @param color barva, která má být nastavena
     * @return true, pokud byla barva úspěšně nastavena, jinak false
     */
    boolean setColor(int c, int r, int color);

    /**
     * Vrací barvu pixelu na dané pozici, pokud existuje.
     *
     * @param c sloupec pixelu
     * @param r řádek pixelu
     * @return Optional obsahující barvu pixelu, pokud existuje, jinak prázdný Optional
     */
    Optional<Integer> getColor(int c, int r);

    /**
     * Vymaže raster a nastaví všechny pixely na zadanou barvu pozadí.
     *
     * @param background barva pozadí, která má být nastavena
     */
    void clear(int background);

}
