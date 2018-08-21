package com.returntrader.presenter.ipresenter;

import java.util.Calendar;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IRegistrationPhaseThreePresenter extends IPresenter {

    void onClickSubmit(String taxInfo, int income, int maritalStatus, boolean isSaveEnabled);

    String getSelectedIncome();

    String getSelectedMaritalStatus();
}
