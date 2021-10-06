package com.github.leoschleier.purepomodoro.ui.settings;

import com.github.leoschleier.purepomodoro.ui.base.BaseMVP;

public interface SettingsActivityContract {

    interface ISettingsModel extends BaseMVP.IBaseModel{

    }

    interface ISettingsView extends BaseMVP.IBaseView{

        void openMainActivity();

        void setPomodoroSetupText(int nIntervals, long workDuration, long shortBreakDuration, long longBreakDuration);

        int getPomodoroIntervals();

        long getPomodoroWorkDuration();

        long getPomodoroShortBreakDuration();

        long getPomodoroLongBreakDuration();

    }

    interface ISettingsPresenter<V extends ISettingsView> extends BaseMVP.IBasePresenter<V>{

        void onResume();

        void onApplyButtonClicked();

        void onBackPressed();

    }

}
