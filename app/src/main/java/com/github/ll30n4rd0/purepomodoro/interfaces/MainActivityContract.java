package com.github.ll30n4rd0.purepomodoro.interfaces;

import com.github.ll30n4rd0.purepomodoro.model.Pomodoro;

import java.util.HashMap;

public interface MainActivityContract{
    interface IModel extends BaseMVP.IBaseModel {
        int getNumIntervals();

        int getCurrentInterval();

        int getWorkDurationSeconds();

        int getShortBreakDurationSeconds();

        int getLongBreakDurationSeconds();

        Pomodoro.State getCurrentPomodoroState();

        Pomodoro.State nextPomodoroState();
    }

    interface IView extends BaseMVP.IBaseView{
        void setTimerText(String timerTextFormatted);

        void setTimerRunningButtonInterface();

        void setTimerPausedButtonInterface();

        void setTimerStoppedButtonInterface();

        void setTimerSkippedButtonInterface();
    }

    interface IState extends BaseMVP.IBaseState{
        HashMap<String, Object> getStateItems();
    }

    interface IPresenter extends BaseMVP.IBaseStatefulPresenter<IView, IState>{

        void startButtonClicked();

        void pauseButtonClicked();

        void stopButtonClicked();

        void skipButtonClicked();

        void onMainActivityStop();
    }


}
