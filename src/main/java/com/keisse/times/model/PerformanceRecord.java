package com.keisse.times.model;

import java.time.LocalTime;

public class PerformanceRecord {
    private LocalTime startTime;
    private LocalTime endTime;

    public PerformanceRecord(LocalTime startTime, LocalTime endTime) {
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public PerformanceRecord(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    private void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
