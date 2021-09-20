package com.github.ll30n4rd0.purepomodoro.presenter;

import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;

import java.util.HashMap;

public class MainActivityState implements MainActivityContract.IState {
    private final HashMap<StateItems, Object> stateItems;

    public MainActivityState(HashMap<StateItems, Object> stateItems) {
        this.stateItems = stateItems;
    }

    @Override
    public HashMap<StateItems, Object> getStateItems() {
        return stateItems;
    }

    public enum StateItems{
        TIME_LEFT_MLLIS,
        DURATION_SECONDS,
        TIMER_RUNNING,
        STOP_TIME_MILLIS
    }
}
