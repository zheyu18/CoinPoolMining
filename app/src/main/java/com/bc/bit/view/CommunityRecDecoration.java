package com.bc.bit.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CommunityRecDecoration extends RecyclerView.ItemDecoration{

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
            outRect.left = 0;
            outRect.right = 0;
        }
    }

    public CommunityRecDecoration(int space, int topBottomSpace) {
        this.mSpace = space;
        this.mTopAndBottom = topBottomSpace;
    }
}
