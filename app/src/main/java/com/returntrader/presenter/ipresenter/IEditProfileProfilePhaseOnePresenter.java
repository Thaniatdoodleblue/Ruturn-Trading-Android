package com.returntrader.presenter.ipresenter;

import com.returntrader.model.dto.request.EditProfileRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;

import java.util.Calendar;

/**
 * Created by moorthy on 12/8/2017.
 */

public interface IEditProfileProfilePhaseOnePresenter extends IPresenter {

    void setDateOfBirth(String date, long dateInMilliSeconds);

    Calendar getBirthDateCalender();

    boolean isDateSelected();

    void onClickSubmit(EditProfileRequest editProfileRequest);
}
