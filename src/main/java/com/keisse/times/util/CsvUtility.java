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
    public static final String SRC_URI = "src/main/resources/WorkWeeks/";

    private Queue<String> dataQueue;
    private int columns;
    private String headerNames;
    private final File inputFile;
    private boolean isExistingFile;

    public CsvUtility(File file) throws IOException {
        inputFile = file;
        dataQueue = parseCsvToDataSet(inputFile);//TODO check if constructing with new file -> NPE
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
