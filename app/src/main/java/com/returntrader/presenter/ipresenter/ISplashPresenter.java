package com.returntrader.presenter.ipresenter;

import android.content.Intent;

/**
 * Created by moorthy on 11/14/2017.
 */

public interface ISplashPresenter extends IPresenter {

    Intent getNextScreenActivity();

    boolean isPartialAuthorizedUser();

    void setDeepLinkingEnabled(boolean isDeepLinkingEnabled);
}
