package com.returntrader.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.presenter.CompanyInfoPresenter;
import com.returntrader.presenter.ipresenter.ICompanyInfoPresenter;
import com.returntrader.view.iview.ICompanyFragmentView;
import com.returntrader.view.widget.JustifiedTextView;

public class CompanyFragment extends BaseFragment implements ICompanyFragmentView {

    public CompanyFragment() {

    }

    private ICompanyInfoPresenter iCompanyInfoPresenter;
    private JustifiedTextView vTvCompanyInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_company, container, false);
        bindFragment(rootView);
        iCompanyInfoPresenter = new CompanyInfoPresenter(this);
        iCompanyInfoPresenter.onCreatePresenter(getArguments());
        return rootView;
    }

    private void bindFragment(View rootView) {
        vTvCompanyInfo = rootView.findViewById(R.id.tv_company_info);
    }

    @Override
    public void setCompanyInfo(CompanyItemInfo companyInfo) {
        if (!(TextUtils.isEmpty(companyInfo.getCompanyInfo()))) {
            vTvCompanyInfo.setText(companyInfo.getCompanyInfo());
        }
    }
}
