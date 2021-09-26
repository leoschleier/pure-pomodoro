package com.github.leoschleier.purepomodoro.data.prefs;

public interface PreferencesHelper {

    Long getTimerDurationSec();

    void setTimerDurationSec(Long timerDurationSec);

    Long getRemainingTimerRuntimeMSec();

    void setRemainingTimerRuntimeMSec(Long remainingTimerRuntimeMSec);

    Long getTimerStopTimeMSec();

    void setTimerStopTimeMSec(Long timerStopTimeMSec);

    Boolean getTimerRunning();

    void setTimerRunning(Boolean timerRunning);

}
