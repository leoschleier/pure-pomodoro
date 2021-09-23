package com.github.ll30n4rd0.purepomodoro.data;

import android.content.Context;
import com.github.ll30n4rd0.purepomodoro.data.prefs.IPreferencesHelper;

public class AppDataManager implements IDataManager{

    private final Context context;
    private final IPreferencesHelper preferencesHelper;

    public AppDataManager(Context context, IPreferencesHelper preferencesHelper){
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
    public long getTimerDurationSec() {
        return preferencesHelper.getTimerDurationSec();
    }

    @Override
    public void setTimerDurationSec(long timerDurationSec) {
        preferencesHelper.setTimerDurationSec(timerDurationSec);
    }

    @Override
    public long getRemainingTimerRuntimeMSec() {
        return preferencesHelper.getRemainingTimerRuntimeMSec();
    }

    @Override
    public void setRemainingTimerRuntimeMSec(long remainingTimerRuntimeMSec) {
        preferencesHelper.setRemainingTimerRuntimeMSec(remainingTimerRuntimeMSec);
    }

    @Override
    public long getTimerStopTimeMSec() {
        return preferencesHelper.getTimerStopTimeMSec();
    }

    @Override
    public void setTimerStopTimeMSec(long timerStopTimeMSec) {
        preferencesHelper.setTimerStopTimeMSec(timerStopTimeMSec);
    }

    @Override
    public boolean getTimerRunning() {
        return preferencesHelper.getTimerRunning();
    }

    @Override
    public void setTimerRunning(boolean timerRunning) {
        preferencesHelper.setTimerRunning(timerRunning);
    }
}
