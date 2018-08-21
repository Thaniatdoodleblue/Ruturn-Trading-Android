package com.returntrader.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.EggPresenter;
import com.returntrader.presenter.ipresenter.IEggPresenter;
import com.returntrader.view.iview.IEggView;
import com.returntrader.view.iview.IHomeView;

/**
 * Created by moorthy on 9/15/2017.
 */

public class EggFragment extends BaseFragment implements PinNumberFragment.AuthenticationListener, IEggView {
    private FragmentManager mFragmentManager = null;
    public IEggPresenter iEggPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_egg, container, false);
        iEggPresenter = new EggPresenter(this);
        iEggPresenter.onCreatePresenter(getArguments());
        return rootView;
    }

    @Override
    public void setFirstEnteredPin(String pinValue) {

    }

    @Override
    public void loadFragment(int type) {
        try {
            if (mFragmentManager == null) {
                mFragmentManager = getChildFragmentManager();
            }

            switch (type) {
                case Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN:
                    Fragment fragment = mFragmentManager.findFragmentByTag("pin");
                    PinNumberFragment pinNumberFragment = iEggPresenter.getPinEnterFragment();
                    if (fragment == null) {
                        Log.d(TAG, "add pin fragment if not exist");
                        mFragmentManager
                                .beginTransaction()
                                .add(R.id.container_egg, pinNumberFragment, "pin")
                                .commit();
                    } else {
                        Log.d(TAG, "show pin fragment");
                        Fragment eggFragment = mFragmentManager.findFragmentByTag("egg");
                        if (eggFragment != null) {
                            Log.d(TAG, "show pin fragment and hide egg fragment");
                            mFragmentManager
                                    .beginTransaction()
                                    .hide(eggFragment)
                                    .show(fragment).commit();
                            iEggPresenter.refreshPinFragment();
                        } else {
                            Log.d(TAG, "show pin fragment without hiding egg fragment ");
                            mFragmentManager
                                    .beginTransaction()
                                    .show(fragment).commit();
                            iEggPresenter.refreshPinFragment();
                        }
                    }
                    break;
                case Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS:
                    iEggPresenter.setPinAuthenticationRequired(false);
                    getCodeSnippet().hideKeyboard(getActivity());
                    ((IHomeView) getActivity()).hideTopBannerCardView(View.VISIBLE);
                    Fragment eggFragment = mFragmentManager.findFragmentByTag("egg");
                    if (eggFragment == null) {
                        Log.d(TAG, "add egg fragment if not exist");
                        EggDetailFragment eggDetailFragment = iEggPresenter.getEggDetailFragment();
                        mFragmentManager
                                .beginTransaction()
                                .add(R.id.container_egg, eggDetailFragment, "egg")
                                .commitNow();
                    } else {
                        Log.d(TAG, "show egg fragment");
                        Fragment pinFragment = mFragmentManager.findFragmentByTag("pin");
                        if (pinFragment != null) {
                            Log.d(TAG, "show egg fragment and hide pin fragment");
                            mFragmentManager.beginTransaction()
                                    .hide(pinFragment)
                                    .show(eggFragment).commit();
                            iEggPresenter.refreshEggFragment();
                        } else {
                            Log.d(TAG, "show egg fragment without hiding pin fragment ");
                            mFragmentManager
                                    .beginTransaction()
                                    .show(eggFragment)
                                    .commit();
                            iEggPresenter.refreshEggFragment();
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PinNumberFragment.AuthenticationListener getAuthenticationListener() {
        return this;
    }

    @Override
    public void loadFromAdapter() {
        iEggPresenter.loadCurrentFragment();
    }

    /***/
    public void updateCalculatedValues() {
        if (iEggPresenter != null) {
            iEggPresenter.refreshEggFragment();
        }
    }
}
