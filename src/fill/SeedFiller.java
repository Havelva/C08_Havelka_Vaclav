package fill;

import rasterdata.RasterBI;

import java.awt.*;
import java.util.Stack;

public class SeedFiller implements Filler {
    private final int x;
    private final int y;
    private final Color fillColor, backgroundColor;

    private final RasterBI raster;

    public SeedFiller(int x, int y, Color fillColor, RasterBI raster) {
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = raster.getBackgroundColor();
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

            if (color.equals(backgroundColor)) {
                raster.setPixel(x, y, fillColor);

                stack.push(new Point(x + 1, y));
                stack.push(new Point(x - 1, y));
                stack.push(new Point(x, y + 1));
                stack.push(new Point(x, y - 1));
            }
        }
    }
}
