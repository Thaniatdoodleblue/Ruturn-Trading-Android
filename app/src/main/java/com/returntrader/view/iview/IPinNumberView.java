package com.returntrader.view.iview;

/**
 * Created by moorthy on 9/14/2017.
 */

public interface IPinNumberView extends IView {

    void loadNextPage(int type);

    void setEnteredPin(String pinValue);

    void setScreenDetails(int entryType);

    void clearPin();

    void clearPinNumber();

    void setForgotPasswordEnabled(int visibility);

    void erasePinOnTry();
}
