package com.carzis.util.custom.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class LoadingDialog extends PopupWindow {

    private Context context;
    private View content;

    public LoadingDialog(Context context) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.fragment_loading, null);

        content = rootView.findViewById(R.id.content);

        content.setOnClickListener(
                view -> dismiss());

        setContentView(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setTouchable(true);
    }

    public void show(View anchorView) {
        showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

}
