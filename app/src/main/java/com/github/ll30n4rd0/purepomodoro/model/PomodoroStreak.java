package com.github.ll30n4rd0.purepomodoro.model;

public class PomodoroStreak {
    private int completedPomodoros, totalWorkDurationSeconds, totalBreakDurationSeconds, totalDurationSeconds;

    public PomodoroStreak(){
        completedPomodoros = 0;
        totalWorkDurationSeconds = 0;
        totalBreakDurationSeconds = 0;
        totalDurationSeconds = 0;
    }

    public int getCompletedPomodoros() {
        return completedPomodoros;
    }

    public int getTotalWorkDurationSeconds(){
        return totalWorkDurationSeconds;
    }

    public int getTotalBreakDurationSeconds() {
        return totalBreakDurationSeconds;
    }

    public int getTotalDurationSeconds() {
        return totalDurationSeconds;
    }

    public void addWorkDuration(int seconds){
        totalWorkDurationSeconds+=seconds;
        addTotalDuration(seconds);
        addCompletedPomodoro();
    }

    public void addBreakDuration(int seconds){
        totalBreakDurationSeconds+=seconds;
        addTotalDuration(seconds);
    }

    private void addCompletedPomodoro(){
        completedPomodoros+=1;
    }

    private void addTotalDuration(int seconds){
        totalDurationSeconds+=seconds;
    }
}
