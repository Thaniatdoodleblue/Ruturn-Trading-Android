package com.returntrader.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.returntrader.view.fragment.CompanyFragment;
import com.returntrader.view.fragment.FundamentalsFragment;
import com.returntrader.view.fragment.GraphFragment;
import com.returntrader.view.fragment.NewsFragment;

/**
 * Created by Karthikeyan on 02-06-2017
 */

public class CompanyDetailsAdapter extends FragmentStatePagerAdapter {

    private Bundle mBundle;
    private String[] mTabName = new String[]{"Graph", "Company", "News", "Fundamentals"};

    public CompanyDetailsAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        this.mBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GraphFragment graphFragment = new GraphFragment();
                graphFragment.setArguments(mBundle);
                return graphFragment;
            case 1:
                CompanyFragment companyFragment = new CompanyFragment();
                companyFragment.setArguments(mBundle);
                return companyFragment;
            case 2:
                NewsFragment newsFragment = new NewsFragment();
                newsFragment.setArguments(mBundle);
                return newsFragment;
            case 3:
                FundamentalsFragment fundamentalsFragment = new FundamentalsFragment();
                fundamentalsFragment.setArguments(mBundle);
                return fundamentalsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

   /* @Override
    public CharSequence getPageTitle(int position) {
        return mTabName[position];
    }*/
}
