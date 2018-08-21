package com.returntrader.presenter.ipresenter;

import java.util.Calendar;

/**
 * Created by moorthy on 10/28/2017.
 */

public interface IRegistrationPhaseOnePresenter extends IPresenter {

    void setDateOfBirth(String date, long dateInMilliSeconds);

    boolean isDateSelected();

    void onClickSubmit(String race, String cityBirth, int nationality, int residence, int province, boolean isSaveEnabled);

    Calendar getBirthDateCalender();

    String getSelectedResidence();

    String getSelectedRace();

    String getSelectedProvince();

    String getSelectedNationality();
}
