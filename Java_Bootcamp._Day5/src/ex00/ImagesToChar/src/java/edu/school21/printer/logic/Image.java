package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    public void printImage(String whitePixel, String blackPixel, String imagePath) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(imagePath));
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    Color currentColor = new Color(img.getRGB(j, i));
                    int colorSum = currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue();
                    if (colorSum > 0) {
                        System.out.print(whitePixel);
                    } else {
                        System.out.print(blackPixel);
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }
}
