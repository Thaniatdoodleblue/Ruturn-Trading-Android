package com.returntrader.presenter;

import android.os.Bundle;
import android.util.Log;

import com.returntrader.common.Constants;
import com.returntrader.model.common.ShakeMakeCompanyData;
import com.returntrader.presenter.ipresenter.IShakeMakeSuccessPresenter;
import com.returntrader.view.iview.IShakeMakeSuccessView;
import com.returntrader.view.iview.IView;

public class ShakeMakeSuccessPresenter extends BasePresenter implements IShakeMakeSuccessPresenter {

    private IShakeMakeSuccessView iShakeMakeSuccessView;
    private ShakeMakeCompanyData shakeMakeCompanyData;

    public ShakeMakeSuccessPresenter(IShakeMakeSuccessView iShakeMakeSuccessView) {
        super(iShakeMakeSuccessView);
        this.iShakeMakeSuccessView = iShakeMakeSuccessView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        Log.d(TAG, "onCreatePresenter: ");
        if(bundle != null) {
            shakeMakeCompanyData = bundle.getParcelable(Constants.BundleKey.BUNDLE_SHAKE_MAKE_COMPANY_DATA);
            iShakeMakeSuccessView.setCompanyLogoSize();
            String statusMsg = bundle.getString(Constants.BundleKey.BUNDLE_SHAKE_MAKE_PAYMENT_STATUS);
            iShakeMakeSuccessView.paymentStatus(statusMsg);
        }
    }


    @Override
    public ShakeMakeCompanyData getShakeMakeCompanyData(){
        return shakeMakeCompanyData;
    }
}
