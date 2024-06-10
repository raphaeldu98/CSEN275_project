package com.example.gardenproject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private PrintWriter writer;

    public Logger(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        writer.println(message);
        writer.flush();  // Ensure immediate flush after each write
    }

    public void close() {
        writer.close();
    }
}