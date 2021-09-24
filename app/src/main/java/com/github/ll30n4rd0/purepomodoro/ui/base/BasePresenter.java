package com.github.ll30n4rd0.purepomodoro.ui.base;

import com.github.ll30n4rd0.purepomodoro.data.DataManager;

public class BasePresenter<V extends BaseMVP.IBaseView> implements BaseMVP.IBasePresenter<V> {

    private final DataManager dataManager;

    private V view;

    public BasePresenter(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void subscribe(V view) {
        this.view = view;
    }

    @Override
    public void unsubscribe() {
        this.view = null;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public V getView(){
        return view;
    }
}
