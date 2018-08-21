package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.BankDetailRequest;

/**
 * Created by moorthy on 8/29/2017.
 */

public interface IBankDetailPresenter extends IPresenter {

    void onClickSubmit(BankDetailRequest bankDetailRequest);
}
