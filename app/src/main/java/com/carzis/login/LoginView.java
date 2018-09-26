package com.carzis.login;

import com.carzis.base.BaseView;

/**
 * Created by Alexandr
 */
public interface LoginView extends BaseView {
    void onLogin();
    void onSendMailForRestorePassword();
    void onCreateAccount();
}
