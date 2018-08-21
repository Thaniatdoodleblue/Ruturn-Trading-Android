package com.returntrader.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.returntrader.common.Constants;
import com.returntrader.model.common.BannerItem;
import com.returntrader.view.fragment.BannerFragment;

import java.util.List;

public class BannerPagerAdapter extends FragmentStatePagerAdapter {
    private List<BannerItem> mBannerFragment;

    public BannerPagerAdapter(FragmentManager fm, List<BannerItem> bannerItems) {
        super(fm);
        this.mBannerFragment = bannerItems;
    }

    @Override
    public Fragment getItem(int position) {
        BannerFragment bannerFragment = new BannerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKey.BUNDLE_BANNER_ITEM, mBannerFragment.get(position));
        bannerFragment.setArguments(bundle);
        return bannerFragment;
    }

    @Override
    public int getCount() {
        return mBannerFragment.size();
    }

}
