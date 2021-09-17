package com.github.ll30n4rd0.purepomodoro.model;


import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;

public class Pomodoro implements MainActivityContract.IModel{
    private int numIntervals, currentInterval;
    private int workDurationSeconds, shortBreakDurationSeconds, longBreakDurationSeconds;
    private Pomodoro.State currentPomodoroState;

    public Pomodoro(int numIntervals, int workDurationSeconds, int shortBreakDurationSeconds, int longBreakDurationSeconds) {
        this.numIntervals = numIntervals;
        this.workDurationSeconds = workDurationSeconds;
        this.shortBreakDurationSeconds = shortBreakDurationSeconds;
        this.longBreakDurationSeconds = longBreakDurationSeconds;
        initState();
    }

    public Pomodoro(){
        this.numIntervals = 4;
        this.workDurationSeconds = 25*60;
        this.shortBreakDurationSeconds = 5*60;
        this.longBreakDurationSeconds = 20*60;
        initState();
    }

    public enum State {
        WORK,
        SHORTBREAK,
        LONGBREAK
    }

    private void initState(){
        this.currentInterval = 1;
        this.currentPomodoroState = Pomodoro.State.WORK;
    }

    @Override
    public int getNumIntervals() {
        return numIntervals;
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
    public Pomodoro.State getCurrentPomodoroState(){
        return currentPomodoroState;
    }

    @Override
    public Pomodoro.State nextPomodoroState() {
        if (currentPomodoroState == Pomodoro.State.WORK) {
            if (currentInterval == numIntervals) {
                currentPomodoroState = Pomodoro.State.LONGBREAK;
            }else{
                currentPomodoroState = Pomodoro.State.SHORTBREAK;
            }
        }else{
            nextInterval();
            currentPomodoroState = Pomodoro.State.WORK;
        }
        return currentPomodoroState;
    }

    private void nextInterval() {
        if (currentInterval == numIntervals) {
            currentInterval = 1;
        } else {
            currentInterval++;
        }
    }

}
