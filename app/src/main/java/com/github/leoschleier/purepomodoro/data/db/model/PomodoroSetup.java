package com.github.leoschleier.purepomodoro.data.db.model;

import androidx.annotation.Nullable;
import com.github.leoschleier.purepomodoro.utils.AppConstants;

public class PomodoroSetup {

    private Integer id;

    private String name;

    private Integer nIntervals;

    private Long workDurationMin;

    private Long shortBreakDurationMin;

    private Long longBreakDurationMin;

    public PomodoroSetup(@Nullable Integer id, String name, Integer nIntervals, Long workDurationMin,
                         Long shortBreakDurationMin, Long longBreakDurationMin) {
        this.id = id == null ? AppConstants.CUSTOM_SETUP_ID : AppConstants.DEFAULT_SETUP_ID;
        this.name = name;
        this.nIntervals = nIntervals;
        this.workDurationMin = workDurationMin;
        this.shortBreakDurationMin = shortBreakDurationMin;
        this.longBreakDurationMin = longBreakDurationMin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getnIntervals() {
        return nIntervals;
    }

    public void setnIntervals(Integer nIntervals) {
        this.nIntervals = nIntervals;
    }

    public Long getWorkDurationMin() {
        return workDurationMin;
    }

    public void setWorkDurationMin(Long workDurationMin) {
        this.workDurationMin = workDurationMin;
    }

    public Long getShortBreakDurationMin() {
        return shortBreakDurationMin;
    }

    public void setShortBreakDurationMin(Long shortBreakDurationMin) {
        this.shortBreakDurationMin = shortBreakDurationMin;
    }

    public Long getLongBreakDurationMin() {
        return longBreakDurationMin;
    }

    public void setLongBreakDurationMin(Long longBreakDurationMin) {
        this.longBreakDurationMin = longBreakDurationMin;
    }
}
