package com.keisse.times.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        evaluate();
    }

    public void evaluate() {
        //TODO still needs rewrite xD
        int count = performanceSet.size();
        Iterator<PerformanceRecord> iterator = performanceSet.iterator();
        PerformanceRecord record=null;
        if (count == 1) {
            record = iterator.next();
            workedTime = ChronoUnit.SECONDS.between(record.getStartTime(), record.getEndTime());
        } else {
            while (iterator.hasNext()) {
                if(record!=null){
                    breakTime += ChronoUnit.SECONDS.between(record.getEndTime(),(record = iterator.next()).getEndTime());
                    workedTime = ChronoUnit.SECONDS.between(record.getStartTime(), record.getEndTime());
                }else {
                    record = iterator.next();
                    workedTime += ChronoUnit.SECONDS.between(record.getStartTime(), record.getEndTime());
                }
            }


        }
    }

    private void evaluatePerformances() {
        //TODO this needs rewrite
        int count = performanceSet.size();
        Iterator<PerformanceRecord> iterator = performanceSet.iterator();

        if (count == 1) {
            PerformanceRecord record = iterator.next();
            workedTime = ChronoUnit.SECONDS.between(record.getStartTime(), record.getEndTime());
            breakTime = 0L;
        } else {
            while (iterator.hasNext()) {
                PerformanceRecord record = iterator.next();
                LocalTime breakStart = record.getEndTime();
                workedTime += ChronoUnit.SECONDS.between(record.getStartTime(), record.getEndTime());

                record = iterator.next();
                LocalTime breakEnd = record.getStartTime();
                breakTime += ChronoUnit.SECONDS.between(breakStart, breakEnd);
            }
        }
    }
}
