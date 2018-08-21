package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.CallbackManager;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.returntrader.R;
import com.returntrader.adapter.ParentOptionsAdapter;
import com.returntrader.adapter.listener.ISettingsAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.common.ExceptionTracker;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.Option;
import com.returntrader.model.common.ParentOptions;
import com.returntrader.model.common.UserInfo;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.ProfilePicUpdateRequest;
import com.returntrader.model.dto.request.UpdateSettingsNotifyRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.ProfilePicUpdateResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ProfilePicUpdateModel;
import com.returntrader.model.webservice.UpdateSettingsNotifyModel;
import com.returntrader.presenter.ipresenter.ISettingsPresenter;
import com.returntrader.utils.ImagePickerContext;
import com.returntrader.view.iview.ISettingsView;
//import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.returntrader.common.Constants.Settings.UPDATESETTINGS_NOTIFY;
import static com.returntrader.common.Constants.Settings.UPDATE_PROFILEPICTURE;

/**
 * Created by moorthy on 8/4/2017.
 */

public class SettingsPresenter extends BasePresenter implements ISettingsPresenter {
    private ISettingsView iSettingsView;
    private CallbackManager mCallbackManager;
    private List<ParentOptions> mMenuItems;
    private ParentOptionsAdapter mParentOptionsAdapter;
    private TwitterAuthClient mTwitterAuthClient;
    private AppConfigurationManager mAppConfigurationManager;
    private ImagePickerContext mImagePickerContext;
    private ProfilePicUpdateModel mProfilePicUpdateModel;
    private UpdateSettingsNotifyModel mUpdateSettingsNotifyModel;


    public SettingsPresenter(ISettingsView iView) {
        super(iView);
        this.iSettingsView = iView;
        this.mTwitterAuthClient = new TwitterAuthClient();
        this.mCallbackManager = CallbackManager.Factory.create();
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mImagePickerContext = new ImagePickerContext();
        mUpdateSettingsNotifyModel = new UpdateSettingsNotifyModel(iUpdateSettingsNotifyModelListener);
        iSettingsView.displayPickedImage(mAppConfigurationManager.getProfilePicture());
    }

