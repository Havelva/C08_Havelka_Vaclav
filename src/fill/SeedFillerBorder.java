package fill;

import rasterdata.RasterBI;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

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
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] processed = new boolean[raster.getWidth()][raster.getHeight()];
        queue.add(new int[]{startX, startY});
        processed[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int x = p[0];
            int y = p[1];

            if (!raster.isOnRaster(x, y)) {
                continue;
            }

            Color color = new Color(raster.getPixel(x, y));

            if (!color.equals(stopColor1) && !color.equals(stopColor2) && !color.equals(fillColor)) {
                raster.setPixel(x, y, getPatternColor(x, y));

                int[][] neighbors = {
                    {x + 1, y},
                    {x - 1, y},
                    {x, y + 1},
                    {x, y - 1}
                };

                for (int[] neighbor : neighbors) {
                    int nx = neighbor[0];
                    int ny = neighbor[1];
                    if (nx >= 0 && ny >= 0 && nx < raster.getWidth() && ny < raster.getHeight() && !processed[nx][ny]) {
                        queue.add(neighbor);
                        processed[nx][ny] = true;
                    }
                }
            }
        }
    }

    private Color getPatternColor(int x, int y) {
        // Example pattern: checkerboard
        int patternSize = 10;
        boolean isEven = ((x / patternSize) % 2 == (y / patternSize) % 2);
        return isEven ? fillColor : fillColor.darker();
    }
}
