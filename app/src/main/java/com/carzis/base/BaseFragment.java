package com.carzis.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.carzis.model.AppError;
import com.carzis.util.ConnectionUtility;
import com.carzis.util.custom.view.LoadingDialog;
import com.carzis.util.custom.view.NoInternetDialog;

/**
 * Created by Alexandr.
 */
public class BaseFragment extends Fragment implements BaseView {

    private final String TAG = BaseFragment.class.getSimpleName();

    private NoInternetDialog noInternetDialog;
    private LoadingDialog loadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        noInternetDialog = new NoInternetDialog(context);
        loadingDialog = new LoadingDialog(context);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }


    @Override
    public void showLoading(boolean load) {
        if (!loadingDialog.isShowing() && load) {
            getView().post(() -> loadingDialog.show(getView()));
        }
        else if (loadingDialog.isShowing() && !load) {
            getView().post(() -> loadingDialog.dismiss());
        }
    }

    @Override
    public void showError(AppError appError) {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (!noInternetDialog.isShowing() && !ConnectionUtility.isConnected(getContext()))
            noInternetDialog.show(getView());
    }
}
