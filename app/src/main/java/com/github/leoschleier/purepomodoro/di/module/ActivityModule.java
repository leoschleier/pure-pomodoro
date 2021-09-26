package com.github.leoschleier.purepomodoro.di.module;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.github.leoschleier.purepomodoro.di.ActivityContext;
import com.github.leoschleier.purepomodoro.di.PerActivity;
import com.github.leoschleier.purepomodoro.ui.main.MainActivityContract;
import com.github.leoschleier.purepomodoro.ui.main.MainPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity(){
        return activity;
    }

    @Provides
    @PerActivity
    MainActivityContract.IMainPresenter<MainActivityContract.IMainView> provideMainPresenter(
            MainPresenter<MainActivityContract.IMainView> presenter) {
        return presenter;
    }
}
