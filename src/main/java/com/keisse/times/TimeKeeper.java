package com.keisse.times;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventListener;

public class TimeKeeper extends JFrame {
    JLabel timeLabel;
    JPanel panel;
    public TimeKeeper() {
        panel = new JPanel();
        this.setPreferredSize(new Dimension(400,400));
        addTimeLabel();
        panel.add(new JButton("START"));
        panel.add(new JButton("PAUZE"));
        panel.add(new JButton("STOP"));
        this.add(panel);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

    }

    private void addTimeLabel() {
        timeLabel = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ss")));
        timeLabel.setFont(new Font(timeLabel.getFont().getName(),Font.PLAIN,35));
        panel.add(timeLabel);
        updateTimer();
    }

    private void updateTimer(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Timer(50, actionEvent -> {
            timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm:ss")));
        }).start();

    }
}
