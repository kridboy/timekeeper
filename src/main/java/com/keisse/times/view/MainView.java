package com.keisse.times.view;

import com.keisse.times.controller.MainController;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainView {
    private static MainView instance;
    private MainController mainController;
    private JFrame frame;
    private JLabel timeLabel;
    private JPanel mainPanel;
    private JTextArea performanceArea;
    private JButton startButton;
    private JButton pauzeButton;
    private JButton stopButton;
    private static final String START= "START";
    private static final String PAUZE= "PAUZE";
    private static final String STOP = "STOP";

    private MainView() {
        mainController = MainController.getInstance();
        initializePanel();
    }

    public static MainView getInstance() {
        return instance == null ? instance = new MainView() : instance;
    }

    public static void startTrackTime() {
        System.err.println(START);

    }

    public MainController getMainController() {
        return mainController;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextArea getPerformanceArea() {
        return performanceArea;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getPauzeButton() {
        return pauzeButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public void setPerformanceArea(JTextArea performanceArea) {
        this.performanceArea = performanceArea;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public void setPauzeButton(JButton pauzeButton) {
        this.pauzeButton = pauzeButton;
    }

    public void setStopButton(JButton stopButton) {
        this.stopButton = stopButton;
    }

    private static void pauzeTrackTime() {
        System.err.println(PAUZE);
    }

    private static void stopTrackTime() {
        System.err.println("STAHP");
    }

    private void updateTimer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Timer(250, actionEvent -> mainController.updateTimerLabel(timeLabel)).start();
    }

    private void initializePanel() {
        mainPanel = new JPanel();
        frame = new JFrame();
        startButton = new JButton(START);
        pauzeButton = new JButton(PAUZE);
        stopButton = new JButton(STOP);
        performanceArea = new JTextArea();
        performanceArea.setColumns(30);
        frame.setPreferredSize(new Dimension(400, 400));
        timeLabel = new JLabel();
        timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.PLAIN, 35));
        mainPanel.add(timeLabel);

        mainPanel.add(startButton);
        mainPanel.add(pauzeButton);
        mainPanel.add(stopButton);
        mainPanel.add(performanceArea);

        frame.add(mainPanel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void openFrame() {
        updateTimer();
        frame.pack();
        frame.setVisible(true);
    }
}
