package com.keisse.times;


import javax.swing.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new TimeKeeper();
    }
}
