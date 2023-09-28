package edu.school21.printer.app;

import edu.school21.printer.logic.Image;

public class Program {
    public static void main(String[] args) {
        if (args.length == 3) {
            Image printer = new Image();
            printer.printImage(args[0], args[1], args[2]);
        } else {
            System.err.println("Wrong command line arguments");
            System.exit(-1);
        }

    }
}
