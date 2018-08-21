package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.UserInfo;

/**
 * Created by moorthy on 8/24/2017.
 */

public interface IEmailEntryPresenter extends IPresenter {

    void onClickSubmit(UserInfo userInfo);

    boolean isNotAboveTeen(String rsaId);
}
