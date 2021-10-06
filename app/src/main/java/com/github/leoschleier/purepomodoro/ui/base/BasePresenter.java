package com.github.leoschleier.purepomodoro.ui.base;

import com.github.leoschleier.purepomodoro.data.DataManager;

import javax.inject.Inject;

public class BasePresenter<V extends BaseMVP.IBaseView> implements BaseMVP.IBasePresenter<V> {

    private final DataManager dataManager;

    private V view;

    @Inject
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
