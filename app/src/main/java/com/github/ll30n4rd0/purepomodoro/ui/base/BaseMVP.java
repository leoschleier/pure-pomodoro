package com.github.ll30n4rd0.purepomodoro.ui.base;

public interface BaseMVP {
    interface IBaseModel {}

    interface IBaseView {}

    interface IBasePresenter<V extends IBaseView>{
        void subscribe(V view);

        void unsubscribe();
    }
}