    /***/
    private void checkPermission() {
        if (iSettingsView.getCodeSnippet().isAboveMarshmallow()) {
            iSettingsView.requestCameraPermission();
        } else {
            showImagePickerDialog();
        }
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        setUpMenuItems();
        setMenuAdapter();
        if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            UserInfo userInfo = iSettingsView.getCodeSnippet()
                    .getObjectFromJson(mAppConfigurationManager.getPartialUserInfo(), UserInfo.class);
            if (userInfo != null) {
                iSettingsView.setUserInfo(userInfo.getPhoneNumber().getPhoneNumber(), getUserName(userInfo.getFirstName(), userInfo.getSurname()));
            }
        } else {

            if (!(TextUtils.isEmpty(mAppConfigurationManager.getUserInfo()))) {
                FullRegistrationRequest userInfo = iSettingsView.getCodeSnippet()
                        .getObjectFromJson(mAppConfigurationManager.getUserInfo(), FullRegistrationRequest.class);
                if (userInfo != null) {
                    iSettingsView.setUserInfo(userInfo.getPhoneNumber().getPhoneNumber(), getUserName(userInfo.getFirstName(), userInfo.getLastName()));
                }
            }
        }
    }


    private String getUserName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    private void setMenuAdapter() {
        mParentOptionsAdapter = null;
        mParentOptionsAdapter = new ParentOptionsAdapter(mMenuItems, iSettingsAdapterListener);
        iSettingsView.setMenuItemAdapter(mParentOptionsAdapter);
    }

    private void setUpMenuItems() {
        mMenuItems = new ArrayList<>(3);

        List<Option> optionsGeneral = new ArrayList<Option>();
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_add_money), Constants.Settings.ADD_MONEY));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_with_draw), Constants.Settings.WITHDRAW_MONEY));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_change_password), Constants.Settings.CHANGE_PASSWORD));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_privacy_policy), Constants.Settings.PRIVACY_POLICY));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_fica), Constants.Settings.FICA));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_sars), Constants.Settings.SARS));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_language), Constants.Settings.LANGUAGE));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_report_problem), Constants.Settings.REPORT_PROBLEM));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_help_qa), Constants.Settings.HELP_QA));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_terms_conditions), Constants.Settings.TERMS_CONDITIONS));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_edit_profile), Constants.Settings.EDIT_PROFILE));
        optionsGeneral.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_messages), Constants.Settings.MESSAGES));


        mMenuItems.add(new ParentOptions(iSettingsView.getActivity().getString(R.string.txt_menu_general), optionsGeneral));

        List<Option> options1 = new ArrayList<Option>();
        options1.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_invite_email_friends), Constants.Settings.INVITE_EMAIL_FRIENDS));
        options1.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_invite_twitter), Constants.Settings.INVITE_TWITTER_FRIENDS));
        options1.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_invite_phone_contact), Constants.Settings.INVITE_PHONE_CONTACTS));
        options1.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_invite_facebook), Constants.Settings.INVITE_FACEBOOK_FRIENDS));
        options1.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_send_money), Constants.Settings.SEND_MONEY));
        options1.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_send_share), Constants.Settings.SEND_SHARES));

        mMenuItems.add(new ParentOptions(iSettingsView.getActivity().getString(R.string.txt_menu_social_header), options1));

        List<Option> options2 = new ArrayList<Option>();
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_sound), Constants.Settings.SOUND));
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_friend), Constants.Settings.FRIEND));
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_social), Constants.Settings.SOCIAL));
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_breaking_news), Constants.Settings.BREAKING_NEWS, mAppConfigurationManager.isBreakingNotifyEnabled()));
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_company_notification), Constants.Settings.COMPANY_NOTIFICATION, mAppConfigurationManager.isCompanyNotifyEnabled()));
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.txt_menu_jse_news), Constants.Settings.JSE_NOTIFICATION, mAppConfigurationManager.isJseNotifyEnabled()));
        options2.add(new Option(iSettingsView.getActivity().getString(R.string.log_out), Constants.Settings.LOGOUT));

        mMenuItems.add(new ParentOptions(iSettingsView.getActivity().getString(R.string.txt_menu_settings), options2));
    }


    private boolean isFromCamera = false;

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResultPresenter");
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);

        int aspectX = 1;
        int aspectY = 1;

        switch (requestCode) {

            case Constants.RequestCodes.KEY_REQUEST_OPEN_CONTACTS:
                shareLink(data);
                break;

            case Constants.RequestCodes.REQUEST_INVITE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the invitation IDs of all sent messages
                    String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                    Log.e("SuccessInvite", "" + ids);
                    //iSettingsView.onEmailInviteClicked();
                } else {
                    Log.e("SuccessFailed" + data, "" + resultCode);
                }
                break;

            case Constants.RequestCodes.PICK_CAMERA_IMAGE_REQUEST_CODE:
                File file = new File(mImagePickerContext.getSelectedImagePath());
                if (!file.exists()) {
                    return;
                }
                isFromCamera = true;
                /*Crop.of(Uri.fromFile(file), Uri.fromFile(file))
                        .withAspect(aspectX, aspectY)
                        .withMaxSize(240, 240)
                        .start(iSettingsView.getActivity());*/

                CropImage.activity(Uri.fromFile(file))
                        .setAspectRatio(aspectX, aspectY)
                        .setRequestedSize(240, 240)
                        .start(iSettingsView.getActivity());
                break;

            case Constants.RequestCodes.PICK_GALLERY_IMAGE_REQUEST_CODE:
                if (data != null) {
                    isFromCamera = false;
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = iSettingsView.getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        if (picturePath != null) {
                            File source = new File(picturePath);
                            if (!source.exists()) {
                                return;
                            }
                            /*Crop.of(Uri.fromFile(source), mImagePickerContext.getImageCaptureUri())
                                    .withAspect(aspectX, aspectY)
                                    .withMaxSize(240, 240)
                                    .start(iSettingsView.getActivity());*/

                            CropImage.activity(Uri.fromFile(source))
                                    .setAspectRatio(aspectX, aspectY)
                                    .setRequestedSize(240, 240)
                                    .start(iSettingsView.getActivity());
                        }
                    }
                }
                break;

           /* case Crop.REQUEST_CROP:
                if (data == null) {
                    return;
                }
                // if nothing received
                if (Crop.getOutput(data) != null) {
                    File croppedFile = null;
                    if (isFromCamera) {
                        try {
                            Bitmap bitmap = mImagePickerContext.getRotators(Crop.getOutput(data).getPath());
                            croppedFile = mImagePickerContext.getFileFromBitmap(bitmap);
//                            Log.d("******File Path", "" + croppedFile.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        croppedFile = new File(Crop.getOutput(data).getPath());
                    }
                    //File croppedFile = new File(Crop.getOutput(data).getPath());
                    Log.d(TAG, "selected file : " + croppedFile.getAbsolutePath());
                    if (croppedFile.exists()) {
                        updateProfilePicture(croppedFile);
                        iSettingsView.displayPickedImage(croppedFile);
                    }
                }
                break;*/


            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    Uri resultUri = result.getUri();
                    File croppedFile = new File(resultUri.getPath());
                    updateProfilePicture(croppedFile);
                    iSettingsView.displayPickedImage(croppedFile);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    iSettingsView.showMessage(error.getMessage());
                }
                break;
        }
    }

    /***/
    private IBaseModelListener<ProfilePicUpdateResponse> iBaseModelListener = new IBaseModelListener<ProfilePicUpdateResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ProfilePicUpdateResponse response) {
            iSettingsView.dismissProgressbar();
            if (response != null) {
                if (!TextUtils.isEmpty(response.getProfilePicUrl())) {
                    iSettingsView.displayPickedImage(response.getProfilePicUrl());
                    mAppConfigurationManager.setProfilePicture(response.getProfilePicUrl());
                } else {
                    iSettingsView.showMessage("Something went wrong. Please try again.");
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iSettingsView.dismissProgressbar();
            if (e != null) {
                iSettingsView.showMessage(e.getException());
            }
        }
    };


    /***/
    private IBaseModelListener<BaseResponse> iUpdateSettingsNotifyModelListener = new IBaseModelListener<BaseResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BaseResponse response) {
            iSettingsView.dismissProgressbar();
            if (response.isSuccess()) {

            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iSettingsView.dismissProgressbar();
            if (e != null) {
                iSettingsView.showMessage(e.getException());
            }
        }
    };

    /***/
    private void updateProfilePicture(File croppedImage) {
        if (iSettingsView.getCodeSnippet().hasNetworkConnection()) {
            if (!TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
                iSettingsView.showProgressbar();
                mProfilePicUpdateModel = new ProfilePicUpdateModel(iBaseModelListener);
                ProfilePicUpdateRequest request = new ProfilePicUpdateRequest();
                request.setUser_id(mAppConfigurationManager.getUserId());
                request.setProfile(croppedImage);
                mProfilePicUpdateModel.updateProfilePic(UPDATE_PROFILEPICTURE, request);
            }
        } else {
            iSettingsView.showNetworkMessage();
        }
    }

    /***/
    private void shareLink(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor cursor = null;
                try {
                    cursor = TraderApplication.getInstance().getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID},
                            null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        // iSettingsView.showMessage("contactId : " + contactId);
                        iSettingsView.sendSms(contactId);
                    }

                } catch (Exception e) {
                    ExceptionTracker.track(e);
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
    }

    /***/
    private ISettingsAdapterListener iSettingsAdapterListener = new ISettingsAdapterListener() {
        @Override
        public void onClickGeneral(Option data, int position) {
            boolean isPartialUser = TextUtils.isEmpty(mAppConfigurationManager.getUserId());
            boolean isFicaVerified = mAppConfigurationManager.isFicaVerified();

            Log.e("UserID = " + isFicaVerified, "" + mAppConfigurationManager.getUserId());
            switch (data.getItemType()) {
                case Constants.Settings.ADD_MONEY:
                    if (isPartialUser) {
                        iSettingsView.showMessage(iSettingsView.getActivity().getString(R.string.alert_full_registration_required));
                        return;
                    }
                    if (!isFicaVerified) {
                        iSettingsView.showMessage(iSettingsView.getActivity().getString(R.string.alert_fica_required));
                        return;
                    }
                    iSettingsView.navigateTo(data.getItemType());
                    break;

                case Constants.Settings.WITHDRAW_MONEY:
                    if (isPartialUser) {
                        iSettingsView.showMessage(iSettingsView.getActivity().getString(R.string.alert_full_registration_required));
                        return;
                    }
                    if (!isFicaVerified) {
                        iSettingsView.showMessage(iSettingsView.getActivity().getString(R.string.alert_fica_required));
                        return;
                    }
                    iSettingsView.navigateTo(data.getItemType());
                    break;

                case Constants.Settings.FICA:
                    if (isPartialUser) {
                        iSettingsView.showMessage(iSettingsView.getActivity().getString(R.string.alert_full_registration_required));
                        return;
                    }
                    iSettingsView.navigateTo(data.getItemType());
                    break;

                default:
                    iSettingsView.navigateTo(data.getItemType());
            }

        }

        @Override
        public void onClickSocial(Option data, int position) {

        }

        @Override
        public void onClickSettings(Option data, int position) {
            doUpdatePreference(data);
        }
    };


    @Override
    public ParentOptionsAdapter getMenuAdapter() {
        return mParentOptionsAdapter;
    }

    @Override
    public void resetAdapter() {
        setMenuAdapter();
    }


    @Override
    public void onClickTwitter() {
        if (!(iSettingsView.getCodeSnippet().hasNetworkConnection())) {
            return;
        }

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session == null) {
            mTwitterAuthClient.authorize(iSettingsView.getActivity(), new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    if (result != null && result.data.getUserId() != 0) {
                        iSettingsView.navigateTo(Constants.Settings.NAVIGATE_TO_TWITTER_FOLLOWER);
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.d(TAG, "failure :" + exception.getMessage());
                }
            });
        } else {
            iSettingsView.navigateTo(Constants.Settings.NAVIGATE_TO_TWITTER_FOLLOWER);
        }
    }

    @Override
    public void onClickLogout() {
        mAppConfigurationManager.clearPreference();
    }

    @Override
    public boolean isUserIdEmpty() {
        return TextUtils.isEmpty(mAppConfigurationManager.getUserId());
    }

    @Override
    public void showImagePickerDialog() {
        mImagePickerContext.showChooserDialog(iSettingsView.getActivity());
    }

    @Override
    public void pickAnImage() {
        checkPermission();
    }

    /***/
    private void doUpdatePreference(Option option) {
        switch (option.getItemType()) {
            case Constants.Settings.BREAKING_NEWS:
                mAppConfigurationManager.setBreakingNotificationEnabled(option.isChecked());
                mAppConfigurationManager.setBreakingNotifyEnabled(option.isChecked());
                iSettingsView.getCodeSnippet().changeNotificationPreference(option.isChecked(), Constants.PushNotificationTopicName.TOPIC_BREAKING_NEWS);
                break;

            case Constants.Settings.JSE_NOTIFICATION:
                mAppConfigurationManager.setJseNotificationEnabled(option.isChecked());
                mAppConfigurationManager.setJseNotifyEnabled(option.isChecked());
                iSettingsView.getCodeSnippet().changeNotificationPreference(option.isChecked(), Constants.PushNotificationTopicName.TOPIC_JSE_NEWS);
                break;

            case Constants.Settings.COMPANY_NOTIFICATION:
                mAppConfigurationManager.setCompanyNotificationEnabled(option.isChecked());
                mAppConfigurationManager.setCompanyNotifyEnabled(option.isChecked());
                iSettingsView.getCodeSnippet().changeNotificationPreference(option.isChecked(), Constants.PushNotificationTopicName.TOPIC_COMPANY_NOTIFICATION);
                break;
        }

        if (!TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            UpdateSettingsNotifyRequest request = new UpdateSettingsNotifyRequest();
            request.setUserId(mAppConfigurationManager.getUserId());
            request.setNotifyBreakingNews(mAppConfigurationManager.isBreakingNotifyEnabled());
            request.setNotifyCN(mAppConfigurationManager.isCompanyNotifyEnabled());
            request.setNotifyJSE(mAppConfigurationManager.isJseNotifyEnabled());
            callUpdateNotifySettings(request);
        }
    }

    /***/
    private void callUpdateNotifySettings(UpdateSettingsNotifyRequest request) {
        if (iSettingsView.getCodeSnippet().hasNetworkConnection()) {
            mUpdateSettingsNotifyModel.updateSettingsNotify(UPDATESETTINGS_NOTIFY, request);
        } else {
            iSettingsView.showNetworkMessage();
        }
    }
}
