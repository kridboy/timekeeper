package com.keisse.times.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WorkDay {
    private NavigableMap<PerformanceRecord, Boolean> performances = new TreeMap<>();
    private LocalDate date;
    private long breakTime = 0L;
    private long workedTime = 0L;

    public Set<PerformanceRecord> getPerformanceSet() {
        return performances.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
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
        performances.put(performance, false);
    }

    public void eval() {
      /*  Iterator<PerformanceRecord> iterator = performanceSet.iterator();
        while (iterator.hasNext()) {
            PerformanceRecord record = iterator.next();
            workedTime += record.getDuration().getSeconds();
            if (!iterator.hasNext())
                breakTime += Duration.between(record.getEndTime(), LocalTime.now()).getSeconds();

        }*/
    }

    public void Evaluate() {
        if (!performances.isEmpty()) {
            performances.forEach(this::calcTime);
            breakTime += Duration.between(performances.lastEntry().getKey().getEndTime(), LocalTime.now()).getSeconds();
        }
    }

    private void calcTime(PerformanceRecord performanceRecord, Boolean added) {
        if (!added) {
            performances.put(performanceRecord, true);
            workedTime += performanceRecord.getDuration().getSeconds();
        }
    }

    public boolean hasPerformances(){
        return !getPerformanceSet().isEmpty();
    }
}
