package com.returntrader.presenter.ipresenter;

/**
 * Created by Karthikeyan on 04-07-2017
 */

public interface ISearchPresenter extends IPresenter {

    void doFetchApiDataForDay(int a);

    void doFetchTop40Data(int b);

    void onClickClose();

    void onClickSearch(String searchQuery);

    void onClickFilter(int clickedPosition);

    int getFilterItemPosition();

    void onClickRemoveBanner(int itemType);

    void onClickBanner(int itemType);

    void resetBannerAdapter();

    void doDelayPriceUpdate();
}
