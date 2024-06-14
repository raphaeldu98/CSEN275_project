package com.example.gardenproject;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private static TextArea logTextArea;
    private PrintWriter writer;

    public Logger(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLogTextArea(TextArea textArea) {
        logTextArea = textArea;
    }

    public void log(String message) {
        String logMessage = "Day " + SystemAPI.date + ": " + message;
        writer.println(logMessage);
        writer.flush();  // Ensure immediate flush after each write
        //updateLogTextArea(logMessage);
    }

    void updateLogTextArea(String message) {
        if (logTextArea != null) {
            Platform.runLater(() -> logTextArea.appendText(message + "\n"));
        }
    }

    public void close() {
        writer.close();
    }
}
