package com.github.leoschleier.purepomodoro.ui.settings;

import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.data.db.model.PomodoroSetup;
import com.github.leoschleier.purepomodoro.ui.base.BasePresenter;
import com.github.leoschleier.purepomodoro.utils.AppConstants;

import javax.inject.Inject;

public class SettingsPresenter<V extends SettingsActivityContract.ISettingsView> extends BasePresenter<V>
        implements SettingsActivityContract.ISettingsPresenter<V> {

    @Inject
    public SettingsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onResume() {
        PomodoroSetup pomodoroSetup = getDataManager().getCustomOrDefaultSetup();

        getView().setPomodoroSetupText(
                pomodoroSetup.getnIntervals(),
                pomodoroSetup.getWorkDurationMin(),
                pomodoroSetup.getShortBreakDurationMin(),
                pomodoroSetup.getLongBreakDurationMin());
    }

    @Override
    public void onApplyButtonClicked() {

        int nIntervals = getView().getPomodoroIntervals();
        long workDuration = getView().getPomodoroWorkDuration();
        long shortBreakDuration = getView().getPomodoroShortBreakDuration();
        long longBreakDuration = getView().getPomodoroLongBreakDuration();

        PomodoroSetup pomodoroSetup = new PomodoroSetup(null, AppConstants.CUSTOM_SETUP_NAME, nIntervals,
                workDuration, shortBreakDuration, longBreakDuration);

        getDataManager().savePomodoroSetup(pomodoroSetup);

        getView().openMainActivity();
    }

    @Override
    public void onBackPressed() {
        getView().openMainActivity();
    }
}
