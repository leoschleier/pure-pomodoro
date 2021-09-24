package com.github.ll30n4rd0.purepomodoro.ui.main;

import com.github.ll30n4rd0.purepomodoro.data.db.model.Pomodoro;
import com.github.ll30n4rd0.purepomodoro.ui.base.BaseMVP;

import java.util.HashMap;

public interface MainActivityContract{
    interface IMainModel extends BaseMVP.IBaseModel {
        int getNumIntervals();

        int getCurrentInterval();

        int getWorkDurationSeconds();

        int getShortBreakDurationSeconds();

        int getLongBreakDurationSeconds();

        Pomodoro.State getCurrentPomodoroState();

        Pomodoro.State nextPomodoroState();
    }

    interface IMainView extends BaseMVP.IBaseView {
        void setTimerText(String timerTextFormatted);

        void setTimerRunningButtonInterface();

        void setTimerPausedButtonInterface();

        void setTimerStoppedButtonInterface();
    }

    interface IMainPresenter<V extends IMainView> extends BaseMVP.IBasePresenter<V> {

        void onStartButtonClicked();

        void onPauseButtonClicked();

        void onContinueButtonClicked();

        void onStopButtonClicked();

        void onMainActivityResume();

        void onMainActivityPause();
    }


}
