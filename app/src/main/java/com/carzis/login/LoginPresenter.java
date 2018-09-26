package com.carzis.login;

import com.carzis.base.Presenter;
import com.carzis.history.HistoryPresenter;
import com.carzis.history.HistoryView;
import com.carzis.repository.remote.ApiUtils;
import com.carzis.repository.remote.CarzisApi;

/**
 * Created by Alexandr
 */
public class LoginPresenter implements Presenter<LoginView> {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginView view;
    private CarzisApi api;

    public LoginPresenter() {}


    public void login(String email, String password) {

    }

    public void sendMailWithPassword(String email) {

    }

    public void createAccount(String email, String password) {

    }


    @Override
    public void attachView(LoginView view) {
        this.view = view;
        api = ApiUtils.getCarzisApi();
    }

    @Override
    public void detachView() {

    }
}
