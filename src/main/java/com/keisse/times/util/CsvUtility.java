package com.keisse.times.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CsvUtility {
    public static final String REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)|(\r\n|\n)";
    private Queue<String> dataQueue;
    private int columns;
    private String headerNames;
    private final File inputFile;

    public CsvUtility(File file) throws IOException {
        inputFile = file;
        if (inputFile.exists()) {
            dataQueue = parseCsvToDataSet(inputFile);
        }
    }

    public Queue<String> getDataQueue() {
        return dataQueue;
    }

    public int getColumns() {
        return columns;
    }

    public String getHeaderNames() {
        return headerNames;
    }

    public File getInputFile() {
        return inputFile;
    }

    private Queue<String> parseCsvToDataSet(File file) throws IOException {
        Queue<String> dataQueue = new LinkedList<>(); //TODO might need comparator
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            while (scanner.hasNext())
                dataQueue.offer(scanner.nextLine());
        }
        Iterator<String> iterator = dataQueue.iterator();
        headerNames = iterator.next();
        iterator.remove();
        columns = headerNames.split(",").length;
        return dataQueue;
    }

    public void addData(String data) throws IOException {
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.append(data);
            dataQueue.add(data);
        }
    }
}
