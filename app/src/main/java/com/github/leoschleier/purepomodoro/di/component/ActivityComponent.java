package com.github.leoschleier.purepomodoro.di.component;

import com.github.leoschleier.purepomodoro.di.PerActivity;
import com.github.leoschleier.purepomodoro.di.module.ActivityModule;
import com.github.leoschleier.purepomodoro.ui.main.MainActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
