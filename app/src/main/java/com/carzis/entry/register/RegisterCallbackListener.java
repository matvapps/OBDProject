package com.carzis.entry.register;

/**
 * Created by Alexandr.
 */
public interface RegisterCallbackListener {
    void onInputPhoneFinish(String phone);
    void onLogRegFinish(boolean isRegistration, Integer code);
    void onResendSms();
}
