package com.returntrader.view.iview;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.returntrader.adapter.ParentOptionsAdapter;

import java.io.File;

/**
 * Created by moorthy on 8/4/2017.
 */

public interface ISettingsView extends IView {

    void showAppInviteDialog();

    void sendSms(String phoneNumber);

    Fragment getFragmentContext();

    void onEmailInviteClicked();

    void resetView();

    void setMenuItemAdapter(ParentOptionsAdapter parentOptionsAdapter);

    void navigateTo(int position);

    void setUserInfo(String phoneNumber, String userName);

    void requestCameraPermission();

    void displayPickedImage(File croppedImage);

    void displayPickedImage(String profilePicUrl);
}
