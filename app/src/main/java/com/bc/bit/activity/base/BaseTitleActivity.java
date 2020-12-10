package com.bc.bit.activity.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.bit.R;


public class BaseTitleActivity extends BaseCommonActivity{

    // 解析
    private LayoutInflater inflater ;
    // 根容器
    private FrameLayout rootLayout ;
    // 标题
    private TextView titleTxt ;
    // back
    protected TextView txtLeft ;
    private TextView txtRight ;
    protected RelativeLayout layout_topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        View view   = inflater.inflate(R.layout.ac_with_topbar_layout,null);
        rootLayout  = (FrameLayout) view.findViewById(R.id.root_container);
        titleTxt    = (TextView) view.findViewById(R.id.top_center_txt);
        txtLeft     = (TextView) view.findViewById(R.id.bnt_back);
        txtRight    = (TextView) view.findViewById(R.id.top_right_txt);
        layout_topbar = (RelativeLayout) view.findViewById(R.id.layout_topbar);
        txtLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setContentView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = inflater.inflate(layoutResID,null);
        rootLayout.addView(view);
    }

    public void setTitleText(String title){
        titleTxt.setText(title);
    }

    public void setLeftButtonIcon(int rid){
        txtLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(rid),null,null,null);
    }

    public void setLeftText(String txt){
        txtLeft.setText(txt);
    }
    public void setRightText(String txt){
        txtRight.setText(txt);
    }
    public void setRightText(String txt,View.OnClickListener click){
        txtRight.setText(txt);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(click);
    }

    public void setRightTextOnClick(View.OnClickListener click){
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(click);
    }


    public void enableRightButton(String txt,View.OnClickListener click){
        txtRight.setText(txt);
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setOnClickListener(click);
    }

    public void disableRightButton(){
        txtRight.setVisibility(View.INVISIBLE);
        txtRight.setOnClickListener(null);
    }

    public void disableLeftButton(){
        txtLeft.setVisibility(View.INVISIBLE);
        txtLeft.setOnClickListener(null);
    }

    public void setTitleBarGone(){
        layout_topbar.setVisibility(View.GONE);
    }
}
