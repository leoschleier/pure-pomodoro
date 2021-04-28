package com.github.ll30n4rd0.purepomodoro.root;

import com.github.ll30n4rd0.purepomodoro.presenter.MainActivityModule;
import com.github.ll30n4rd0.purepomodoro.root.AppModule;
import com.github.ll30n4rd0.purepomodoro.view.MainActivity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, MainActivityModule.class})
public interface AppComponent {
    void inject(MainActivity target);
}
