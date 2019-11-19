package com.keisse.times.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WorkDay {
    private Set<PerformanceRecord> performanceSet = new HashSet<>();
    private LocalDate date;
    private long breakTime = 0L;
    private long workedTime = 0L;

    public Set<PerformanceRecord> getPerformanceSet() {
        return performanceSet;
    }

    public WorkDay(LocalDate date) {
        this.date = date;
    }

    public long getBreakTime() {
        return breakTime;
    }

    public long getWorkedTime() {
        return workedTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addPerformance(PerformanceRecord performance) {
        performanceSet.add(performance);
    }

    public void eval() {
        Iterator<PerformanceRecord> iterator = performanceSet.iterator();
        while (iterator.hasNext()) {
            PerformanceRecord record = iterator.next();
            workedTime += record.getDuration().getSeconds();
            if (!iterator.hasNext())
                breakTime += Duration.between(record.getEndTime(), LocalTime.now()).getSeconds();

        }
    }
}
