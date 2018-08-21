package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.EditProfileRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.EditProfileProfilePhaseOnePresenter;
import com.returntrader.presenter.ipresenter.IEditProfileProfilePhaseOnePresenter;
import com.returntrader.utils.DatePickerContext;
import com.returntrader.view.iview.IEditProfilePhaseOneView;

/**
 * Created by azeem on 10-Nov-17
 */

public class EditProfilePhaseOneActivity extends BaseActivity implements View.OnClickListener, IEditProfilePhaseOneView {
    private EditText vEtFirstName;
    private EditText vEtSurName;
    private Spinner mRadioGroupGender;
    private TextView mEtDateOfBirth;
    private EditText mEtEmail;
    private EditText mEtCellNumber;
    private EditText mEtRsaId;
    private Spinner mSpinnerRace;
    private EditText mEtComplexNumber;
    private EditText mEtComplexName;
    private EditText mEtStreetNumber;
    private DatePickerContext mDatePickerContext;
    private IEditProfileProfilePhaseOnePresenter iEditProfileProfilePhaseOnePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_one);
        bindActivity();
        iEditProfileProfilePhaseOnePresenter = new EditProfileProfilePhaseOnePresenter(this);
        iEditProfileProfilePhaseOnePresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_edit_profile_one);


        vEtFirstName = findViewById(R.id.et_fist_name);
        vEtSurName = findViewById(R.id.et_surname);
        mRadioGroupGender = findViewById(R.id.spinner_gender);
        mEtDateOfBirth = findViewById(R.id.et_date_of_birth);

        mEtCellNumber = findViewById(R.id.et_cell_number);

        mEtEmail = findViewById(R.id.et_email);
        mEtRsaId = findViewById(R.id.et_rsa_id_number);

        mSpinnerRace = findViewById(R.id.spinnerRace);
        mEtComplexNumber = findViewById(R.id.et_complex_one);
        mEtComplexName = findViewById(R.id.et_complex_two);
        //mEtPostalCode = ) findViewById(R.id.et_postal_code);
        mEtStreetNumber = findViewById(R.id.et_street_number);


        vEtFirstName.setEnabled(false);
        vEtSurName.setEnabled(true);
        mEtEmail.setEnabled(false);
        mEtRsaId.setEnabled(false);
        mEtCellNumber.setEnabled(false);
        mRadioGroupGender.setEnabled(false);
        mEtDateOfBirth.setEnabled(false);
        mSpinnerRace.setEnabled(false);

        mEtDateOfBirth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields()) {
                    EditProfileRequest editProfileRequest = new EditProfileRequest();
                    editProfileRequest.setStreet(mEtStreetNumber.getText().toString().trim());
                    editProfileRequest.setComplexApartmentNumberOne(mEtComplexNumber.getText().toString().trim());
                    editProfileRequest.setComplexApartmentNumberTwo(mEtComplexName.getText().toString().trim());
                    editProfileRequest.setRace(mSpinnerRace.getSelectedItem().toString());
                    iEditProfileProfilePhaseOnePresenter.onClickSubmit(editProfileRequest);
                }
                break;
            case R.id.et_date_of_birth:
                showDatePicker();
                break;
            case R.id.tv_edit_fields:
                navigateToUpdateRequest();
                break;

        }
    }

    private void navigateToUpdateRequest() {
        Intent intent = new Intent(this, UpdateRequestActivity.class);
        startActivity(intent);
    }

    private boolean validateFields() {


        if (TextUtils.isEmpty(vEtFirstName.getText())) {
            showMessage(getString(R.string.txt_error_first_name));
            return false;
        }


        if (TextUtils.isEmpty(vEtSurName.getText())) {
            showMessage(getString(R.string.txt_error_last_name));
            return false;
        }

        if (!(iEditProfileProfilePhaseOnePresenter.isDateSelected())) {
            showMessage(getString(R.string.txt_error_date_of_birth));
            return false;
        }

        if (mSpinnerRace.getSelectedItemPosition() == 0) {
            showMessage(getString(R.string.txt_error_race));
            return false;
        }

        if (mEtStreetNumber.length() <= 0) {
            showMessage(getString(R.string.txt_error_street));
            return false;
        }

        if (!(TextUtils.isEmpty(mEtComplexNumber.getText()))) {
            if (TextUtils.isEmpty(mEtComplexName.getText())) {
                showMessage(getString(R.string.txt_error_enter_unit_complex_name));
                return false;
            }
        }

        if (!(TextUtils.isEmpty(mEtComplexName.getText()))) {
            if (TextUtils.isEmpty(mEtComplexNumber.getText())) {
                showMessage(getString(R.string.txt_error_enter_unit_complex_name));
                return false;
            }
        }
        return true;
    }


    private void showDatePicker() {
        getDatePicker().showDatePicker(this, new DatePickerContext.OnDateSelectedListener() {
            @Override
            public void getSelectedDate(String date, long dateInMilliSeconds) {
                mEtDateOfBirth.setText(date);
                iEditProfileProfilePhaseOnePresenter.setDateOfBirth(date, dateInMilliSeconds);
            }

            @Override
            public void dialogDismiss() {

            }
        }, iEditProfileProfilePhaseOnePresenter.getBirthDateCalender());
    }

    private DatePickerContext getDatePicker() {
        if (mDatePickerContext == null) {
            mDatePickerContext = new DatePickerContext(this);
        }
        return mDatePickerContext;
    }


    @Override
    public void setBirthDate(String date) {
        mEtDateOfBirth.setText(date);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(EditProfilePhaseOneActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void navigateToPhaseTwo(Bundle bundle) {
        Intent intent = new Intent(this, EditProfilePhaseTwoActivity.class);
        if (bundle != null) intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_EDIT_PROFILE_PHASE_TWO);
    }


    @Override
    public void setUserData(FullRegistrationRequest fullRegistrationRequest) {
        if (fullRegistrationRequest == null) {
            return;
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getFirstName()))) {
            vEtFirstName.setText(fullRegistrationRequest.getFirstName());
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getLastName()))) {
            vEtSurName.setText(fullRegistrationRequest.getLastName());
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getDateOfBirth()))) {
            mEtDateOfBirth.setText(fullRegistrationRequest.getDateOfBirth());
        }

        if (fullRegistrationRequest.getPhoneNumber() != null) {
            if (!(TextUtils.isEmpty(fullRegistrationRequest.getPhoneNumber().getPhoneNumber()))) {
                int length = fullRegistrationRequest.getPhoneNumber().getPhoneNumber().length();
                String phoneNumber = "********" + fullRegistrationRequest.getPhoneNumber().getPhoneNumber().substring(length - 3, length);
                mEtCellNumber.setText(phoneNumber);
            }
        }


        if (!(TextUtils.isEmpty(fullRegistrationRequest.getIdentificationNumber()))) {
            int length = fullRegistrationRequest.getIdentificationNumber().length();
            String rsaNumber = "**********" + fullRegistrationRequest.getIdentificationNumber().substring(length - 3, length);
            mEtRsaId.setText(rsaNumber);
        }


        mRadioGroupGender.setSelection(fullRegistrationRequest.getGenderId() == 0 ? 1 : 0);

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getEmail()))) {
            String start = fullRegistrationRequest.getEmail().substring(0, 3);
            mEtEmail.setText(start + "******");
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getStreet()))) {
            mEtStreetNumber.setText(fullRegistrationRequest.getStreet());
        }
        if (!(TextUtils.isEmpty(fullRegistrationRequest.getComplexApartmentNumber()))) {
            mEtComplexNumber.setText(fullRegistrationRequest.getComplexApartmentNumber());
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getComplexApartmentName()))) {
            mEtComplexName.setText(fullRegistrationRequest.getComplexApartmentName());
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getRace()))) {
            ArrayAdapter arrayAdapter = (ArrayAdapter) mSpinnerRace.getAdapter();
            mSpinnerRace.setSelection(arrayAdapter.getPosition(fullRegistrationRequest.getRace()));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iEditProfileProfilePhaseOnePresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
