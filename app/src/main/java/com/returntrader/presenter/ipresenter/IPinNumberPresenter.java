package com.returntrader.presenter.ipresenter;

/**
 * Created by moorthy on 9/14/2017.
 */

public interface IPinNumberPresenter extends IPresenter {

    void doValidation(String pinValue);

    void updateFingerPrintState(boolean canShow);

    boolean canShowFingerPrint();
}
