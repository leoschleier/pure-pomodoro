package com.github.leoschleier.purepomodoro.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import com.github.leoschleier.purepomodoro.di.ApplicationContext;
import com.github.leoschleier.purepomodoro.di.PreferenceInfo;
import com.github.leoschleier.purepomodoro.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_TIMER_DURATION_SEC = "PREF_KEY_TIMER_DURATION_SEC";
    private static final String PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC = "PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC";
    private static final String PREF_KEY_TIMER_STOP_TIME_MSEC = "PREF_KEY_TIMER_STOP_TIME_MSEC";
    private static final String PREF_KEY_TIMER_RUNNING = "PREF_KEY_TIMER_RUNNING";
    private static final String PREF_KEY_POMODORO_STATE = "PREF_KEY_POMODORO_STATE";
    private static final String PREF_KEY_INTERVALS_COMPLETED = "PREF_KEY_INTERVALS_COMPLETED";


    private final SharedPreferences prefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context, @PreferenceInfo String prefFileName) {
        this.prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public Long getTimerDurationSec() {
        long duration = prefs.getLong(PREF_KEY_TIMER_DURATION_SEC, AppConstants.LONG_NULL_INDEX);
        return duration == AppConstants.LONG_NULL_INDEX ? null : duration;
    }

    @Override
    public void setTimerDurationSec(Long timerDurationSec) {
        long duration = timerDurationSec == null ? AppConstants.LONG_NULL_INDEX : timerDurationSec;
        prefs.edit().putLong(PREF_KEY_TIMER_DURATION_SEC, duration).apply();
    }

    @Override
    public Long getRemainingTimerRuntimeMSec() {
        long remainingRuntime = prefs.getLong(PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC, AppConstants.LONG_NULL_INDEX);
        return remainingRuntime == AppConstants.LONG_NULL_INDEX ? null : remainingRuntime;
    }

    @Override
    public void setRemainingTimerRuntimeMSec(Long remainingTimerRuntimeMSec) {
        long remainingRuntime = remainingTimerRuntimeMSec == null ? AppConstants.LONG_NULL_INDEX : remainingTimerRuntimeMSec;
        prefs.edit().putLong(PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC, remainingRuntime).apply();
    }

    @Override
    public Long getTimerStopTimeMSec() {
        long stopTime = prefs.getLong(PREF_KEY_TIMER_STOP_TIME_MSEC, AppConstants.LONG_NULL_INDEX);
        return stopTime == AppConstants.LONG_NULL_INDEX ? null : stopTime;
    }

    @Override
    public void setTimerStopTimeMSec(Long timerStopTimeMSec) {
        long stopTime = timerStopTimeMSec == null ? AppConstants.LONG_NULL_INDEX : timerStopTimeMSec;
        prefs.edit().putLong(PREF_KEY_TIMER_STOP_TIME_MSEC, stopTime).apply();
    }

    @Override
    public Boolean getTimerRunning() {
        return prefs.getBoolean(PREF_KEY_TIMER_RUNNING, false);
    }

    @Override
    public void setTimerRunning(Boolean timerRunning) {
        boolean running = timerRunning != null && timerRunning;
        prefs.edit().putBoolean(PREF_KEY_TIMER_RUNNING, running).apply();
    }

    @Override
    public Integer getPomodoroState() {
        int pomodoroState = prefs.getInt(PREF_KEY_POMODORO_STATE, AppConstants.INT_NULL_INDEX);
        return pomodoroState == AppConstants.INT_NULL_INDEX ? null : pomodoroState;
    }

    @Override
    public void setPomodoroState(Integer pomodoroState) {
        int state = pomodoroState == null ? AppConstants.INT_NULL_INDEX : pomodoroState;
        prefs.edit().putInt(PREF_KEY_POMODORO_STATE, state).apply();
    }

    @Override
    public Integer getNIntervalsCompleted() {
        int nIntervalsCompleted = prefs.getInt(PREF_KEY_INTERVALS_COMPLETED, AppConstants.INT_NULL_INDEX);
        return nIntervalsCompleted == AppConstants.INT_NULL_INDEX ? null : nIntervalsCompleted;
    }

    @Override
    public void setNIntervalsCompleted(Integer nIntervalsCompleted) {
        int intervals = nIntervalsCompleted == null ? AppConstants.INT_NULL_INDEX : nIntervalsCompleted;
        prefs.edit().putInt(PREF_KEY_INTERVALS_COMPLETED, intervals).apply();
    }
}
