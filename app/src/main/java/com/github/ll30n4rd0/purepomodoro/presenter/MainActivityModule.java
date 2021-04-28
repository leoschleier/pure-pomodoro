package com.github.ll30n4rd0.purepomodoro.presenter;
import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;
import com.github.ll30n4rd0.purepomodoro.model.Pomodoro;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    public MainActivityContract.IPresenter provideMainActivityPresenter (MainActivityContract.IModel model){
        return new MainActivityPresenter(model);
    }

    @Provides
    public MainActivityContract.IModel provideMainActivityModel (){
        return new Pomodoro();
    }}
