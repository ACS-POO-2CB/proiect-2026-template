package org.example;

import org.example.util.InputLoader;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <PathType> <InputFile>...");
            return;
        }

        InputLoader loader = new InputLoader();

        if (args.length == 2) {
            // Process single file
            loader.processInputFile(args[1]);
        } else if (args.length == 4) {
            // Process servers, groups, events
            // args[1] = servers file
            // args[2] = groups file
            // args[3] = events file
            loader.processInputFile(args[1]);
            loader.processInputFile(args[2]);
            loader.processInputFile(args[3]);
        }
    }
}
