package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.presenter.ipresenter.IFingerPrintPresenter;
import com.returntrader.view.iview.IFingerPrintView;

/**
 * Created by moorthy on 11/13/2017.
 */

public class FingerPrintPresenter extends BasePresenter implements IFingerPrintPresenter {

    private IFingerPrintView iFingerPrintView;
    private int entryPoint;
    private AppConfigurationManager mAppConfigurationManager;
    private Bundle bundle;

    public FingerPrintPresenter(IFingerPrintView iView) {
        super(iView);
        this.iFingerPrintView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            this.bundle = bundle;
            int from = bundle.getInt(Constants.BundleKey.BUNDLE_FINGER_PRINT_NAVIGATION_FROM);
            setEntryPoint(from);
        }
    }


    private void setEntryPoint(int entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Override
    public int getEntryPoint() {
        return entryPoint;
    }

    @Override
    public void setFingerPrintPinEnabled(boolean isEnabled) {
        mAppConfigurationManager.setFingerPrintEnabledStatus(isEnabled);
    }

    @Override
    public Bundle getBundleData() {
        return bundle;
    }
}
