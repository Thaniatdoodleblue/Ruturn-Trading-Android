package com.returntrader.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.presenter.OtpCodeVerifyPresenter;
import com.returntrader.presenter.ipresenter.IOtpCodeVerifyPresenter;
import com.returntrader.view.iview.IOtpCodeVerifyView;
import com.returntrader.view.widget.CustomTextView;
import com.returntrader.view.widget.DrawableClickListener;


public class OtpCodeVerifyActivity extends BaseActivity implements IOtpCodeVerifyView, View.OnClickListener, DrawableClickListener {

    private Button vBtn;
    //    private EditText vEtOne, vEtTwo, vEtThree, vEtFour;
    private CustomTextView vTvAckText;
    private IOtpCodeVerifyPresenter iOtpCodeVerifyPresenter;
    private PinView pinView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verify);
        bindActivity();
        iOtpCodeVerifyPresenter = new OtpCodeVerifyPresenter(this);
        iOtpCodeVerifyPresenter.onCreatePresenter(getIntent().getExtras());
    }


    /***/
    public void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_verify_mobile_number);

        vBtn = findViewById(R.id.btn_next);

//        vEtOne = findViewById(R.id.firstDigit);
//        vEtTwo = findViewById(R.id.secondDigit);
//        vEtThree = findViewById(R.id.thirdDigit);
//        vEtFour = findViewById(R.id.fourthDigit);
        vTvAckText = findViewById(R.id.tv_ack_text);
        pinView = findViewById(R.id.pinView);

        vTvAckText.setDrawableClickListener(this);
        vBtn.setOnClickListener(this);

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pinView.getText().toString().length() == 4) {
                    performNext();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        addTextWatcher();
//        vEtFour.setOnEditorActionListener((v, actionId, event) -> {
//            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//                performNext();
//            }
//            return false;
//        });

        registerSmsReceiver();
    }

    /***/
    private void registerSmsReceiver() {
        IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        mIntentFilter.setPriority(999);
        registerReceiver(mSmsReceiver, mIntentFilter);
    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(this);
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    /***/
    void performNext() {
//        String mOtpValue = vEtOne.getText().toString().trim() + vEtTwo.getText().toString().trim() +
//                vEtThree.getText().toString().trim() + vEtFour.getText().toString().trim();
        String mOtpValue = pinView.getText().toString();

        if (TextUtils.isEmpty(mOtpValue)) {
            showMessage(getString(R.string.enter_valid_otp));
            return;
        }

        if (mOtpValue.length() == 4) {
            iOtpCodeVerifyPresenter.onClickVerifyOtp(mOtpValue);
        } else {
            showMessage(getString(R.string.enter_valid_otp));
        }
    }

    @Override
    public void setPhoneNumberText(String phoneNumer) {
        vTvAckText.setText(phoneNumer);
    }

    @Override
    public void setOtp(String otp) {

        if (!TextUtils.isEmpty(otp)) {
            // showMessage(otp);
//            vEtOne.setText(String.valueOf(otp.charAt(0)));
//            vEtTwo.setText(String.valueOf(otp.charAt(1)));
//            vEtThree.setText(String.valueOf(otp.charAt(2)));
//            vEtFour.setText(String.valueOf(otp.charAt(3)));
            pinView.setText(otp);
        }
    }

    @Override
    public void navigateToHome(Bundle bundle) {
        Intent intent = new Intent(this, HomeActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next:
                performNext();
                break;
            case R.id.tv_ack_text:
                //navigateToBasicInfo(null);
                break;
            case R.id.tv_resend_otp:
                pinView.getText().clear();
                iOtpCodeVerifyPresenter.onClickResendOtp();
                break;
        }
    }


//    /***/
//    private boolean isAllFieldsAreFilled() {
//        if (TextUtils.isEmpty(vEtOne.getText())) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(vEtTwo.getText())) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(vEtThree.getText())) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(vEtFour.getText())) {
//            return false;
//        }
//        return true;
//    }


    @Override
    public void navigateToEmailEntry(Bundle bundle) {
        Intent intent = new Intent(this, EmailEntryActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    /***/
    public void navigateToBasicInfo(Bundle bundle) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void navigateToRegistrationFirstActivity() {
        startActivity(new Intent(getApplicationContext(), RegistrationFirstActivity.class));
    }

//    /***/
//    private void addTextWatcher() {
//
//        vEtOne.addTextChangedListener(MyTextWatcher);
//        vEtTwo.addTextChangedListener(MyTextWatcher);
//        vEtThree.addTextChangedListener(MyTextWatcher);
//        vEtFour.addTextChangedListener(MyTextWatcher);
//
//       /* vEtOne.addTextChangedListener(OTPTextWatcher(null, vEtTwo));
//        vEtTwo.addTextChangedListener(OTPTextWatcher(vEtOne, vEtThree));
//        vEtThree.addTextChangedListener(OTPTextWatcher(vEtTwo, vEtFour));
//        vEtFour.addTextChangedListener(OTPTextWatcher(vEtThree, null));*/
//
//        vEtOne.setOnKeyListener(onKeyListener);
//        vEtTwo.setOnKeyListener(onKeyListener);
//        vEtThree.setOnKeyListener(onKeyListener);
//        vEtFour.setOnKeyListener(onKeyListener);
//    }


//    /***/
//    private View.OnKeyListener onKeyListener = (v, keyCode, event) -> {
//        if (TextUtils.isEmpty(((EditText) v).getText().toString())) {
//            if (keyCode == KeyEvent.KEYCODE_DEL) {
//                switch (v.getId()) {
//                    case R.id.firstDigit:
//                        Log.d(TAG, "onKey 1: edit_otp_one");
//                        break;
//                    case R.id.secondDigit:
//                        vEtOne.requestFocus();
//                        Log.d(TAG, "onKey 2: edit_otp_two");
//                        break;
//                    case R.id.thirdDigit:
//                        vEtTwo.requestFocus();
//                        Log.d(TAG, "onKey 3: edit_otp_three");
//                        break;
//                    case R.id.fourthDigit:
//                        vEtThree.requestFocus();
//                        Log.d(TAG, "onKey 4: edit_otp_four");
//                        break;
//                }
//            }
//        }
//        return false;
//    };

//    /***/
//    private TextWatcher OTPTextWatcher(final EditText etPrev, final EditText etNext) {
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 1) {
//                    if (etNext != null) {
//                        etNext.requestFocus();
//                        etNext.setSelection(etNext.getText().toString().trim().length());
//                    }
//                } /*else {
//                    if (etPrev != null) {
//                        //if (!(etPrev.getText().length() > 0)) {
//                        etPrev.requestFocus();
//                        etPrev.setSelection(etPrev.getText().toString().trim().length());
//                        //}
//                    }
//                }*/
//
//                if (isAllFieldsAreFilled()) {
//                    performNext();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        };
//    }

//    /***/
//    private TextWatcher MyTextWatcher = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            if (vEtOne.getText().hashCode() == s.hashCode()) {
//                if (vEtOne.getText().toString().length() == s.length())
//                    vEtTwo.requestFocus();
//                if (vEtOne.getText().toString().isEmpty()) vEtOne.requestFocus();
//            }
//            if (vEtTwo.getText().hashCode() == s.hashCode()) {
//                if (vEtTwo.getText().toString().length() == s.length())
//                    vEtThree.requestFocus();
//                if (vEtTwo.getText().toString().isEmpty()) vEtOne.requestFocus();
//            }
//            if (vEtThree.getText().hashCode() == s.hashCode()) {
//                if (vEtThree.getText().toString().length() == s.length())
//                    vEtFour.requestFocus();
//                if (vEtThree.getText().toString().isEmpty()) vEtTwo.requestFocus();
//            }
//            if (vEtFour.getText().hashCode() == s.hashCode()) {
//                if (vEtFour.getText().toString().isEmpty()) vEtThree.requestFocus();
//            }
//
//            if (isAllFieldsAreFilled()) {
//                performNext();
//            }
//        }
//    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSmsReceiver);
    }

    private BroadcastReceiver mSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            if (intent == null || intent.getAction() == null) {
                return;
            }
            if (intent.getAction().equalsIgnoreCase(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                Bundle data = intent.getExtras();
                Object[] pdus = (Object[]) data.get("pdus");
                if (pdus != null) {
                    for (int i = 0; i < pdus.length; i++) {
                        SmsMessage smsMessage = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = data.getString("format");
                            smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                        } else {
                            smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        }
                        String senderAddress = smsMessage.getDisplayOriginatingAddress();
                        String message = smsMessage.getDisplayMessageBody();
                        Log.d(TAG, "sender : " + senderAddress);
                        Log.d(TAG, "messageBody : " + message);
                        /*if (!senderAddress.toLowerCase().contains(Constants.Common.SMS_ORIGIN.toLowerCase())) {
                            return;
                        }*/

                        if (TextUtils.isEmpty(message)) {
                            return;
                        }

                        if (!(message.toLowerCase().contains(Constants.Common.SMS_ORIGIN.toLowerCase()))) {
                            return;
                        }

                        // verification code from sms
                        String verificationCode = getVerificationCode(message);
                        Log.d(TAG, "OTP received: " + verificationCode);
                        setOtp(verificationCode);
                    }
                }
            }
        }
    };

    /***/
    private String getVerificationCode(String message) {
        String code;
        /*int index = message.indexOf(Constants.Common.OTP_DELIMITER);
        if (index != -1) {
            int start = index + 1;
            int length = 4;
            code = message.substring(start, start + length);
            return code;
        }*/

        code = message.trim().substring(0, 4);
        return code;
    }

    @Override
    public void onClick(DrawablePosition target) {
        switch (target) {
            case RIGHT:
                navigateToBasicInfo(null);
                break;
        }
    }



    /*
    * used to reset the pin in-case of error response
    * */
    @Override
    public void resetPin() {
        pinView.getText().clear();
    }
}
