package fill;

import rasterdata.RasterBI;

import java.awt.*;
import java.util.Stack;

public class SeedFillerBorder implements Filler {
    private final int x;
    private final int y;
    private final Color fillColor, stopColor1, stopColor2;

    private final RasterBI raster;

    public SeedFillerBorder(int x, int y, Color fillColor, Color stopColor1, Color stopColor2, RasterBI raster) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.stopColor1 = stopColor1;
        this.stopColor2 = stopColor2;
        this.raster = raster;
    }

    @Override
    public void fill() {
        seedFill(x, y);
    }

    private void seedFill(int startX, int startY) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(startX, startY));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int x = p.x;
            int y = p.y;

            if (!raster.isOnRaster(x, y)) {
                continue;
            }

            Color color = new Color(raster.getPixel(x, y));

            if (!color.equals(stopColor1) && !color.equals(stopColor2) && !color.equals(fillColor)) {
                raster.setPixel(x, y, fillColor);

                stack.push(new Point(x + 1, y));
                stack.push(new Point(x - 1, y));
                stack.push(new Point(x, y + 1));
                stack.push(new Point(x, y - 1));
            }
        }
    }
}
