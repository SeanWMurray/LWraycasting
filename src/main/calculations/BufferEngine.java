package main.calculations;

import java.awt.*;

public class BufferEngine {

    private int[] pixelBuffer;

    private int width;
    private int height;

    private int vertical;
    private int horizontal;

    private int color = Color.CYAN.getRGB();

    public BufferEngine(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPixelBuffer(int[] pixelBuffer) {
        this.pixelBuffer = pixelBuffer;
    }

    public void update() {
        vertical++;
//        for (int i = 0; i < pixelBuffer.length; i++) {
//            pixelBuffer[i] = Color.black.getRGB();
//        }
    }

    public void drawline(int x, int slope, int start, int length, int color) {
        int initslope = slope;
        int tempSlope = slope;
        for (int i = start; i < length; i++) {
            tempSlope += initslope;
            for (int k = 0; k < initslope; k++) {
                pixelBuffer[i * width + (x + (tempSlope + k))] = color;
            }
        }
    }

    public void cube(int topx, int topy, int bottomx, int bottomy) {
        for (int i = topx; i < bottomx; i++) {
            for (int k = topy; k < bottomy; k++) {
                pixelBuffer[((topy + k) * width) + i] = color;
            }
        }
    }
}
