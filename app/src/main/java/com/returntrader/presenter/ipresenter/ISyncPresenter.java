package com.returntrader.presenter.ipresenter;

/**
 * Created by guru on 8/19/2017.
 */

public interface ISyncPresenter extends IPresenter {

    boolean isDataDownloaded();

    void setPartialRegisterCompleted();
}
