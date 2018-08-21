package com.returntrader.presenter.ipresenter;

/**
 * Created by moorthy on 7/12/2017.
 */

public interface IGraphFragPresenter extends IPresenter {

    void onLoad(int type);

    void callGraphApi();
}
