package com.github.ll30n4rd0.purepomodoro.data;

import com.github.ll30n4rd0.purepomodoro.data.prefs.PreferencesHelper;

public interface DataManager extends PreferencesHelper {

    void updateTimerState(long timerDurationSec,
                          long remainingTimerRuntimeMSec,
                          long timerStopTimeMSec,
                          boolean timerRunning);
}
