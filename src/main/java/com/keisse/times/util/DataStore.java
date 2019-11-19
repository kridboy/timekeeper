package com.keisse.times.util;

import com.keisse.times.model.PerformanceRecord;
import com.keisse.times.model.WorkDay;
import com.keisse.times.model.WorkWeek;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class DataStore {
    private static final String BASE_URI = "src/main/resources/WorkWeeks/";
    private static final File BASE_FOLDER = new File(BASE_URI);
    private static DataStore instance;

    private DataStore() {
        BASE_FOLDER.mkdirs();
    }

    public static DataStore getInstance() {
        return instance == null ? new DataStore() : instance;
    }

    public Map<Integer, WorkWeek> collectAllData() throws IOException {
        Map<Integer, WorkWeek> dataMap = new HashMap<>();

        for (File weekFolder : BASE_FOLDER.listFiles()) {
            WorkWeek week = new WorkWeek();
            if (BASE_FOLDER.listFiles().length == 0) {
                for (File dataFile : weekFolder.listFiles()) {
                    CsvUtility csvUtil = new CsvUtility(dataFile);
                    Queue<String> dataQueue = csvUtil.getDataQueue();

                    week.addWorkDay(parseWorkDay(dataQueue));
                }
                dataMap.put(week.getWeekOfYear(), week);
            }
        }
        return dataMap;
    }

    public void writeWorkDayToFile(WorkDay day) throws IOException {
        Integer weekOfYear = day.getDate().get(WeekFields.ISO.weekOfWeekBasedYear());
        File file = new File(BASE_URI + weekOfYear + '/' + day.getDate() + ".csv");

        file.getParentFile().mkdirs();
        if (!file.exists())
            file.createNewFile();
        new CsvUtility(file).addData(buildCsv(day));
    }

    private WorkDay parseWorkDay(Queue<String> dataQueue) {
        WorkDay day = new WorkDay(LocalDate.parse(dataQueue.peek().split(CsvUtility.REGEX)[0]));
        while (!dataQueue.isEmpty()) {
            String row = dataQueue.poll();
            String[] tableRow = row.split(CsvUtility.REGEX);
            LocalTime startTime = LocalTime.parse(tableRow[1]);
            LocalTime endTime = LocalTime.parse(tableRow[2]);
            PerformanceRecord record = new PerformanceRecord(startTime, endTime);

            day.addPerformance(record);
        }
        return day;
    }

    private String buildCsv(WorkDay day) {
        StringBuilder builder = new StringBuilder();
        for (PerformanceRecord e : day.getPerformanceSet()) {
            builder.append(day.getDate())
                    .append(',')
                    .append(e.getStartTime())
                    .append(',')
                    .append(e.getEndTime())
                    .append("%n");
        }
        return builder.toString();
    }
}
