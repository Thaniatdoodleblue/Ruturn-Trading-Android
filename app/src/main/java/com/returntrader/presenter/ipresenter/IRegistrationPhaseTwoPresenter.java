package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.FullRegistrationRequest;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IRegistrationPhaseTwoPresenter extends IPresenter {

    void onClickSubmit(FullRegistrationRequest fullRegistrationRequest, boolean isSaveEnabled);
}
