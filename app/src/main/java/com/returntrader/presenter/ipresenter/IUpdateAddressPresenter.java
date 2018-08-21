package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.AddressRequest;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IUpdateAddressPresenter extends IPresenter {

    void onClickSubmit(AddressRequest addressRequest);
}
