package com.carzis.entry.auth;

import com.carzis.base.BaseView;
import com.carzis.model.User;

public interface AuthView extends BaseView {
    void onAuth(User user, String token);
}
