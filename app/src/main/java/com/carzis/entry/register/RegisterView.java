package com.carzis.entry.register;

import com.carzis.base.BaseView;
import com.carzis.model.User;

public interface RegisterView extends BaseView {
    void onRegister();
    void onConfirmRegister(User user, String token);
    void onPhoneExisted(String phone);
}
