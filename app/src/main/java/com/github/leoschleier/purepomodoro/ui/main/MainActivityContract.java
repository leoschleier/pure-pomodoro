package com.github.leoschleier.purepomodoro.ui.main;

import com.github.leoschleier.purepomodoro.data.db.model.Pomodoro;
import com.github.leoschleier.purepomodoro.ui.base.BaseMVP;

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

        void onSettingsItemClicked();

        void onMainActivityResume();

        void onMainActivityPause();
    }


}
