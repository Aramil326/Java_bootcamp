package edu.school21.printer.app;

import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.Image;
import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) {
        if (args.length == 2) {
            Args jArgs = new Args();
            JCommander jCommander = new JCommander(jArgs);
            jCommander.parse(args);
            Image printer = new Image(jArgs);
            printer.printImage();
        } else {
            System.err.println("Wrong command line arguments");
            System.exit(-1);
        }
    }
}
