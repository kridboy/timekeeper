package com.keisse.times.controller;

import com.keisse.times.model.PerformanceRecord;
import com.keisse.times.model.WorkDay;
import com.keisse.times.util.DataStore;
import com.keisse.times.view.MainView;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import static com.keisse.times.util.Normalizer.normalEndTime;
import static com.keisse.times.util.Normalizer.normalStartTime;

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

            if (today.hasPerformances())
                performance = new PerformanceRecord(LocalTime.now());
            else
                performance = new PerformanceRecord(normalStartTime(LocalTime.now()));

            today.Evaluate();
            button.setText(PAUZE);

        } else {
            isPauzed = true;
            performance.setEndTime(LocalTime.now());
            today.addPerformance(performance);
            today.Evaluate();
            button.setText(RESUME);
        }
        System.out.printf("BREAKTIME:[%dsec]||WORKEDTIME:[%dsec]%n", today.getBreakTime(),today.getWorkedTime());
        System.out.printf("BREAKTIME:[%.1fmin]||WORKEDTIME:[%.1fmin]%n",((float) today.getBreakTime() / 60), ((float) today.getWorkedTime() / 60));
    }

    public void stopTrackTime(JButton button) {
        System.err.println("STAHP");
        //TODO check if performance was started
        String ObjButtons[] = {"Yes", "No"};
        int PromptResult = JOptionPane.showOptionDialog(mainView.getFrame(), "Are you sure you You're done working?", "Exit Window", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {
            performance.setEndTime(normalEndTime(LocalTime.now()));
            today.addPerformance(performance);

            try {
                dataStore.writeWorkDayToFile(today);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            mainView.getFrame().dispose();
            System.exit(1);
        }

    }

    public static void updateTimerLabel(JLabel label) {
        label.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E, dd MMM HH:mm:ss")));
    }

    public void updatePerformanceField(JTextArea area) {
        //TODO WorkDay.toString() method
        StringBuilder builder = new StringBuilder();
        Iterator<PerformanceRecord> iterator = today.getPerformanceSet().iterator();
        while (iterator.hasNext()) {
            PerformanceRecord record = iterator.next();
            builder.append(String.format("startTime:[%s]||", record.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
            builder.append(String.format(" EndTime:[%s]%n", record.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        }
        if (performance.getEndTime() == null)
            builder.append("startTime:[" + performance.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "]");
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
