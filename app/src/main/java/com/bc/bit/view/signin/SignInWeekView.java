package com.bc.bit.view.signin;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;

public class SignInWeekView extends WeekView {
    private int mRadius;
    private Paint mRingPaint = new Paint();
    private int mRingRadius;

    private static final String TAG = "SignInWeekView";
    /**
     * 不可用画笔
     */
    private Paint mDisablePaint = new Paint();

    private int mH;

    public SignInWeekView(Context context) {
        super(context);
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mSchemePaint.getColor());
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(dipToPx(context, 1));
        setLayerType(View.LAYER_TYPE_SOFTWARE, mRingPaint);
        mRingPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));

        mDisablePaint.setColor(0xFFffffff);
        mDisablePaint.setAntiAlias(true);
        mDisablePaint.setStrokeWidth(dipToPx(context,2));
        mDisablePaint.setFakeBoldText(true);

        mH = dipToPx(context, 18);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 6 * 2;
        mRingRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSelectTextPaint.setTextSize(dipToPx(getContext(),17));
    }
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        canvas.drawCircle(cx, cy, mRingRadius, mRingPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine - dipToPx(getContext(), 1);
        int cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(calendar.isCurrentDay() ? "今" : "选",
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(calendar.isCurrentDay() ? "今" : String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }

        //日期是否可用？拦截
        if (onCalendarIntercept(calendar)) {
            canvas.drawLine(x + mH, mH, x + mItemWidth - mH, mItemHeight - mH, mDisablePaint);
        }
    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
