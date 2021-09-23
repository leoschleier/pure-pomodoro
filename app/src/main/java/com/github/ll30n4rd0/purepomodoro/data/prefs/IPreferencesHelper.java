package com.github.ll30n4rd0.purepomodoro.data.prefs;

public interface IPreferencesHelper {

    long getTimerDurationSec();

    void setTimerDurationSec(long timerDurationSec);

    long getRemainingTimerRuntimeMSec();

    void setRemainingTimerRuntimeMSec(long remainingTimerRuntimeMSec);

    long getTimerStopTimeMSec();

    void setTimerStopTimeMSec(long timerStopTimeMSec);

    boolean getTimerRunning();

    void setTimerRunning(boolean timerRunning);

}
