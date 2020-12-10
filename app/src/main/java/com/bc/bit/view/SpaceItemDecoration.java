package com.bc.bit.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2018/8/14.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    int mSpace;
    int mTopAndBottom;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mTopAndBottom;
        outRect.right = mTopAndBottom;
        outRect.bottom = mSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;

        }
    }

    public SpaceItemDecoration(int space, int topBottomSpace) {
        this.mSpace = space;
        this.mTopAndBottom = topBottomSpace;
    }
}
