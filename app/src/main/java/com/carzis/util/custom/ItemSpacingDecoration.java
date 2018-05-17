package com.carzis.util.custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Alexandr
 */

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public ItemSpacingDecoration(int mItemOffset) {
        this.mItemOffset = mItemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
