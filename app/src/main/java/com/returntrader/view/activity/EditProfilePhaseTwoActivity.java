package com.returntrader.view.activity;

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
import com.returntrader.library.Log;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.EditProfileProfilePhaseTwoPresenter;
import com.returntrader.presenter.ipresenter.IEditProfileProfilePhaseTwoPresenter;
import com.returntrader.view.iview.IEditProfilePhaseTwoView;

/**
 * Created by azeem on 10-Nov-17
 */

public class EditProfilePhaseTwoActivity extends BaseActivity implements View.OnClickListener, IEditProfilePhaseTwoView {


    private Spinner mSpinnerNationality;
    private Spinner mSpinnerResidence;
    private Spinner mSpinnerProvince;
    private EditText mPostalCode;
    private EditText mEtCityOfBirth;
    private EditText mEtTaxNumber;

    private Spinner mSpinnerIncome;
    private Spinner mSpinnerMaritalStatus;

    private IEditProfileProfilePhaseTwoPresenter iEditProfileProfilePhaseTwoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_two);
        bindActivity();
        iEditProfileProfilePhaseTwoPresenter = new EditProfileProfilePhaseTwoPresenter(this);
        iEditProfileProfilePhaseTwoPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_edit_profile_two);

        mPostalCode = findViewById(R.id.et_postal_code);
        mEtTaxNumber = findViewById(R.id.et_tax_number);
        mEtCityOfBirth = findViewById(R.id.et_city_of_birth);
        mSpinnerNationality = findViewById(R.id.spinner_nationality);
        mSpinnerResidence = findViewById(R.id.spinner_country_of_residence);
        mSpinnerProvince = findViewById(R.id.spinner_province);
        mSpinnerIncome = findViewById(R.id.spinner_income);
        mSpinnerMaritalStatus = findViewById(R.id.spinner_marital_status);

        mSpinnerNationality.setEnabled(false);
        mSpinnerResidence.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (validate()) {
                    String tax = TextUtils.isEmpty(mEtTaxNumber.getText().toString()) ? "" : mEtTaxNumber.getText().toString().trim();
                    iEditProfileProfilePhaseTwoPresenter.onClickSubmit(
                            mSpinnerProvince.getSelectedItemPosition(),
                            mPostalCode.getText().toString().trim(),
                            mSpinnerResidence.getSelectedItemPosition(),
                            mEtCityOfBirth.getText().toString().trim(),
                            mSpinnerNationality.getSelectedItemPosition(),
                            tax,
                            mSpinnerIncome.getSelectedItemPosition(),
                            mSpinnerMaritalStatus.getSelectedItemPosition());
                }
                break;
        }
    }


    private boolean validate() {


        if (mSpinnerProvince.getSelectedItemPosition() == 0) {
            showMessage(getString(R.string.txt_choose_province));
            return false;
        }

        if (mPostalCode.length() < Constants.Common.MAX_POSTAL_CODE_LENGTH) {
            showMessage(getString(R.string.txt_error_postal_code));
            return false;
        }


        if (mSpinnerResidence.getSelectedItemPosition() == 0) {
            showMessage(getString(R.string.txt_error_residence_country));
            return false;
        }

        if (mEtCityOfBirth.length() == 0) {
            showMessage(getString(R.string.txt_error_birth_city));
            return false;
        }

        if (mSpinnerNationality.getSelectedItemPosition() == 0) {
            showMessage(getString(R.string.txt_error_nationlity));
            return false;
        }

       /* if (mEtTaxNumber.length() <= 0) {
            showMessage(getString(R.string.txt_error_enter_tax_number));
            return false;
        }*/
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(EditProfilePhaseTwoActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void setUserData(FullRegistrationRequest fullRegistrationRequest) {
        if (!(TextUtils.isEmpty(fullRegistrationRequest.getTaxNumber()))) {
            mEtTaxNumber.setText(fullRegistrationRequest.getTaxNumber());
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getPinCode()))) {
            mPostalCode.setText(fullRegistrationRequest.getPinCode());
        }

        Log.d(TAG, "getCityName : " + fullRegistrationRequest.getBirthPlace().getCityName());
        if (fullRegistrationRequest.getBirthPlace() != null) {
            if (!(TextUtils.isEmpty(fullRegistrationRequest.getBirthPlace().getCityName()))) {
                mEtCityOfBirth.setText(fullRegistrationRequest.getBirthPlace().getCityName());
            }
        }

    }

    @Override
    public void setSpinnerAdapter(ArrayAdapter<String> arrayAdapter, int type) {
        int spinnerPosition = 0;
        switch (type) {
            case Constants.SpinnerType.TYPE_COUNTRY_OF_RESIDENCE:
                mSpinnerResidence.setAdapter(arrayAdapter);
                spinnerPosition = arrayAdapter.getPosition(iEditProfileProfilePhaseTwoPresenter.getSelectedResidence());
                mSpinnerResidence.setSelection(spinnerPosition);
                break;
            case Constants.SpinnerType.TYPE_PROVINCE:
                spinnerPosition = arrayAdapter.getPosition(iEditProfileProfilePhaseTwoPresenter.getSelectedProvince());
                mSpinnerProvince.setAdapter(arrayAdapter);
                mSpinnerProvince.setSelection(spinnerPosition);
                break;
            case Constants.SpinnerType.TYPE_NATIONALITY:
                spinnerPosition = arrayAdapter.getPosition(iEditProfileProfilePhaseTwoPresenter.getSelectedNationality());
                mSpinnerNationality.setAdapter(arrayAdapter);
                mSpinnerNationality.setSelection(spinnerPosition);
                break;
            case Constants.SpinnerType.TYPE_INCOME:
                spinnerPosition = arrayAdapter.getPosition(iEditProfileProfilePhaseTwoPresenter.getSelectedIncome());
                mSpinnerIncome.setAdapter(arrayAdapter);
                mSpinnerIncome.setSelection(spinnerPosition);
                break;
            case Constants.SpinnerType.TYPE_MARITAL_STATUS:
                spinnerPosition = arrayAdapter.getPosition(iEditProfileProfilePhaseTwoPresenter.getSelectedMaritalStatus());
                mSpinnerMaritalStatus.setAdapter(arrayAdapter);
                mSpinnerMaritalStatus.setSelection(spinnerPosition);
                break;

        }
    }

    @Override
    public void setUpdateResult(int requestCode) {
        setResult(requestCode);
        finish();
    }
}
