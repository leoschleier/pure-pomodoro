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

    interface IMainView extends BaseMVP.IBaseView{
        void setTimerText(String timerTextFormatted);

        void setTimerRunningButtonInterface();

        void setTimerPausedButtonInterface();

        void setTimerStoppedButtonInterface();
    }

    interface IMainState extends BaseMVP.IBaseState{
        HashMap<StateItems, Object> getStateItems();

        enum StateItems{
            TIME_LEFT_MILLIS,
            DURATION_SECONDS,
            TIMER_RUNNING,
            STOP_TIME_MILLIS
        }
    }

    interface IMainPresenter extends BaseMVP.IBaseStatefulPresenter<IMainView, IMainState>{

        void startButtonClicked();

        void pauseButtonClicked();

        void continueButtonClicked();

        void stopButtonClicked();

        void onMainActivityStop();
    }


}
