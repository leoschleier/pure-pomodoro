package com.github.leoschleier.purepomodoro.data;

import com.github.leoschleier.purepomodoro.data.db.DbHelper;
import com.github.leoschleier.purepomodoro.data.prefs.PreferencesHelper;

public interface DataManager extends PreferencesHelper, DbHelper {

    void updateTimerState(long timerDurationSec,
                          long remainingTimerRuntimeMSec,
                          long timerStopTimeMSec,
                          boolean timerRunning,
                          int pomodoroState,
                          int nIntervalsCompleted);
}
