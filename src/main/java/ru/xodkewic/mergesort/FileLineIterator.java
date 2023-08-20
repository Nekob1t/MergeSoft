package ru.xodkewic.mergesort;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLineIterator {
    private final BufferedReader bufReader;
    private String currentLine;

    public FileLineIterator(String fileName) throws Exception {
        FileReader fileReader = new FileReader(fileName);
        bufReader = new BufferedReader(fileReader);
    }

    public void next() {
        try {
            this.currentLine = bufReader.readLine();
            while (this.currentLine != null && this.currentLine.contains(" "))
                this.currentLine = bufReader.readLine();
        }
        catch (IOException e) {
            this.currentLine = null;
            System.out.println(e.getMessage());
        }
    }

    public String getCurrentLine() {
        return this.currentLine;
    }
}
