package com.returntrader.view.iview;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.returntrader.adapter.BannerPagerAdapter;
import com.returntrader.adapter.SearchHomeAdapter;

/**
 * Created by Karthikeyan on 04-07-2017
 */

public interface ISearchView extends IView {

    void navigateToActivity(Bundle data);

    void navigateToBanner(Class destination, Bundle bundle, int requestCode);

    void setSearchHomeAdapter(SearchHomeAdapter mailListAdapter);

    void setProgressBarVisibility(int visibility);

    void setBannerViewPagerAdapter(BannerPagerAdapter bannerViewPagerAdapter);

    FragmentManager getChildViewFragment();

    void setBannerVisibility(int visibility);

    boolean isListPositionAtTop();

    void scrollToTop();

    void navigateToFullRegistration(Bundle bundle);

    void setEmptyText(String message, int count);

    void startDelayPriceUpdateService();
}






