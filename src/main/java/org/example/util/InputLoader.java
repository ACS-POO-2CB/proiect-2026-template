package org.example.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.example.command.Command;
import org.example.command.CommandFactory;

public class InputLoader {

    public void processInputFile(String inputPath) {
        String actualInputPath = inputPath;
        File inputFile = new File(actualInputPath);
        if (!inputFile.exists() && !actualInputPath.endsWith(".in")) {
            File potentialFile = new File(actualInputPath + ".in");
            if (potentialFile.exists()) {
                actualInputPath = actualInputPath + ".in";
            }
        }

        // Output path is inputPath replacing .in with .out
        // Or if it doesn't end with .in, just append .out?
        // The requirements say "stocate în fișiere cu extensia .out".
        // Example: servers_01.in -> servers_01.out
        String outputPath;
        if (actualInputPath.endsWith(".in")) {
            outputPath = actualInputPath.substring(0, actualInputPath.length() - 3) + ".out";
        } else {
            outputPath = actualInputPath + ".out";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(actualInputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            String headerLine = reader.readLine();
            if (headerLine == null) return;

            String[] headers = headerLine.split("\\|");
            // Trim headers?
            for (int i=0; i<headers.length; i++) headers[i] = headers[i].trim();

            String line;
            int lineNumber = 1; // Header is line 1?
            // "## line no: <lineNumber>"
            // Usually line number refers to the data line in the file.
            // If header is line 1, first data row is line 2.
            // Let's check the examples.
            // But usually we start counting from the beginning of the file.
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] values = line.split("\\|", -1); // -1 to keep empty strings
                
                Map<String, String> data = new HashMap<>();
                // Assume values correspond to headers.
                // The first column is "command".
                String commandType = null;

                for (int i = 0; i < headers.length && i < values.length; i++) {
                    String key = headers[i];
                    String value = values[i].trim();
                    data.put(key, value);
                    
                    if (key.equalsIgnoreCase("command")) {
                        commandType = value;
                    }
                }

                if (commandType != null) {
                    Command command = CommandFactory.createCommand(commandType, data, lineNumber);
                    if (command != null) {
                        String result = command.execute();
                        if (result != null && !result.isEmpty()) {
                            writer.write(result);
                            writer.newLine();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
