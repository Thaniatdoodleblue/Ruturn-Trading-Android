package com.returntrader.view.iview;

/**
 * Created by moorthy on 12/5/2017.
 */

public interface IQuestionView extends IView {

    void loadFragment(int type);

    void dismissDialog();

    void updateProgressStatus(int isSuccess);
}
