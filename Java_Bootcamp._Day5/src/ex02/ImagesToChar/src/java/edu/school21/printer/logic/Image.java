package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Image {
    private final String white;
    private final String black;

    public Image(Args args) {
        this.white = args.getWhite();
        this.black = args.getBlack();
    }

    public void printImage() {
        ColoredPrinter printer = new ColoredPrinter();
        BufferedImage img;
        try {
            img = ImageIO.read(new File("target/resources/it.bmp"));
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    Color currentColor = new Color(img.getRGB(j, i));
                    int colorSum = currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue();
                    if (colorSum > 0) {
                        printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(white));
                    } else {
                        printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(black));
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
