package com.github.ll30n4rd0.purepomodoro.presenter;

import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;

import java.util.HashMap;

public class MainActivityState implements MainActivityContract.IState {
    private final HashMap<String, Object> stateItems;

    public MainActivityState(HashMap<String, Object> stateItems) {
        this.stateItems = stateItems;
    }

    @Override
    public HashMap<String, Object> getStateItems() {
        return stateItems;
    }
}
