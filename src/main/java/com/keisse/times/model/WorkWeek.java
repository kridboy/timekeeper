package com.keisse.times.model;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WorkWeek {
    private Map<LocalDate, WorkDay> workWeek = new HashMap<>();
    private Integer weekOfYear;

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public WorkWeek() {
    }

    public WorkWeek(Map<LocalDate, WorkDay> workWeek) {
        this.workWeek = workWeek;
        weekOfYear = workWeek.keySet()
                .iterator()
                .next()
                .get(WeekFields.ISO.weekOfWeekBasedYear());
    }

    public WorkDay getWorkDay(LocalDate date) {
        return workWeek.get(date);
    }

    public void addWorkDay(WorkDay day) {
        if(workWeek.size()==0)
            weekOfYear = day.getDate().get(WeekFields.ISO.weekOfWeekBasedYear());

        if (workWeek.containsKey(day.getDate()))
            throw new RuntimeException("Day is already present in workWeek..");

        updateWorkDay(day);
    }

    public void updateWorkDay(WorkDay day) {
        workWeek.put(day.getDate(), day);
    }

    public void removeWorkDay(WorkDay day) {
        removeWorkDay(day.getDate());
    }

    public void removeWorkDay(LocalDate date) {
        workWeek.remove(date);
    }
}
