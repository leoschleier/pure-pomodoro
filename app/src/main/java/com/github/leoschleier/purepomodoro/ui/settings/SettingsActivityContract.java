package com.github.leoschleier.purepomodoro.ui.settings;

import com.github.leoschleier.purepomodoro.ui.base.BaseMVP;

public interface SettingsActivityContract {

    interface ISettingsModel extends BaseMVP.IBaseModel{

    }

    interface ISettingsView extends BaseMVP.IBaseView{

        void openMainActivity();

    }

    interface ISettingsPresenter<V extends ISettingsView> extends BaseMVP.IBasePresenter<V>{

        void onApplyButtonClicked();

        void onBackPressed();

    }

}
