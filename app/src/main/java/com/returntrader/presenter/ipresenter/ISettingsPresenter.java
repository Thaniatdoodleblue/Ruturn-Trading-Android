package com.returntrader.presenter.ipresenter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.returntrader.adapter.ParentOptionsAdapter;

/**
 * Created by moorthy on 8/4/2017.
 */

public interface ISettingsPresenter extends IPresenter {

    ParentOptionsAdapter getMenuAdapter();

    void resetAdapter();

    void onClickTwitter();

    void onClickLogout();

    boolean isUserIdEmpty();

    void showImagePickerDialog();

    void pickAnImage();
}
