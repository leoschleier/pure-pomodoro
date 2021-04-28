package com.github.ll30n4rd0.purepomodoro.model;


import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;

public class Pomodoro implements MainActivityContract.IModel{
    private int intervals, currentInterval;
    private int workDurationSeconds, shortBreakDurationSeconds, longBreakDurationSeconds;
    private PomodoroStreak pomodoroStreak;
    private PomodoroState currentPomodoroState;

    public Pomodoro(int intervals, int workDurationSeconds, int shortBreakDurationSeconds, int longBreakDurationSeconds) {
        this.intervals = intervals;
        this.workDurationSeconds = workDurationSeconds;
        this.shortBreakDurationSeconds = shortBreakDurationSeconds;
        this.longBreakDurationSeconds = longBreakDurationSeconds;
        currentInterval = 1;
        currentPomodoroState = PomodoroState.WORK;
        pomodoroStreak = new PomodoroStreak();
    }

    public Pomodoro(){
        this.intervals = 4;
        this.workDurationSeconds = 25*60;
        this.shortBreakDurationSeconds = 5*60;
        this.longBreakDurationSeconds = 20*60;
        currentInterval = 1;
        currentPomodoroState = PomodoroState.WORK;
        pomodoroStreak = new PomodoroStreak();
    }

    @Override
    public int getIntervals() {
        return intervals;
    }

    @Override
    public int getCurrentInterval() {
        return currentInterval;
    }

    @Override
    public int getWorkDurationSeconds() {
        return workDurationSeconds;
    }

    @Override
    public int getShortBreakDurationSeconds() {
        return shortBreakDurationSeconds;
    }

    @Override
    public int getLongBreakDurationSeconds() {
        return longBreakDurationSeconds;
    }

    @Override
    public PomodoroState getCurrentPomodoroState(){
        return currentPomodoroState;
    }

    @Override
    public PomodoroState getNextPomodoroState() {
        if (currentPomodoroState == PomodoroState.WORK) {
            pomodoroStreak.addWorkDuration(workDurationSeconds);
            if (currentInterval == intervals) {
                currentPomodoroState = PomodoroState.LONGBREAK;
            }else{
                currentPomodoroState = PomodoroState.SHORTBREAK;
            }
        }else{
            if (currentPomodoroState==PomodoroState.SHORTBREAK){
                pomodoroStreak.addBreakDuration(shortBreakDurationSeconds);
            }else{
                pomodoroStreak.addBreakDuration(longBreakDurationSeconds);
            }
            nextInterval();
            currentPomodoroState = PomodoroState.WORK;
        }
        return currentPomodoroState;
    }

    @Override
    public int getCompletedPomodoros() {
        return pomodoroStreak.getCompletedPomodoros();
    }

    @Override
    public int getTotalDurationSeconds() {
        return pomodoroStreak.getTotalDurationSeconds();
    }

    @Override
    public int getTotalWorkDurationSeconds() {
        return pomodoroStreak.getTotalWorkDurationSeconds();
    }

    @Override
    public int getTotalBreakDurationSeconds() {
        return pomodoroStreak.getTotalBreakDurationSeconds();
    }

    private void nextInterval() {
        if (currentInterval == intervals) {
            currentInterval = 1;
        } else {
            currentInterval++;
        }
    }

}
