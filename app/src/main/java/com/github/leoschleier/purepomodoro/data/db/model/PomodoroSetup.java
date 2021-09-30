package com.github.leoschleier.purepomodoro.data.db.model;

public class PomodoroSetup {

    private Integer id;

    private String name;

    private Integer nIntervals;

    private Integer workDurationMin;

    private Integer shortBreakDurationMin;

    private Integer longBreakDurationMin;

    public PomodoroSetup(Integer id, String name, Integer nIntervals, Integer workDurationMin,
                         Integer shortBreakDurationMin, Integer longBreakDurationMin) {
        this.id = id;
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

    public Integer getWorkDurationMin() {
        return workDurationMin;
    }

    public void setWorkDurationMin(Integer workDurationMin) {
        this.workDurationMin = workDurationMin;
    }

    public Integer getShortBreakDurationMin() {
        return shortBreakDurationMin;
    }

    public void setShortBreakDurationMin(Integer shortBreakDurationMin) {
        this.shortBreakDurationMin = shortBreakDurationMin;
    }

    public Integer getLongBreakDurationMin() {
        return longBreakDurationMin;
    }

    public void setLongBreakDurationMin(Integer longBreakDurationMin) {
        this.longBreakDurationMin = longBreakDurationMin;
    }
}
