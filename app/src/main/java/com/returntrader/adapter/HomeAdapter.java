package com.returntrader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.returntrader.view.fragment.EggFragment;
import com.returntrader.view.fragment.HomeSearchFragment;
import com.returntrader.view.fragment.SettingsFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {

    private String TAG = getClass().getSimpleName();
    private int mNumOfTabs = 3;
    private HomeSearchFragment mHomeSearchFragment;
    private EggFragment mHomeEggFragment;
    private SettingsFragment mSettingsFragment;

    public HomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (mHomeSearchFragment == null) {
                    mHomeSearchFragment = new HomeSearchFragment();
                }
                return mHomeSearchFragment;
            case 1:
                if (mHomeEggFragment == null) {
                    mHomeEggFragment = new EggFragment();
                }
                return mHomeEggFragment;
            case 2:
                if (mSettingsFragment == null) {
                    mSettingsFragment = new SettingsFragment();
                }
                return mSettingsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public void updateFragment(int position) {
        switch (position) {
            case 0:
                if (mHomeSearchFragment != null) {
                    mHomeSearchFragment.updateBanner();
                }
                break;
            case 1:
                if (mHomeEggFragment != null) {
                    //mHomeEggFragment.loadFragment(Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN);
                    mHomeEggFragment.loadFromAdapter();
                }
                break;
            case 2: // to reset expanded view in settings fragment
                if (mSettingsFragment != null) {
                    mSettingsFragment.resetView();
                }
                break;
        }
    }

    public SettingsFragment getSettingsFragment() {
        return mSettingsFragment;
    }


    public HomeSearchFragment getHomeSearchFragment() {
        return mHomeSearchFragment;
    }

    public void scrollToTop() {
        if (mHomeSearchFragment != null) {
            mHomeSearchFragment.scrollToTop();
        }
    }

    public boolean isTopItemShown() {
        return mHomeSearchFragment != null && mHomeSearchFragment.isListPositionAtTop();
    }
}
