package com.returntrader.view.iview;

import com.returntrader.adapter.CompanyDetailsAdapter;
import com.returntrader.database.CompanyItemInfo;

/**
 * Created by moorthy on 7/20/2017.
 */

public interface ICompanyDetailsView extends IView {

    void setViewPagerAdapter(CompanyDetailsAdapter adapter);

    void setCompanyData(CompanyItemInfo companyData, String fromCompanyData);

    void setFavouriteStatus(boolean isFavourite);

    void postOrderProgress(int isSuccess);

    void doBalanceUpdate();

    void showQuestions();

    String getBuyAmount();

    void setUpTopBar();

    void showHolidayDialog();

    void doBuy();
}

