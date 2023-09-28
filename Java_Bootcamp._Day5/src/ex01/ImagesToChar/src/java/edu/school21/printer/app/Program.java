package edu.school21.printer.app;

import edu.school21.printer.logic.Image;

public class Program {
    public static void main(String[] args) {
        if (args.length == 2) {
            Image printer = new Image();
            printer.printImage(args[0], args[1]);
        } else {
            System.err.println("Wrong command line arguments");
            System.exit(-1);
        }

    }
}
