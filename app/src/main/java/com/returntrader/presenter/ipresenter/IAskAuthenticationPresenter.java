package com.returntrader.presenter.ipresenter;

import com.returntrader.view.fragment.PinNumberFragment;

/**
 * Created by moorthy on 9/20/2017.
 */

public interface IAskAuthenticationPresenter extends IPresenter {

    PinNumberFragment getAuthPinEntryFragment();

    void setAuthPinRequired(boolean isRequired);
}
