package com.github.leoschleier.purepomodoro.ui.settings;

import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.ui.base.BasePresenter;

import javax.inject.Inject;

public class SettingsPresenter<V extends SettingsActivityContract.ISettingsView> extends BasePresenter<V>
        implements SettingsActivityContract.ISettingsPresenter<V> {

    @Inject
    public SettingsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onBackPressed() {
        SettingsActivityContract.ISettingsView view = getView();
        view.openMainActivity();
    }
}
