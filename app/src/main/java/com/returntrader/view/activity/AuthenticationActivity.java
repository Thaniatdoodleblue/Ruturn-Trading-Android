package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.Log;
import com.returntrader.presenter.AuthenticationPresenter;
import com.returntrader.presenter.ipresenter.IAuthenticationPresenter;
import com.returntrader.view.fragment.PinNumberFragment;
import com.returntrader.view.iview.IAuthenticationView;

/**
 * Created by moorthy on 8/2/2017
 */

public class AuthenticationActivity extends BaseActivity implements IAuthenticationView, PinNumberFragment.AuthenticationListener {
    private IAuthenticationPresenter iAuthenticationPresenter;
    private FragmentManager mFragmentManager = null;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authentication);
        bindActivity();
        iAuthenticationPresenter = new AuthenticationPresenter(this);
        iAuthenticationPresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        mToolbarTitle.setText(R.string.pinLock);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                iAuthenticationPresenter.setPinAuthenticationRequired(false);
                getCodeSnippet().hideKeyboard(AuthenticationActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /***/
    private void navigateToFingerPrintOrThankYou() {
        if (getCodeSnippet().isAboveMarshmallow()) {
            Intent intent = new Intent(getApplicationContext(), FingerPrintActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_FINGER_PRINT_NAVIGATION_FROM, Constants.FingerPrintNavigation.FROM_REGISTRATION);
            intent.putExtras(bundle);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else {
            setResult(RESULT_OK);
            startActivity(new Intent(getApplicationContext(), ThankYouActivity.class));
            finish();
        }
    }


    /***/
    private void hideBackAndClearPin(boolean showHomeUp) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeUp);
        iAuthenticationPresenter.clearPin();
    }

    @Override
    public void loadFragment(int fragmentType) {

        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }

        switch (fragmentType) {
            case Constants.AuthenticationType.LOAD_CONFIRM_PIN_ENTRY:
//                mToolbarTitle.setText(R.string.reenternewpinLock);
                hideBackAndClearPin(true);
                mFragmentManager
                        .beginTransaction()
                        .add(R.id.container_authentication, iAuthenticationPresenter.getConfirmPinEntryFragment(), "ConfirmPinNumberFragment")
                        .addToBackStack("ConfirmPinNumberFragment")
                        .commit();
                break;
            case Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN:
//                mToolbarTitle.setText(R.string.pinLock);
                hideBackAndClearPin(true);
                mFragmentManager
                        .beginTransaction()
                        .add(R.id.container_authentication, iAuthenticationPresenter.getAuthenticateFragment(), "getAuthenticateFragment")
                        .addToBackStack("getAuthenticateFragment")
                        .commit();
                break;

            case Constants.AuthenticationType.LOAD_LOGINAUTH_PIN:
//                mToolbarTitle.setText(R.string.pinLock);
                hideBackAndClearPin(true);
                mFragmentManager
                        .beginTransaction()
                        .add(R.id.container_authentication, iAuthenticationPresenter.getLoginAuthFragment(), "getLoginAuthFragment")
                        .addToBackStack("getLoginAuthFragment")
                        .commit();
                break;

            case Constants.AuthenticationType.LOAD_NEXT_PAGE:
                Log.d(TAG, "authenticationFrom :  " + iAuthenticationPresenter.authenticationFrom());
                switch (iAuthenticationPresenter.authenticationFrom()) {
                    case Constants.AuthenticationType.FROM_FORGOT_PIN:
//                        closeScreen();
                        iAuthenticationPresenter.updatePasscode();
                        break;
                    case Constants.AuthenticationType.FROM_REGISTRATION:
                        navigateToFingerPrintOrThankYou();
                        break;
                    case Constants.AuthenticationType.FROM_LOGIN:
                        //navigateToHome();
                        iAuthenticationPresenter.verifyPinAndLogin();
                        break;
                    case Constants.AuthenticationType.FROM_CHANGE_REQUEST:
                        iAuthenticationPresenter.updatePasscode();
                        break;
                }
                break;
            case Constants.AuthenticationType.LOAD_NEW_PIN_ENTRY:
//                mToolbarTitle.setText(R.string.newpinLock);
                hideBackAndClearPin(true);
                mFragmentManager
                        .beginTransaction()
                        .add(R.id.container_authentication, iAuthenticationPresenter.getNewPinEntryFragment(), "PinNumberFragment")
                        .addToBackStack("PinNumberFragment")
                        .commit();
                break;
            case Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS:
                switch (iAuthenticationPresenter.authenticationFrom()) {
                    case Constants.AuthenticationType.FROM_EDIT_PROFILE:
                        navigateToEditProfile();
                        break;
                    default:
                        navigateToAuthentication();
                }
                break;
        }
        iAuthenticationPresenter.updateCurrentFragment(fragmentType);
    }

    /***/
    private void navigateToEditProfile() {
        Intent intent = new Intent(this, EditProfilePhaseOneActivity.class);
        startActivity(intent);
        finish();
    }

    /***/
    private void navigateToAuthentication() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_CHANGE_REQUEST);
        intent.putExtras(bundle);
        finish();
        startActivity(intent);
    }


    private void navigateToHome() {
        if (getCodeSnippet().isAboveMarshmallow()) {
            Intent intent = new Intent(getApplicationContext(), FingerPrintActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_FINGER_PRINT_NAVIGATION_FROM, Constants.FingerPrintNavigation.FROM_LOGIN);
            intent.putExtras(bundle);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            setResult(RESULT_CANCELED);
            finishAffinity();
        }
    }


    @Override
    public void navigateToFingerPrintOrOTP(Bundle bundle) {
        if (getCodeSnippet().isAboveMarshmallow()) {
            Intent intent = new Intent(getApplicationContext(), FingerPrintActivity.class);
            bundle.putInt(Constants.BundleKey.BUNDLE_FINGER_PRINT_NAVIGATION_FROM, Constants.FingerPrintNavigation.FROM_LOGIN);
            intent.putExtras(bundle);
            startActivity(intent);
//            setResult(RESULT_OK);
//            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), OtpCodeVerifyActivity.class);
            bundle.putInt(Constants.BundleKey.BUNDLE_FINGER_PRINT_NAVIGATION_FROM, Constants.FingerPrintNavigation.FROM_LOGIN);
            intent.putExtras(bundle);
            startActivity(intent);
//            setResult(RESULT_CANCELED);
//            finishAffinity();
        }
        finish();
    }


    @Override
    public void closeScreen() {
        finish();
    }

    @Override
    public void setFirstEnteredPin(String value) {
        iAuthenticationPresenter.setFirstEnteredPin(value);
    }

    @Override
    public void setToolBarTitle(String toolBarTitle) {

        if (TextUtils.isEmpty(toolBarTitle)) {
            return;
        }
        mToolbarTitle.setText(toolBarTitle);
    }

    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(AuthenticationActivity.this);
        if (mFragmentManager.getBackStackEntryCount() > 1) {
            mFragmentManager.popBackStack();
            hideBackAndClearPin(true);
        } else {
            new AppConfigurationManager(getActivity()).setPinAuthenticationRequired(false);
            new AppConfigurationManager(getActivity()).setFingerPrintEnabledStatus(false);
            finish();
        }
    }


}
