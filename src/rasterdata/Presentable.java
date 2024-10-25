package rasterdata;

import java.awt.*;

/**
 * Rozhraní Presentable definuje metodu pro prezentaci grafického obsahu.
 * 
 * @method present Metoda, která vykresluje grafický obsah pomocí objektu Graphics.
 * @param graphics Objekt Graphics, který slouží k vykreslování grafického obsahu.
 */
public interface Presentable {

    void present (Graphics graphics);
}
