package com.github.ll30n4rd0.purepomodoro.data;

import android.content.Context;
import com.github.ll30n4rd0.purepomodoro.data.prefs.PreferencesHelper;


public class AppDataManager implements DataManager {

    private final Context context;
    private final PreferencesHelper preferencesHelper;

    public AppDataManager(Context context, PreferencesHelper preferencesHelper){
        this.context = context;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void updateTimerState(long timerDurationSec,
                                 long remainingTimerRuntimeMSec,
                                 long timerStopTimeMSec,
                                 boolean timerRunning) {
        setTimerDurationSec(timerDurationSec);
        setRemainingTimerRuntimeMSec(remainingTimerRuntimeMSec);
        setTimerStopTimeMSec(timerStopTimeMSec);
        setTimerRunning(timerRunning);
    }

    @Override
    public Long getTimerDurationSec() {
        return preferencesHelper.getTimerDurationSec();
    }

    @Override
    public void setTimerDurationSec(Long timerDurationSec) {
        preferencesHelper.setTimerDurationSec(timerDurationSec);
    }

    @Override
    public Long getRemainingTimerRuntimeMSec() {
        return preferencesHelper.getRemainingTimerRuntimeMSec();
    }

    @Override
    public void setRemainingTimerRuntimeMSec(Long remainingTimerRuntimeMSec) {
        preferencesHelper.setRemainingTimerRuntimeMSec(remainingTimerRuntimeMSec);
    }

    @Override
    public Long getTimerStopTimeMSec() {
        return preferencesHelper.getTimerStopTimeMSec();
    }

    @Override
    public void setTimerStopTimeMSec(Long timerStopTimeMSec) {
        preferencesHelper.setTimerStopTimeMSec(timerStopTimeMSec);
    }

    @Override
    public Boolean getTimerRunning() {
        return preferencesHelper.getTimerRunning();
    }

    @Override
    public void setTimerRunning(Boolean timerRunning) {
        preferencesHelper.setTimerRunning(timerRunning);
    }
}
