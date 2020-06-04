package main.display;

import main.calculations.BufferEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RayDisplay extends JFrame implements Runnable {

    private int width = 640;
    private int height = 480;
    private int topBar;

    private long FRAME_TIME_MILIS = 1000 / 30;

    private String title = "Raycasting";

    private Thread rayThread;

    private Boolean enabled;

    private BufferedImage frame;
    private BufferEngine bufferEng;

    public RayDisplay() {

        bufferEng = new BufferEngine(width, height);
        frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferEng.setPixelBuffer(((DataBufferInt) frame.getRaster().getDataBuffer()).getData());

        setSize(width, height);
        setResizable(false);
        setTitle(title);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        topBar = (int) (height - getContentPane().getSize().getHeight());
        setSize(width, height + topBar);

        rayThread = new Thread(this);
        rayThread.start();
    }

    @Override
    public void run() {
        begin();
    }

    public void begin() {
        long startTime;
        long difference;
        long delayTime;
        enabled = true;

        while(enabled) {

            startTime = System.nanoTime();
            bufferEng.update();
            draw();

            difference = System.nanoTime() - startTime;
            delayTime = FRAME_TIME_MILIS - (difference / 1000000);
            if (delayTime < 0) {
                delayTime = 0;
            }

            try {
                Thread.sleep(delayTime);
            } catch (InterruptedException e) { }
        }

        System.exit(0);
    }

    private void draw() {
        BufferStrategy buffer = getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(3);
            buffer = getBufferStrategy();
        }

        Graphics g = buffer.getDrawGraphics();
        g.drawImage(frame, 0, 0, getWidth(), getHeight(), null);
        buffer.show();
    }

    public void end() {
        enabled = false;
    }

}
