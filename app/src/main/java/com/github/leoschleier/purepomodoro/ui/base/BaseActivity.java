package com.github.leoschleier.purepomodoro.ui.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.leoschleier.purepomodoro.PomodoroApp;
import com.github.leoschleier.purepomodoro.di.component.ActivityComponent;
import com.github.leoschleier.purepomodoro.di.component.DaggerActivityComponent;
import com.github.leoschleier.purepomodoro.di.module.ActivityModule;

public class BaseActivity extends AppCompatActivity implements BaseMVP.IBaseView {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent
                .builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((PomodoroApp) getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent(){
        return activityComponent;
    }


}
