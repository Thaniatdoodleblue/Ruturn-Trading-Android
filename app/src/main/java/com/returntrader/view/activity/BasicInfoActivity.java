package com.returntrader.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.common.PhoneNumber;
import com.returntrader.model.common.UserInfo;
import com.returntrader.permissions.IPermissionHandler;
import com.returntrader.permissions.PermissionProducer;
import com.returntrader.permissions.RequestPermission;
import com.returntrader.presenter.BasicInfoPresenter;
import com.returntrader.presenter.ipresenter.IBasicInfoPresenter;
import com.returntrader.view.iview.IBasicInfoView;

public class BasicInfoActivity extends BaseActivity implements View.OnClickListener, IBasicInfoView, PermissionProducer, CompoundButton.OnCheckedChangeListener {


    private EditText vEtFirstName;
    private EditText vEtSurName;
    private CheckBox vCheckBoxTermsCondition;
    private CheckBox vCheckBoxTermsConditionPartner;
    private TextView vTvTermsCondition;
    private TextView vTvTermsConditionPartner;
    private ImageView vImageInfoTermsAndCondition;
    private Button vBtnNext;
    private Spinner vSpinnerTitle;
    private EditText vEtMobileNumber;
    private IBasicInfoPresenter iBasicInfoPresenter;
    private IPermissionHandler iPermissionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        bindActivity();
        iPermissionHandler = RequestPermission.newInstance(this);
        iBasicInfoPresenter = new BasicInfoPresenter(this);
        iBasicInfoPresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    private void requestSmsPermission() {
        if (getCodeSnippet().isAboveMarshmallow()) {
            iPermissionHandler.callSmsPermissionHandler();
        }
    }

