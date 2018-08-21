package com.returntrader.presenter.ipresenter;

import com.returntrader.view.fragment.PinNumberFragment;

/**
 * Created by moorthy on 9/14/2017.
 */

public interface IAuthenticationPresenter extends IPresenter {

    void setFirstEnteredPin(String value);

    String getFirstEnteredPin();

    PinNumberFragment getNewPinEntryFragment();

    PinNumberFragment getConfirmPinEntryFragment();

    PinNumberFragment getAuthenticateFragment();

    PinNumberFragment getLoginAuthFragment();

    void updateCurrentFragment(int currentPage);

    void clearPin();

    int authenticationFrom();

    void setPinAuthenticationRequired(boolean isAuthenticationRequired);

    void verifyPinAndLogin();

    void updatePasscode();
}
