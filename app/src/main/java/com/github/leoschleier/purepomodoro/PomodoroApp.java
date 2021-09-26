package com.github.leoschleier.purepomodoro;

import android.app.Application;
import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.di.component.ApplicationComponent;
import com.github.leoschleier.purepomodoro.di.component.DaggerApplicationComponent;
import com.github.leoschleier.purepomodoro.di.module.ApplicationModule;

import javax.inject.Inject;

public class PomodoroApp extends Application {

    @Inject
    DataManager dataManager;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent){
        this.applicationComponent = applicationComponent;
    }
}
