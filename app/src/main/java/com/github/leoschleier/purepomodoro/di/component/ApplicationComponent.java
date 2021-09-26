package com.github.leoschleier.purepomodoro.di.component;

import android.app.Application;
import android.content.Context;
import com.github.leoschleier.purepomodoro.PomodoroApp;
import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.di.ApplicationContext;
import com.github.leoschleier.purepomodoro.di.module.ApplicationModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(PomodoroApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
