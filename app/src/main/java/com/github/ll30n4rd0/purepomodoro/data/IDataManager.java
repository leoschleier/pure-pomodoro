package com.github.ll30n4rd0.purepomodoro.data;

import com.github.ll30n4rd0.purepomodoro.data.prefs.IPreferencesHelper;

public interface IDataManager extends IPreferencesHelper {

    void updateTimerState(long timerDurationSec,
                          long remainingTimerRuntimeMSec,
                          long timerStopTimeMSec,
                          boolean timerRunning);
}
