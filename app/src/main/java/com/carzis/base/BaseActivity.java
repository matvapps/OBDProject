package com.carzis.base;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.carzis.model.AppError;
import com.carzis.util.ConnectionUtility;
import com.carzis.util.custom.view.LoadingDialog;
import com.carzis.util.custom.view.NoInternetDialog;

/**
 * Created by Alexandr.
 */
public class BaseActivity extends AppCompatActivity implements BaseView{

    private NoInternetDialog noInternetDialog;
    private LoadingDialog loadingDialog;
    private ViewGroup viewGroup;

    @Override
    protected void onResume() {
        super.onResume();

        noInternetDialog = new NoInternetDialog(this);
        loadingDialog = new LoadingDialog(this);
        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void showLoading(boolean load) {
        if (!loadingDialog.isShowing() && load)
            loadingDialog.show(viewGroup);
        else if (loadingDialog.isShowing() && !load)
            loadingDialog.dismiss();
    }

    @Override
    public void showError(AppError appError) {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        if (!noInternetDialog.isShowing() && !ConnectionUtility.isConnected(BaseActivity.this))
            noInternetDialog.show(viewGroup);
    }
}
