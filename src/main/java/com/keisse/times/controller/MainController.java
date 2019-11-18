package com.keisse.times.controller;

import com.keisse.times.model.PerformanceRecord;
import com.keisse.times.model.WorkDay;
import com.keisse.times.util.DataStore;
import com.keisse.times.view.MainView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class MainController {
    private static final String PAUZE = " PAUZE ";
    private static final String START = " START ";
    private static final String RESUME = "RESUME";
    private static final String STOP = "STOP";
    private static MainController instance;
    private static DataStore dataStore = DataStore.getInstance();
    private static MainView mainView = MainView.getInstance();
    private WorkDay today;
    private PerformanceRecord performance;
    private String records;
    private boolean isPauzed = true;

    private MainController() {
        today = new WorkDay(LocalDate.now());
    }

    public static MainController getInstance() {
        return instance == null ? instance = new MainController() : instance;
    }

    public void toggleTrackTime(JButton button) {
        System.err.println("TOGGLE");
        if (isPauzed) {
            isPauzed = false;
            performance = new PerformanceRecord(LocalTime.now());
            button.setText(PAUZE);
        } else {
            isPauzed = true;
            performance.setEndTime(LocalTime.now());
            today.addPerformance(performance);
            button.setText(RESUME);
        }
        System.out.printf("BREAKTIME:[%dsec]||WORKEDTIME:[%dsec]%n",today.getBreakTime(),today.getWorkedTime());
    }

    public void stopTrackTime(JButton button) {
        System.err.println("STAHP");
        performance.setEndTime(LocalTime.now());
        today.addPerformance(performance);
    }

    public static void updateTimerLabel(JLabel label) {
        label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E, dd MMM hh:mm:ss")));
    }

    public void updatePerformanceField(JTextArea area) {
        //TODO WorkDay.toString() method
        StringBuilder builder = new StringBuilder();
        Iterator<PerformanceRecord> iterator = today.getPerformanceSet().iterator();
        while (iterator.hasNext()) {
            PerformanceRecord record = iterator.next();
            builder.append(String.format("startTime:[%s]||", record.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
            builder.append(String.format(" EndTime:[%s]%n", record.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
        }
        if(performance.getEndTime()==null)
            builder.append("Performance started: @["+performance.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))+"]");
        area.setText(builder.toString());
    }


    private void addActionListenerForComponents(JButton startButton, JButton stopButton, JTextArea performanceArea) {
        startButton.addActionListener(actionEvent -> {
            toggleTrackTime(startButton);
            updatePerformanceField(performanceArea);
        });
        stopButton.addActionListener(actionEvent -> stopTrackTime(stopButton));
    }

    public void initialize() {
        addActionListenerForComponents(
                mainView.getStartButton(),
                mainView.getStopButton(),
                mainView.getPerformanceArea());

        mainView.openFrame();

    }
}
