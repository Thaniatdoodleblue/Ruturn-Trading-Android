package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.RegistrationPhaseOnePresenter;
import com.returntrader.presenter.ipresenter.IRegistrationPhaseOnePresenter;
import com.returntrader.utils.DatePickerContext;
import com.returntrader.view.iview.IRegistrationPhaseOneView;

public class RegistrationPhaseOneActivity extends BaseActivity implements View.OnClickListener, IRegistrationPhaseOneView {

    private IRegistrationPhaseOnePresenter iRegistrationPhaseOnePresenter;

    private Spinner mSpinnerRace;
    //private Spinner mSpinnerCityOfBirth;
    private Spinner mSpinnerNationality;
    private Spinner mSpinnerResidence;
    private Spinner mSpinnerProvince;
    private LinearLayout mLinearLayoutDateOfBirth;
    private EditText mEtPhoneNumber;
    private EditText mEtCityOfBirth;
    private TextView mEtDateOfBirth;
    private DatePickerContext mDatePickerContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phase_one);
        bindActivity();
        iRegistrationPhaseOnePresenter = new RegistrationPhaseOnePresenter(this);
        iRegistrationPhaseOnePresenter.onCreatePresenter(getIntent().getExtras());

    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_user_registration_one);

        mSpinnerRace = findViewById(R.id.spinnerRace);
        mEtPhoneNumber = findViewById(R.id.et_phone_number);
        mEtCityOfBirth = findViewById(R.id.et_city_of_birth);
        mSpinnerNationality = findViewById(R.id.spinner_nationality);
        mSpinnerResidence = findViewById(R.id.spinner_country_of_residence);
        mSpinnerProvince = findViewById(R.id.spinner_province);
        mLinearLayoutDateOfBirth = findViewById(R.id.layout_date_of_birth);
        //mEtPhoneNumber =  findViewById(R.id.et_phone_number);
        mEtDateOfBirth = findViewById(R.id.et_date_of_birth);

        mLinearLayoutDateOfBirth.setOnClickListener(this);
        mEtDateOfBirth.setOnClickListener(this);

        mEtDateOfBirth.setEnabled(false);
        mSpinnerResidence.setEnabled(false);
        mSpinnerNationality.setEnabled(false);
        mLinearLayoutDateOfBirth.setEnabled(false);

    }

    private void submit(boolean isSaveEnabled) {
        iRegistrationPhaseOnePresenter.onClickSubmit(
                mSpinnerRace.getSelectedItem().toString().trim(),
                mEtCityOfBirth.getText().toString().trim(),
                mSpinnerNationality.getSelectedItemPosition(),
                mSpinnerResidence.getSelectedItemPosition(),
                mSpinnerProvince.getSelectedItemPosition()
                , isSaveEnabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields()) {
                    submit(false);
                }
                break;
            case R.id.btn_save_and_exit:
                submit(true);
                break;
            case R.id.layout_date_of_birth:
                showDatePicker();
                break;
            case R.id.et_date_of_birth:
                showDatePicker();
                break;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(RegistrationPhaseOneActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    private boolean validateFields() {
        if (!(iRegistrationPhaseOnePresenter.isDateSelected())) {
            showMessage(R.string.txt_error_date_of_birth);
            return false;
        }

        if (mSpinnerRace.getSelectedItemPosition() == 0) {
            showMessage(R.string.txt_error_race);
            return false;
        }

        if (mSpinnerResidence.getSelectedItemPosition() == 0) {
            showMessage(R.string.txt_error_residence_country);
            return false;
        }

        if (mEtCityOfBirth.length() == 0) {
            showMessage(R.string.txt_error_birth_city);
            return false;
        }

        if (mSpinnerNationality.getSelectedItemPosition() == 0) {
            showMessage(R.string.txt_error_nationlity);
            return false;
        }

        if (mSpinnerProvince.getSelectedItemPosition() == 0) {
            showMessage(R.string.txt_choose_province);
            return false;
        }
        return true;
    }


    private void showDatePicker() {
        getDatePicker().showDatePicker(this, new DatePickerContext.OnDateSelectedListener() {
            @Override
            public void getSelectedDate(String date, long dateInMilliSeconds) {
                mEtDateOfBirth.setText(date);
                iRegistrationPhaseOnePresenter.setDateOfBirth(date, dateInMilliSeconds);
            }

            @Override
            public void dialogDismiss() {

            }
        }, iRegistrationPhaseOnePresenter.getBirthDateCalender());
    }

    private DatePickerContext getDatePicker() {
        if (mDatePickerContext == null) {
            mDatePickerContext = new DatePickerContext(this);
        }
        return mDatePickerContext;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return;
        }

        mEtPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void setSpinnerAdapter(ArrayAdapter<String> arrayAdapter, int type) {


        int spinnerPosition = 0;
        switch (type) {
            case Constants.SpinnerType.TYPE_COUNTRY_OF_RESIDENCE:
                mSpinnerResidence.setAdapter(arrayAdapter);
                spinnerPosition = arrayAdapter.getPosition(iRegistrationPhaseOnePresenter.getSelectedResidence());
                mSpinnerResidence.setSelection(spinnerPosition);
                break;
            case Constants.SpinnerType.TYPE_PROVINCE:
                spinnerPosition = arrayAdapter.getPosition(iRegistrationPhaseOnePresenter.getSelectedProvince());
                mSpinnerProvince.setAdapter(arrayAdapter);
                mSpinnerProvince.setSelection(spinnerPosition);
                break;
            case Constants.SpinnerType.TYPE_NATIONALITY:
                spinnerPosition = arrayAdapter.getPosition(iRegistrationPhaseOnePresenter.getSelectedNationality());
                mSpinnerNationality.setAdapter(arrayAdapter);
                mSpinnerNationality.setSelection(spinnerPosition);
                break;
           /* case Constants.SpinnerType.TYPE_CITY_OF_BIRTH:
                mSpinnerCityOfBirth.updateInfoFromService(arrayAdapter);
                mSpinnerCityOfBirth.setSelection(spinnerPosition);
                break;*/

        }
    }

    @Override
    public void navigateToPhaseTwo(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), RegistrationPhaseTwoActivity.class);
        if (bundle != null) i.putExtras(bundle);
        startActivityForResult(i, Constants.RequestCodes.NAVIGATE_REGISTRATION_PHASE_TWO);
    }

    @Override
    public void setBirthDate(String date) {
        mEtDateOfBirth.setText(date);
    }

    @Override
    public void populateUserInfo(FullRegistrationRequest fullRegistrationRequest) {
        if (fullRegistrationRequest == null) {
            return;
        }

        if (fullRegistrationRequest.getBirthPlace() != null) {
            if (!(TextUtils.isEmpty(fullRegistrationRequest.getBirthPlace().getCityName()))) {
                mEtCityOfBirth.setText(fullRegistrationRequest.getBirthPlace().getCityName());
            }
        }

        if (fullRegistrationRequest.getDateOfBirth() != null) {
            setBirthDate(fullRegistrationRequest.getDateOfBirth());
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getRace()))) {
            ArrayAdapter arrayAdapter = (ArrayAdapter) mSpinnerRace.getAdapter();
            mSpinnerRace.setSelection(arrayAdapter.getPosition(fullRegistrationRequest.getRace()));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iRegistrationPhaseOnePresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
