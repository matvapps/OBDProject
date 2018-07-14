package com.carzis.base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();
}
