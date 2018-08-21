package com.returntrader.presenter.ipresenter;

/**
 * Created by moorthy on 12/8/2017.
 */

public interface IEditProfileProfilePhaseTwoPresenter extends IPresenter {


    String getSelectedResidence();

    String getSelectedProvince();

    String getSelectedNationality();


    void onClickSubmit(int province, String postalCode, int residence, String cityBirth, int nationality, String taxNumber, int income, int maritalStatus);

    String getSelectedIncome();

    String getSelectedMaritalStatus();
}
