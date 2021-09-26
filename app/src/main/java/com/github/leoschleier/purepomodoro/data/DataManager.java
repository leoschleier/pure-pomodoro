package com.github.leoschleier.purepomodoro.data;

import com.github.leoschleier.purepomodoro.data.prefs.PreferencesHelper;

public interface DataManager extends PreferencesHelper {

    void updateTimerState(long timerDurationSec,
                          long remainingTimerRuntimeMSec,
                          long timerStopTimeMSec,
                          boolean timerRunning);
}
