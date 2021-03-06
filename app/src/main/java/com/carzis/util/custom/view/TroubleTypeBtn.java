package com.carzis.util.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class TroubleTypeBtn extends LinearLayout {

    private TextView textView;
    private ImageView imageView;
    private ImageView background;

    private String textStr;
    private boolean useBtnImage;

    private boolean enabled;

    public TroubleTypeBtn(Context context) {
        this(context, null);
    }
    public TroubleTypeBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TroubleTypeBtn(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.btn_trouble_type, this, true);

        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        background = findViewById(R.id.background);

        textView.setAllCaps(true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TroubleTypeBtn);
        textStr = a.getString(R.styleable.TroubleTypeBtn_btnText);
        useBtnImage = a.getBoolean(R.styleable.TroubleTypeBtn_useBtnImage, true);

        if (useBtnImage)
            imageView.setVisibility(VISIBLE);
        else
            imageView.setVisibility(GONE);

        final Drawable drawable = a.getDrawable(R.styleable.TroubleTypeBtn_btnImage);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }

        if (!TextUtils.isEmpty(textStr)) {
            textView.setText(textStr);
        }

        setDefaults();

        a.recycle();

    }

    public void setDefaults() {
        enabled = false;
        background
                .setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.light_gray_field_background));

        imageView.setAlpha(0.6f);
        textView.setTextColor(Color.GRAY);

    }

    public void setSelected(boolean selected) {
        if (selected) {
            enabled = true;
            background.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.button_blue_rect_selector));
            imageView.setAlpha(1f);
            textView.setTextColor(Color.WHITE);
        } else {
            setDefaults();
        }
    }

    public boolean isSelected() {
        return enabled;
    }

}
