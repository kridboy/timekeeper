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
    private static final String PAUZE = "PAUZE";
    private static MainController instance;
    private static DataStore dataStore = DataStore.getInstance();
    private static MainView mainView = MainView.getInstance();
    private WorkDay today;
    private PerformanceRecord performance;
    private String records;
    private boolean isPauzed;

    private MainController() {
    }

    public static MainController getInstance() {
        return instance == null ? instance = new MainController() : instance;
    }

    public void startTrackTime() {
        System.err.println("STARTED");
        today = new WorkDay(LocalDate.now());
        performance = new PerformanceRecord(LocalTime.now());
        isPauzed = false;
    }

    public void pauzeTrackTime(JButton button) {
        if(!isPauzed){
            isPauzed = true;
            performance.setEndTime(LocalTime.now());
            today.addPerformance(performance);
            button.setText("RESUME");
        }else{
            isPauzed = false;
            performance = new PerformanceRecord(LocalTime.now());
            button.setText(PAUZE);
        }
        System.err.println(PAUZE);
    }

    public void pauze(JButton button){

    }

    public void stopTrackTime() {
        System.err.println("STAHP");
        performance.setEndTime(LocalTime.now());
        today.addPerformance(performance);
    }

    public void updateTimerLabel(JLabel label) {
        label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E, dd MMM hh:mm:ss")));
    }

    public void updatePerformanceField(JTextArea area) {
        JTextArea newArea = new JTextArea();
        StringBuilder builder = new StringBuilder();
        if (today != null && today.getPerformanceSet() != null && today.getPerformanceSet().size() > 0 && !isPauzed) {
            Iterator<PerformanceRecord> iterator = today.getPerformanceSet().iterator();
            while (iterator.hasNext()) {
                PerformanceRecord record = iterator.next();
                builder.append(String.format("startTime:[%s]", record.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
                builder.append(String.format(" EndTime:[%s]%n", record.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
            }
            area.setText(builder.toString());
        }
    }


    private void addActionListenerForComponents(JButton startButton, JButton pauzeButton, JButton stopButton, JTextArea performanceArea) {
        startButton.addActionListener(actionEvent -> startTrackTime());
        pauzeButton.addActionListener(actionEvent -> {
            pauzeTrackTime(pauzeButton);
            updatePerformanceField(performanceArea);
        });
        stopButton.addActionListener(actionEvent -> stopTrackTime());
    }

    public void initialize() {
        addActionListenerForComponents(
                mainView.getStartButton(),
                mainView.getPauzeButton(),
                mainView.getStopButton(),
                mainView.getPerformanceArea());

        mainView.openFrame();

    }
}
