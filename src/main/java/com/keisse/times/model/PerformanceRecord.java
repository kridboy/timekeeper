package com.keisse.times.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class PerformanceRecord implements Comparable<PerformanceRecord>{
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration duration;

    public PerformanceRecord(LocalTime startTime, LocalTime endTime){
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public PerformanceRecord() {
    }

    public void test(){
        //this was possible :D remember
        LocalTime t = LocalTime.now();
        PerformanceRecord p = new PerformanceRecord(t,t=LocalTime.MIDNIGHT);
        System.out.println("kek");
    }

    public PerformanceRecord(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
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
        duration = Duration.between(getStartTime(),getEndTime());
    }

    @Override
    public int compareTo(PerformanceRecord performanceRecord) {
        return getStartTime().compareTo(performanceRecord.getStartTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerformanceRecord)) return false;
        PerformanceRecord record = (PerformanceRecord) o;
        return Objects.equals(getStartTime(), record.getStartTime()) &&
                Objects.equals(getEndTime(), record.getEndTime()) &&
                Objects.equals(getDuration(), record.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getEndTime(), getDuration());
    }
}
