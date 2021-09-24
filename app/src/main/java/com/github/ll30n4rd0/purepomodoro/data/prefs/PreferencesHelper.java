package com.github.ll30n4rd0.purepomodoro.data.prefs;

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
