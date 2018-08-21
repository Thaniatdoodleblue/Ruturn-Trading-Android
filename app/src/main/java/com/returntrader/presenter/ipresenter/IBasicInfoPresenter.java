package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.UserInfo;

/**
 * Created by moorthy on 8/24/2017.
 */

public interface IBasicInfoPresenter extends IPresenter {

    void onClickSubmit(UserInfo userInfo, int selectedItemPosition);
}
