package com.github.ll30n4rd0.purepomodoro.root;

import android.app.Application;
import com.github.ll30n4rd0.purepomodoro.presenter.MainActivityModule;

public class App extends Application {
    private AppComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .mainActivityModule(new MainActivityModule())
                .build();
    }
    public AppComponent getComponent() {
        return component;
    }
}
