package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.presenter.ipresenter.ICompanyInfoPresenter;
import com.returntrader.view.iview.ICompanyFragmentView;

/**
 * Created by moorthy on 7/24/2017.
 */

public class CompanyInfoPresenter extends BasePresenter implements ICompanyInfoPresenter {


    private ICompanyFragmentView iCompanyFragmentView;

    public CompanyInfoPresenter(ICompanyFragmentView iView) {
        super(iView);
        this.iCompanyFragmentView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            CompanyItemInfo homeSearchData = bundle.getParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM);
            if (homeSearchData != null) {
                iCompanyFragmentView.setCompanyInfo(homeSearchData);
            }
        }
    }
}
