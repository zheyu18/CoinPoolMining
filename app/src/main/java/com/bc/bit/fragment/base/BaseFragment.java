package com.bc.bit.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 所有Fragment通用父类
 */
public abstract class BaseFragment extends Fragment {

    private Boolean isInitData = false; //标志位,判断数据是否初始化
    private Boolean isVisibleToUser = false; //标志位,判断fragment是否可见
    private Boolean isPrepareView = false; //标志位,判断view已经加载完成 避免空指针操作


    /**
     * 找控件
     */
    protected void initViews(){

    }

    /**
     * 设置数据
     */
    protected void initDatum(){

    }

    /**
     * 绑定监听器
     */
    protected void initListeners() {

    }

    /**
     * 返回要显示的View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutView(inflater,container,savedInstanceState);
    }

    /**
     * 返回View
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * View创建完毕了
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
        isPrepareView=true;//此时view已经加载完成，设置其为true
    }

    /**
     * 找控件
     * @param id
     * @param <T>
     * @return
     */
    public final <T extends View> T findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }


    /**
     * 设置Fragment target，由子类实现
     */
    protected boolean setFragmentTarget(){

        return false;
    }


    /**
     * 懒加载方法
     */
    public void lazyInitData(){
        if(setFragmentTarget()){
            if(!isInitData && isVisibleToUser && isPrepareView){//如果数据还没有被加载过，并且fragment已经可见，view已经加载完成
                initDatum();//加载数据
                isInitData=true;//是否已经加载数据标志重新赋值为true
            }
        }else {
            if(!isInitData && isVisibleToUser && isPrepareView){//如果数据还没有被加载过，并且fragment已经可见，view已经加载完成
                initDatum();//加载数据
                isInitData=true;//是否已经加载数据标志重新赋值为true
            }else if (!isInitData && getParentFragment()==null && isPrepareView ){
                initDatum();
                isInitData=true;
            }
        }
    }


    /**
     * Fragment显示隐藏监听
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            lazyInitData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;//将fragment是否可见值赋给标志isVisibleToUser
        lazyInitData();//加载懒加载
    }

    /**
     * fragment生命周期中onViewCreated之后的方法 在这里调用一次懒加载 避免第一次可见不加载数据
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitData();
    }

}