    @Override
    public void onReceivedPermissionStatus(int code, boolean isGrated) {
        switch (code) {
            case IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS:
                doTriggerRegistration();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS, true);
                } else {
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS, false);
                }

            }
        }
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_user_detail);

        vBtnNext = findViewById(R.id.btn_next);
        vSpinnerTitle = findViewById(R.id.spinner_title);
        vEtMobileNumber = findViewById(R.id.et_mobile_number);
        vEtFirstName = findViewById(R.id.et_fist_name);
        vEtSurName = findViewById(R.id.et_surname);
        vTvTermsCondition = findViewById(R.id.tv_tc);
        vTvTermsConditionPartner = findViewById(R.id.tv_tc_partner);
        vCheckBoxTermsCondition = findViewById(R.id.checkbox_terms_and_conditions);
        vCheckBoxTermsConditionPartner = findViewById(R.id.checkbox_partner_terms);
        vImageInfoTermsAndCondition = findViewById(R.id.image_terms_and_conditions_info);


        vImageInfoTermsAndCondition.setOnClickListener(this);
        vBtnNext.setOnClickListener(this);
        vEtMobileNumber.addTextChangedListener(mobileNumberTextWatcher);
        vEtFirstName.addTextChangedListener(mobileNumberTextWatcher);
        vEtSurName.addTextChangedListener(mobileNumberTextWatcher);
        vSpinnerTitle.setOnItemSelectedListener(onItemSelectedListener);
        vCheckBoxTermsCondition.setOnCheckedChangeListener(this);
        vCheckBoxTermsConditionPartner.setOnCheckedChangeListener(this);

        //vEtFirstName.setFilters(new InputFilter[]{getCodeSnippet().getNumberFilter()});
        //vEtSurName.setFilters(new InputFilter[]{getCodeSnippet().getNumberFilter()});

        getCodeSnippet().makeLinks(vTvTermsCondition, new String[]{"Terms and Conditions"}, new ClickableSpan[]{
                onClickTermsConditions
        });
        getCodeSnippet().makeLinks(vTvTermsConditionPartner, new String[]{"Terms and Conditions"}, new ClickableSpan[]{
                onClickTermsConditionsPartner
        });



       /* vEtFirstName.setText("Azeem Kumar");
        vEtSurName.setText("Kumar");
        vEtMobileNumber.setText("0769046241");*/
    }

    ClickableSpan onClickTermsConditions = new ClickableSpan() {
        @Override
        public void onClick(View textView) {
            navigateToTermsCondition(getString(R.string.txt_menu_terms_conditions), getString(R.string.url_terms_condition));
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.GREEN);
        }
    };


    ClickableSpan onClickTermsConditionsPartner = new ClickableSpan() {
        @Override
        public void onClick(View textView) {
            navigateToTermsCondition(getString(R.string.txt_menu_terms_conditions_partner), getString(R.string.url_terms_condition_partner));
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.GREEN);
        }
    };

    @Override
    public void setUpTitleSpinner(ArrayAdapter<String> spinnerArrayAdapter) {
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vSpinnerTitle.setAdapter(spinnerArrayAdapter);
    }


    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) view).setTextColor(Color.WHITE); //Change selected item text color
            ((TextView) view).setTextSize(19); //Change selected item text color
            view.setBackgroundColor(ContextCompat.getColor(BasicInfoActivity.this, R.color.homePage)); //Change selected item background color
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields(true)) {
                    requestSmsPermission();
                }
                break;

            case R.id.image_terms_and_conditions_info:
                navigateToTermsCondition(getString(R.string.txt_menu_terms_conditions), getString(R.string.url_terms_condition));
                break;

            case R.id.image_terms_and_conditions_info_partner:
                navigateToTermsCondition(getString(R.string.txt_menu_terms_conditions_partner), getString(R.string.url_terms_condition_partner));
                break;
        }
    }

    /***/
    private void doTriggerRegistration() {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(vEtFirstName.getText().toString().trim());
        userInfo.setSurname(vEtSurName.getText().toString().trim());
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode("+27");
        phoneNumber.setPhoneNumber(vEtMobileNumber.getText().toString().trim());
        userInfo.setPhoneNumber(phoneNumber);
        iBasicInfoPresenter.onClickSubmit(userInfo, vSpinnerTitle.getSelectedItemPosition());
    }


    private void setNextButtonColor() {
        if (validateFields(false)) {
            vBtnNext.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            vBtnNext.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
        } else {
            vBtnNext.setTextColor(ContextCompat.getColor(this, R.color.homePage));
            vBtnNext.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    private boolean validateFields(boolean showMessage) {

        if (TextUtils.isEmpty(vEtFirstName.getText())) {
            if (showMessage) showMessage(getString(R.string.txt_error_firstname));
            return false;
        }


        if (TextUtils.isEmpty(vEtSurName.getText())) {
            if (showMessage) showMessage(getString(R.string.txt_error_surname));
            return false;
        }

        if (vEtMobileNumber.length() == 0 || vEtMobileNumber.length() < 10) {
            if (showMessage) {
                showMessage(getString(R.string.txt_error_your_cell_number_does_not_match));
                findViewById(R.id.tv_error_mobile_number).setVisibility(View.VISIBLE);
            }
            return false;
        } else {
            findViewById(R.id.tv_error_mobile_number).setVisibility(View.GONE);
        }

        if (!(vCheckBoxTermsCondition.isChecked())) {
            if (showMessage) showMessage(getString(R.string.txt_error_terms_condition));
            return false;
        }

        if (!(vCheckBoxTermsConditionPartner.isChecked())) {
            if (showMessage) showMessage(getString(R.string.txt_error_terms_condition));
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(BasicInfoActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }


    private TextWatcher mobileNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (vEtMobileNumber.getText().hashCode() == editable.hashCode() && vEtMobileNumber.isFocused()) {
                if (vEtMobileNumber.length() > 0)
                    findViewById(R.id.tv_error_mobile_number).setVisibility(View.GONE);
            }
            setNextButtonColor();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iBasicInfoPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(BasicInfoActivity.this);
        doubleBackToExit();
    }

    @Override
    public void navigateToVerifyOtp(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), OtpCodeVerifyActivity.class);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, Constants.RequestCodes.NAVIGATE_BASIC_INFO_TO_OTP_VERIFICATION);
    }

    public void navigateToTermsCondition(String title, String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, url);
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, title);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_BASIC_INFO_TO_OTP_VERIFICATION);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        setNextButtonColor();
    }

}
