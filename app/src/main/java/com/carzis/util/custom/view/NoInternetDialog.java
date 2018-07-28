package com.carzis.util.custom.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class NoInternetDialog extends PopupWindow {

    private Context context;
    private View content;

    public NoInternetDialog(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_no_internet, null);

        content = rootView.findViewById(R.id.content);

        content.setOnClickListener(
                view -> dismiss());

        setContentView(rootView);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

    }

    public void show(View anchorView) {
        showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

}
