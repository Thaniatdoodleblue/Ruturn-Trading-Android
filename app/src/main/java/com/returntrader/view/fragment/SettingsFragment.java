package com.returntrader.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.returntrader.R;
import com.returntrader.adapter.ParentOptionsAdapter;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.presenter.SettingsPresenter;
import com.returntrader.presenter.ipresenter.ISettingsPresenter;
import com.returntrader.view.activity.AddMoneyActivity;
import com.returntrader.view.activity.AuthenticationActivity;
import com.returntrader.view.activity.ContactInviteActivity;
import com.returntrader.view.activity.FicaDocumentActivity;
import com.returntrader.view.activity.HomeActivity;
import com.returntrader.view.activity.NotificationActivity;
import com.returntrader.view.activity.RegistrationPhaseOneActivity;
import com.returntrader.view.activity.ReportProblemActivity;
import com.returntrader.view.activity.SplashActivity;
import com.returntrader.view.activity.TwitterInviteActivity;
import com.returntrader.view.activity.WebViewActivity;
import com.returntrader.view.activity.WithDrawMoneyActivity;
import com.returntrader.view.iview.ISettingsView;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends BaseFragment implements View.OnClickListener, GroupExpandCollapseListener, ISettingsView {
    private RecyclerView mRecyclerView;
    private TextView tvPhoneNumber;
    private TextView tvUserName;
    private Button btLogOutButton;
    private ImageView editNameIcon;
    private ImageView editPictureIcon;
    private ExpandableGroup expandedGroup;
    private CircleImageView circleImage;
    private AppConfigurationManager mAppConfigurationManager;
    public ISettingsPresenter iSettingsPresenter;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rlListOptions);
        tvPhoneNumber = view.findViewById(R.id.tvNumberId);
        tvUserName = view.findViewById(R.id.tvNameId);
        btLogOutButton = view.findViewById(R.id.btLogOutButton);
        editNameIcon = view.findViewById(R.id.ivnameIconId);
        editPictureIcon = view.findViewById(R.id.ivCameraIcon);
        circleImage = view.findViewById(R.id.circleImage);

        circleImage.setImageBitmap(null);
        circleImage.setImageDrawable(null);
        circleImage.setImageResource(R.drawable.ic_view_person);

        mAppConfigurationManager = new AppConfigurationManager(getActivity());

        btLogOutButton.setOnClickListener(this);
        editNameIcon.setOnClickListener(this);
        editPictureIcon.setOnClickListener(this);
        circleImage.setOnClickListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        iSettingsPresenter = new SettingsPresenter(this);
        iSettingsPresenter.onCreatePresenter(getArguments());
    }

    @Override
    public void setMenuItemAdapter(ParentOptionsAdapter parentOptionsAdapter) {
        parentOptionsAdapter.setOnGroupExpandCollapseListener(this);
        mRecyclerView.setAdapter(parentOptionsAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCameraIcon:
                showPickerDialog();
                break;
            case R.id.circleImage:
                showPickerDialog();
                break;
        }
    }

    /***/
    private void showPickerDialog() {
        iSettingsPresenter.pickAnImage();
    }

    /***/
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, do you want to logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            iSettingsPresenter.onClickLogout();
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            getActivity().finish();
            dialog.cancel();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onGroupExpanded(ExpandableGroup group) {
        if (expandedGroup != null) {
            iSettingsPresenter.getMenuAdapter().toggleGroup(expandedGroup);
        }
        expandedGroup = group;
    }

    @Override
    public void onGroupCollapsed(ExpandableGroup group) {
        expandedGroup = null;
    }

    @Override
    public void navigateTo(int type) {
        Bundle bundle;
        Intent intent = null;
        switch (type) {
            case Constants.Settings.ADD_MONEY:
                intent = new Intent(getActivity(), AddMoneyActivity.class);
                break;
            case Constants.Settings.EDIT_PROFILE:
                if (iSettingsPresenter.isUserIdEmpty()) {
                    showMessage(R.string.alert_full_registration_required);
                    return;
                }
                new AppConfigurationManager(getActivity()).setPinAuthenticationRequired(true);
                new AppConfigurationManager(getActivity()).setFingerPrintEnabledStatus(true);
                bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_EDIT_PROFILE);
                intent = new Intent(getActivity(), AuthenticationActivity.class);
                intent.putExtras(bundle);
                //intent = new Intent(getActivity(), EditProfilePhaseOneActivity.class);
                break;
            case Constants.Settings.MESSAGES:
                intent = new Intent(getActivity(), NotificationActivity.class);
                break;
            case Constants.Settings.CHANGE_PASSWORD:
                new AppConfigurationManager(getActivity()).setPinAuthenticationRequired(true);
                new AppConfigurationManager(getActivity()).setFingerPrintEnabledStatus(true);
                bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_CHANGE_PIN_VERIFICATION);
                intent = new Intent(getActivity(), AuthenticationActivity.class);
                intent.putExtras(bundle);
                break;
            case Constants.Settings.PRIVACY_POLICY:
                bundle = new Bundle();
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, getString(R.string.url_privacy_policy));
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, getString(R.string.txt_menu_privacy_policy));
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtras(bundle);
                break;
            case Constants.Settings.TERMS_CONDITIONS:
                bundle = new Bundle();
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, getString(R.string.url_terms_condition));
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, getString(R.string.txt_menu_terms_conditions));
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtras(bundle);
                break;
            case Constants.Settings.HELP_QA:
                bundle = new Bundle();
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, getString(R.string.url_help));
                bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, getString(R.string.txt_menu_help_qa));
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtras(bundle);
                break;

            case Constants.Settings.REPORT_PROBLEM:
                intent = new Intent(getActivity(), ReportProblemActivity.class);
                //reportBug();
                break;
            case Constants.Settings.WITHDRAW_MONEY:
               /* if (iSettingsPresenter.isUserIdEmpty()) {
                    showMessage(R.string.alert_full_registration_required);
                    return;
                }*/
                intent = new Intent(getActivity(), WithDrawMoneyActivity.class);
                break;
            case Constants.Settings.FICA:
                intent = new Intent(getActivity(), FicaDocumentActivity.class);
                break;
            case Constants.Settings.INVITE_FACEBOOK_FRIENDS:
                showAppInviteDialog();
                break;
            case Constants.Settings.INVITE_PHONE_CONTACTS:
                //inviteContact();
                intent = new Intent(getActivity(), ContactInviteActivity.class);
                break;
            case Constants.Settings.INVITE_EMAIL_FRIENDS:
                onEmailInviteClicked();
                break;
            case Constants.Settings.INVITE_TWITTER_FRIENDS:
                inviteViaTwitter();
                break;
            case Constants.Settings.NAVIGATE_TO_TWITTER_FOLLOWER:
                intent = new Intent(getActivity(), TwitterInviteActivity.class);
                break;
            case Constants.Settings.LOGOUT:
