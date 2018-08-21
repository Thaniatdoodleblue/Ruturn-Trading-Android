package com.returntrader.presenter.ipresenter;

import com.returntrader.view.fragment.EggDetailFragment;
import com.returntrader.view.fragment.FingerPrintAuthFragment;
import com.returntrader.view.fragment.PinNumberFragment;

/**
 * Created by moorthy on 9/15/2017.
 */

public interface IEggPresenter extends IPresenter {

    void loadCurrentFragment();

    void setPinAuthenticationRequired(boolean isAuthenticationRequired);

    PinNumberFragment getPinEnterFragment();

    EggDetailFragment getEggDetailFragment();

    void refreshPinFragment();

    void refreshEggFragment();

}
