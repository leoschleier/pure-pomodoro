package com.github.ll30n4rd0.purepomodoro.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import com.github.ll30n4rd0.purepomodoro.utils.AppConstants;

public class AppPreferencesHelper implements IPreferencesHelper {

    private static final String PREF_KEY_TIMER_DURATION_SEC = "PREF_KEY_TIMER_DURATION_SEC";
    private static final String PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC = "PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC";
    private static final String PREF_KEY_TIMER_STOP_TIME_MSEC = "PREF_KEY_TIMER_STOP_TIME_MSEC";
    private static final String PREF_KEY_TIMER_RUNNING = "PREF_KEY_TIMER_RUNNING";

    private final SharedPreferences prefs;

    public AppPreferencesHelper(Context context, String prefFileName) {
        this.prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public long getTimerDurationSec() {
        long duration = prefs.getLong(PREF_KEY_TIMER_DURATION_SEC, AppConstants.NULL_INDEX);
        return duration == AppConstants.NULL_INDEX ? null : duration;
    }

    @Override
    public void setTimerDurationSec(long timerDurationSec) {
        long duration = timerDurationSec == AppConstants.NULL_INDEX ? null : timerDurationSec;
        prefs.edit().putLong(PREF_KEY_TIMER_DURATION_SEC, duration).apply();
    }

    @Override
    public long getRemainingTimerRuntimeMSec() {
        long remainingRuntime = prefs.getLong(PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC, AppConstants.NULL_INDEX);
        return remainingRuntime == AppConstants.NULL_INDEX ? null : remainingRuntime;
    }

    @Override
    public void setRemainingTimerRuntimeMSec(long remainingTimerRuntimeMSec) {
        long remainingRuntime = remainingTimerRuntimeMSec == AppConstants.NULL_INDEX ? null : remainingTimerRuntimeMSec;
        prefs.edit().putLong(PREF_KEY_REMAINING_TIMER_RUNTIME_MSEC, remainingRuntime).apply();
    }

    @Override
    public long getTimerStopTimeMSec() {
        long stopTime = prefs.getLong(PREF_KEY_TIMER_STOP_TIME_MSEC, AppConstants.NULL_INDEX);
        return stopTime == AppConstants.NULL_INDEX ? null : stopTime;
    }

    @Override
    public void setTimerStopTimeMSec(long timerStopTimeMSec) {
        long stopTime = timerStopTimeMSec == AppConstants.NULL_INDEX ? null : timerStopTimeMSec;
        prefs.edit().putLong(PREF_KEY_TIMER_STOP_TIME_MSEC, stopTime).apply();
    }

    @Override
    public boolean getTimerRunning() {
        return prefs.getBoolean(PREF_KEY_TIMER_RUNNING, false);
    }

    @Override
    public void setTimerRunning(boolean timerRunning) {
        prefs.edit().putBoolean(PREF_KEY_TIMER_RUNNING, timerRunning).apply();
    }
}
