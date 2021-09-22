package com.github.ll30n4rd0.purepomodoro.ui.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface BaseMVP {
    interface IBaseModel{}

    interface IBaseView{}

    interface IBasePresenter<V extends IBaseView>{
        void subscribe(@NonNull V view);

        void unsubscribe();
    }

    interface IBaseState{}

    interface IBaseStatefulPresenter<V extends IBaseView, S extends IBaseState> extends IBasePresenter<V>{
        void subscribe(@NonNull V view, @Nullable S state);
        @NonNull S getState();
    }

}
