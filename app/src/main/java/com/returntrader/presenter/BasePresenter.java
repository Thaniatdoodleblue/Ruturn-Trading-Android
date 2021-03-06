package com.returntrader.presenter;

import android.content.Intent;

import com.returntrader.presenter.ipresenter.IPresenter;
import com.returntrader.view.iview.IView;


abstract class BasePresenter implements IPresenter {

    protected String TAG = getClass().getSimpleName();

    private IView iView;

    public BasePresenter(IView iView) {
        this.iView = iView;
        iView.bindPresenter(this);
    }

    @Override
    public void onStartPresenter() {

    }

    @Override
    public void onStopPresenter() {

    }

    @Override
    public void onPausePresenter() {

    }

    @Override
    public void onResumePresenter() {

    }

    @Override
    public void onDestroyPresenter() {

    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {

    }
}
