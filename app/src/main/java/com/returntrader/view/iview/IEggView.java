package com.returntrader.view.iview;

import com.returntrader.view.fragment.PinNumberFragment;

/**
 * Created by moorthy on 9/15/2017.
 */

public interface IEggView extends IView {

    PinNumberFragment.AuthenticationListener getAuthenticationListener();

    void loadFromAdapter();
}
