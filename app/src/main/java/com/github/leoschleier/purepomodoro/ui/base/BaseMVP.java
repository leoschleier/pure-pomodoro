package com.github.leoschleier.purepomodoro.ui.base;

public interface BaseMVP {
    interface IBaseModel {}

    interface IBaseView {}

    interface IBasePresenter<V extends IBaseView>{
        void subscribe(V view);

        void unsubscribe();
    }
}