//                boolean isPartialUser = mAppConfigurationManager.isPartialRegisterCompleted();
                boolean isPartialUser = TextUtils.isEmpty(mAppConfigurationManager.getUserId());
                if (isPartialUser) {
                    partialUserAlertDialog();
                } else {
                    logout();
                }
                break;
        }
        if (intent != null) {
            if (type == Constants.Settings.FICA) {
                startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_FICA_TO_UPLOAD);
            } else {
                startActivity(intent);
            }
        }
    }

    /***/
    private void partialUserAlertDialog() {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("WARNING!\n" +
                "If you logout before completing registration, your account will be deleted.");

        builder.setPositiveButton("Complete Registration", (dialog, which) -> {
            Intent intent = new Intent(getActivity(), RegistrationPhaseOneActivity.class);
            getActivity().startActivity(intent);
            dialog.cancel();
        });
        builder.setNegativeButton("Delete Account", (dialog, which) -> {
            iSettingsPresenter.onClickLogout();
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            getActivity().finish();
            dialog.cancel();
        });

        AlertDialog alert = builder.create();
        alert.show();

//        final Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
//        final Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
//        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
//        positiveButtonLL.gravity = Gravity.CENTER;
//        positiveButton.setLayoutParams(positiveButtonLL);
//        negativeButton.setLayoutParams(positiveButtonLL);
    }

    @Override
    public void setUserInfo(String phoneNumber, String userName) {
        if (!(TextUtils.isEmpty(phoneNumber))) {
            tvPhoneNumber.setText(phoneNumber);
        }

        if (!(TextUtils.isEmpty(userName))) {
            tvUserName.setText(userName);
        }
    }

    /***/
    private void inviteViaTwitter() {
        iSettingsPresenter.onClickTwitter();
    }

    /***/
    private void inviteContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, Constants.RequestCodes.KEY_REQUEST_OPEN_CONTACTS);
    }

    /***/
    public void reportBug() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:");
            intent.setData(uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Report Problem");
            startActivity(Intent.createChooser(intent, "Send via"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEmailInviteClicked() {
        /*Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.app_name))
                .setMessage("Your best buddy is using Ruturn Trading and wants you to join too." +
                        "\n" +
                        "Start trading today.")
                .setDeepLink(Uri.parse(getString(R.string.txt_url_app_fb_link)))
                .setCallToActionText("Install application.")
                .setOtherPlatformsTargetApplication(AppInviteInvitation.IntentBuilder.PlatformMode.PROJECT_PLATFORM_IOS, getString(R.string.txt_key_ios_firebase_client_id))
                .build();
        startActivityForResult(intent, Constants.RequestCodes.REQUEST_INVITE);*/


        Intent i = new Intent(Intent.ACTION_SENDTO);
        Uri uri = Uri.parse("mailto:");
        i.setData(uri);
        i.putExtra(Intent.EXTRA_SUBJECT, "Invite for Ruturn Trading");
        i.putExtra(Intent.EXTRA_TEXT, "Your best buddy is using Ruturn Trading and wants you to join too." + "\n" + "Start trading today.");
        startActivity(Intent.createChooser(i, "Send via"));
    }

    @Override
    public void showAppInviteDialog() {
        String appLinkUrl, previewImageUrl;
        appLinkUrl = getString(R.string.txt_url_app_fb_link);
        previewImageUrl = getString(R.string.txt_fb_app_image_url);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(appLinkUrl))
                .build();
        ShareDialog shareDialog = new ShareDialog(getActivity());
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

        /*if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }*/
    }

    @Override
    public void sendSms(String phoneNumber) {
        String appUrl = TraderApplication.getInstance().getAppUrl();
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        sendIntent.putExtra("sms_body", getString(R.string.txt_share_content, getString(R.string.txt_url_app_fb_link)));
        sendIntent.putExtra("address", phoneNumber);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    @Override
    public Fragment getFragmentContext() {
        return this;
    }




    @Override
    public void resetView() {
        iSettingsPresenter.resetAdapter();
        expandedGroup = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        iSettingsPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }

    @Override
    public void requestCameraPermission() {
        ((HomeActivity) getActivity()).requestCameraPermission();
    }

    //Loading Image from file
    @Override
    public void displayPickedImage(File croppedImage) {
        TraderApplication.getInstance().loadImage(croppedImage, circleImage);
    }

    //Loading Image from url
    @Override
    public void displayPickedImage(String profilePicUrl) {
        if (TextUtils.isEmpty(profilePicUrl)) {
            circleImage.setBackground(null);
            circleImage.setImageDrawable(null);
            circleImage.setImageResource(R.drawable.ic_view_person);
        } else {
            TraderApplication.getInstance().loadImage(profilePicUrl, circleImage);
        }
    }
}
