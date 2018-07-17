package com.carzis.main.view;

import com.carzis.base.BaseView;
import com.carzis.model.response.ProfileResponse;

/**
 * Created by Alexandr.
 */
public interface ProfileView extends BaseView {
    void onGetProfile(ProfileResponse profileResponse);
    void onUpdateProfile();
}
