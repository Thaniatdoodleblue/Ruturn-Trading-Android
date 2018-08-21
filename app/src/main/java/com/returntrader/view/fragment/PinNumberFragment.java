package com.returntrader.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.PinNumberPresenter;
import com.returntrader.presenter.ipresenter.IPinNumberPresenter;
import com.returntrader.view.activity.ForgotPinActivity;
import com.returntrader.view.iview.IPinNumberView;
import com.returntrader.view.widget.NumberPadView;
import com.returntrader.view.widget.NumberView;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_ENTRY_TYPE;

public class PinNumberFragment extends BaseFragment implements IPinNumberView, CheckBox.OnCheckedChangeListener, View.OnClickListener {

    private CheckBox vEtTwo;
    private CheckBox vEtThree;
    private CheckBox vEtFour;
    private CheckBox vEtOne;
    private TextView vTvPinStatus;
    private ScrollView mRootView;
    private IPinNumberPresenter iPinNumberPresenter;
    private FrameLayout mNumberPadContainer;
    private NumberPadView mNumberView;
    private String finalNumber;
    private TextView vTvForgotPassword;
    private RelativeLayout rlFingerPrintContainer;
    private TextView vBtnSkip;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pin_number, container, false);
        bindFragment(rootView);
        iPinNumberPresenter = new PinNumberPresenter(this);
        iPinNumberPresenter.onCreatePresenter(getArguments());
        return rootView;
    }

    public void bindFragment(View rootView) {
        vTvPinStatus = rootView.findViewById(R.id.tv_pin_status);
        mRootView = rootView.findViewById(R.id.layout_root_layout);
        vEtOne = rootView.findViewById(R.id.firstDigit);
        vEtTwo = rootView.findViewById(R.id.secondDigit);
        vEtThree = rootView.findViewById(R.id.thirdDigit);
        vEtFour = rootView.findViewById(R.id.fourthDigit);
        mNumberPadContainer = rootView.findViewById(R.id.layout_number_pad_view);
        vEtFour.setOnCheckedChangeListener(this);
        vTvForgotPassword = rootView.findViewById(R.id.tv_forgot_pass_code);
        rlFingerPrintContainer = rootView.findViewById(R.id.rlFingerPrintContainer);
        vBtnSkip = rootView.findViewById(R.id.vBtnSkip);
        vTvForgotPassword.setOnClickListener(this);
        vBtnSkip.setOnClickListener(this);
        setUpNumberPad();
    }

    private void setUpNumberPad() {
        mNumberView = new NumberPadView(getActivity());
        mNumberPadContainer.removeAllViews();
        mNumberPadContainer.addView(mNumberView);
        mNumberView.setOnNumberListener(new NumberClickActivity());
    }

    private void doPerformNavigate(String newnumber) {
        if (!(TextUtils.isEmpty(newnumber))) {
            iPinNumberPresenter.doValidation(newnumber);
        } else {
            showMessage("Please enter a valid pin PinFrag");
        }
    }


    @Override
    public void loadNextPage(int type) {
        Log.d(TAG, "getParentFragment() " + getParentFragment());
        if (getParentFragment() == null) {
            ((AuthenticationListener) getActivity()).loadFragment(type);
        } else {
            ((AuthenticationListener) getParentFragment()).loadFragment(type);
        }
    }

    @Override
    public void setEnteredPin(String pinValue) {
        if (getParentFragment() == null) {
            ((AuthenticationListener) getActivity()).setFirstEnteredPin(pinValue);
        } else {
            ((AuthenticationListener) getParentFragment()).setFirstEnteredPin(pinValue);
        }

    }

    private int entryType;

    @Override
    public void setScreenDetails(int entryType) {
        this.entryType = entryType;
        rlFingerPrintContainer.setVisibility(View.GONE);
        switch (entryType) {
            case Constants.AuthenticationType.LOAD_CONFIRM_PIN_ENTRY:
                vTvPinStatus.setText(R.string.txt_re_enter_newpin);
                break;
            case Constants.AuthenticationType.LOAD_NEW_PIN_ENTRY:
                vTvPinStatus.setText(R.string.txt_enter_newpin);
                break;
            case Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN:
                vTvPinStatus.setText(R.string.txt_enter_pin);
                checkFingurePrintAccessability();
                /*vTvPinStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.homePage));
                mRootView.setBackgroundColor(ContextCompat.getColor(getActivity(),android.R.color.white));*/
                break;
            case Constants.AuthenticationType.LOAD_LOGINAUTH_PIN:
                //To satisfy the condition and to enable forgotpin in LoginAuth itself.
                setForgotPasswordEnabled(View.VISIBLE);
                vTvPinStatus.setText(R.string.txt_enter_pin);
                break;
        }
    }


    /***/
    private void checkFingurePrintAccessability() {
        FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!fingerprintManager.isHardwareDetected()) {
                // Device doesn't support fingerprint authentication
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
            } else {
                //showFingurePrintAccessDialog();
                if (iPinNumberPresenter.canShowFingerPrint()) {
                    rlFingerPrintContainer.setVisibility(View.VISIBLE);
                }
            }
    }

