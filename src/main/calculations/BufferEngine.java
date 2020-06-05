package main.calculations;

import main.components.Level;
import main.components.View;

import java.awt.*;

public class BufferEngine {

    private int[] pixelBuffer;

    private int width;
    private int height;

    private Level level;
    private int[][] map;

    private int color;

    public BufferEngine(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setPixelBuffer(int[] pixelBuffer) {
        this.pixelBuffer = pixelBuffer;
    }

    public void update(View v, double frameTime) {
        groundAndSky();

        v.update(frameTime);

        for (int x = 0; x < width; x++) {

            double cameraX = 2 * (x) / (double)(width) - 1;


            double rayDirX = v.xRotation + v.xPlane * cameraX;
            double rayDirY = v.yRotation + v.yPlane * cameraX;

            int mapX = (int) v.xPosition;
            int mapY = (int) v.yPosition;

            double sideDistX;
            double sideDistY;

            double deltaDistX = Math.abs(1 / rayDirX);
            double deltaDistY = Math.abs(1 / rayDirY);
            double WallDist;

            //ray step
            int stepX;
            int stepY;

            boolean hit = false;
            int side = 0;

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (v.xPosition - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - v.xPosition) * deltaDistX;
            }

            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (v.yPosition - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - v.yPosition) * deltaDistY;
            }

            while (!hit) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }

                if (map[mapX][mapY] > 0) {
                    hit = true;
                }
            }

            if(side == 0) {
                WallDist = (mapX - v.xPosition + (1 - stepX) / 2) / rayDirX;
            } else {
                WallDist = (mapY - v.yPosition + (1 - stepY) / 2) / rayDirY;
            }

            int lineHeight = (int) (height / WallDist);

            int drawStart = -lineHeight / 2 + height / 2;
            if(drawStart < 0) {
                drawStart = 0;
            }
            int drawEnd = lineHeight / 2 + height / 2;
            if(drawEnd >= height) {
                drawEnd = height - 1;
            }


            switch(map[mapX][mapY]) {
                case 1:
                    color = Color.red.getRGB();
                    break;
                case 2:
                    color = Color.green.getRGB();
                    break;
                case 3:
                    color = Color.blue.getRGB();
                    break;
                case 4:
                    color = Color.magenta.getRGB();
                    break;
                default:
                    color = Color.yellow.getRGB();
                    break;
            }

            if (side == 1) {
                color = color / 2;
            }

            //draw a vertical ray, these make up the cubes
            drawVerLine(x, drawStart, drawEnd, color);

        }
    }

    //assigns a level and gets the map array
    public void setLevel(Level l) {
        level = l;
        map = level.getMap();
    }

    //draws a vertical line, which is a ray in this case
    private void drawVerLine(int x, int start, int end, int color) {
        for (int y = start; y < end; y++) {
            pixelBuffer[y * width + x] = color;
        }
    }

    //draws a top color and a bottom to give a sky and floor feel
    //when the rest of the rendering is done
    private void groundAndSky() {
        for (int i = 0; i < pixelBuffer.length; i++) {
            if (i < (pixelBuffer.length / 2)) {
                pixelBuffer[i] = Color.white.getRGB();
            } else {
                pixelBuffer[i] = Color.darkGray.getRGB();
            }
        }
    }
}
