package com.github.ll30n4rd0.purepomodoro.ui.main;

import java.util.HashMap;

public class MainActivityMainState implements MainActivityContract.IMainState {
    private final HashMap<StateItems, Object> stateItems;

    public MainActivityMainState(HashMap<StateItems, Object> stateItems) {
        this.stateItems = stateItems;
    }

    @Override
    public HashMap<StateItems, Object> getStateItems() {
        return stateItems;
    }

}