//    /***/
//    private void showFingurePrintAccessDialog() {
//        Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_fingureprintaccess);
//        dialog.findViewById(R.id.tv_alert_ok).setOnClickListener(view -> dialog.dismiss());
//        dialog.show();
//    }

    @Override
    public void clearPin() {
        Log.d(TAG, "Pin Cleared");
        vEtOne.setChecked(false);
        vEtTwo.setChecked(false);
        vEtThree.setChecked(false);
        vEtFour.setChecked(false);
        clearPinNumber();
        setScreenDetails(entryType);
    }

    @Override
    public void clearPinNumber() {
        finalNumber = null;
        mNumberView.clearPinNumber();
    }

    @Override
    public void setForgotPasswordEnabled(int visibility) {
        vTvForgotPassword.setVisibility(visibility);//visibility
    }

    @Override
    public void erasePinOnTry() {
        new Handler().postDelayed(() -> clearPin(), 500);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.fourthDigit:
                if (isChecked) {
                    doPerformNavigate(finalNumber);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_pass_code:
                navigateToForgotPin();
                break;
            case R.id.vBtnSkip:
                iPinNumberPresenter.updateFingerPrintState(false);//false
                rlFingerPrintContainer.setVisibility(View.GONE);
                break;
        }
    }

    /***/
    private void navigateToForgotPin() {
        Intent intent = new Intent(getActivity(), ForgotPinActivity.class);
        intent.putExtra(BUNDLE_ENTRY_TYPE, entryType);
        startActivity(intent);
        getActivity().finish();
    }

    /***/
    public interface AuthenticationListener {
        void setFirstEnteredPin(String pinValue);

        void loadFragment(int type);
    }

    private class NumberClickActivity implements NumberView.OnNumberListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onNumberButton(String newNumber) {
            Log.i(TAG, "onNumberButton" + newNumber);
            finalNumber = newNumber;
            if (newNumber.length() > 0) {
                if (newNumber.length() == 1) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(false);
                    vEtThree.setChecked(false);
                    vEtFour.setChecked(false);
                } else if (newNumber.length() == 2) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(true);
                    vEtThree.setChecked(false);
                    vEtFour.setChecked(false);
                } else if (newNumber.length() == 3) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(true);
                    vEtThree.setChecked(true);
                    vEtFour.setChecked(false);
                } else if (newNumber.length() == 4) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(true);
                    vEtThree.setChecked(true);
                    vEtFour.setChecked(true);
                }
            }
        }

        @Override
        public void onClearButton() {
            if (TextUtils.isEmpty(finalNumber)) {
                return;
            }

            if (finalNumber.length() > 0) {
                if (finalNumber.length() == 1) {
                    vEtOne.setChecked(false);
                    vEtTwo.setChecked(false);
                    vEtThree.setChecked(false);
                    vEtFour.setChecked(false);
                    finalNumber = "";
                } else if (finalNumber.length() == 2) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(false);
                    vEtThree.setChecked(false);
                    vEtFour.setChecked(false);
                    finalNumber = finalNumber.substring(0, 1);
                } else if (finalNumber.length() == 3) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(true);
                    vEtThree.setChecked(false);
                    vEtFour.setChecked(false);
                    finalNumber = finalNumber.substring(0, 2);
                } else if (finalNumber.length() == 4) {
                    vEtOne.setChecked(true);
                    vEtTwo.setChecked(true);
                    vEtThree.setChecked(true);
                    vEtFour.setChecked(false);
                    finalNumber = finalNumber.substring(0, 3);
                }

                mNumberView.setNumber(finalNumber);
            }

            Log.i(TAG, "onClearButton" + finalNumber);
        }
    }

}