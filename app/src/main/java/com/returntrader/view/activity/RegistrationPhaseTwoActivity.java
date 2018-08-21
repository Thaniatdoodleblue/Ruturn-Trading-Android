package com.returntrader.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.RegistrationPhaseTwoPresenter;
import com.returntrader.presenter.ipresenter.IRegistrationPhaseTwoPresenter;
import com.returntrader.view.iview.IRegistrationPhaseTwoView;

public class RegistrationPhaseTwoActivity extends BaseActivity implements View.OnClickListener, IRegistrationPhaseTwoView {

    private RadioGroup mRadioGroupGender;
    private EditText mEtComplexNumber;
    private EditText mEtComplexName;
    private EditText mEtStreetNumber;
    private EditText mEtStreetName;
    private EditText mEtSuburb;
    private EditText mEtCity;
    private EditText mEtPostalCode;
    private ImageButton ibGooglePlaces;
    private IRegistrationPhaseTwoPresenter iRegistrationPhaseTwoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phase_two);
        bindActivity();
        iRegistrationPhaseTwoPresenter = new RegistrationPhaseTwoPresenter(this);
        iRegistrationPhaseTwoPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_user_registration_two);

        mRadioGroupGender = findViewById(R.id.radio_gender);

        mEtComplexNumber = findViewById(R.id.et_complex_one);
        mEtComplexName = findViewById(R.id.et_complex_two);
        mEtStreetNumber = findViewById(R.id.et_street_number);
        mEtStreetName = findViewById(R.id.et_street_name);
        mEtSuburb = findViewById(R.id.et_area);
        mEtCity = findViewById(R.id.et_city);
        mEtPostalCode = findViewById(R.id.et_postal_code);

        ibGooglePlaces = findViewById(R.id.ibGooglePlaces);
        ibGooglePlaces.setOnClickListener(this);
    }


    private void onClickSubmit(boolean isSaveEnabled) {
        FullRegistrationRequest fullRegistrationRequest = new FullRegistrationRequest();
        fullRegistrationRequest.setGenderId(getGenderId());
        fullRegistrationRequest.setComplexApartmentNumber(mEtComplexNumber.getText().toString().trim());
        fullRegistrationRequest.setComplexApartmentName(mEtComplexName.getText().toString().trim());
        fullRegistrationRequest.setStreetNumber(mEtStreetNumber.getText().toString().trim());
        fullRegistrationRequest.setLocation(mEtStreetName.getText().toString().trim());
        fullRegistrationRequest.setArea(mEtSuburb.getText().toString());
        fullRegistrationRequest.setCity(mEtCity.getText().toString().trim());
        fullRegistrationRequest.setPinCode(mEtPostalCode.getText().toString().trim());
        iRegistrationPhaseTwoPresenter.onClickSubmit(fullRegistrationRequest, isSaveEnabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields()) {
                    onClickSubmit(false);
                }
                break;
            case R.id.btn_save_and_exit:
                onClickSubmit(true);
                break;
            case R.id.ibGooglePlaces:
                showLocationPicker();
                break;
        }
    }

    private boolean validateFields() {
        if (getGenderId() == -1) {
            showMessage(getString(R.string.txt_error_gender));
            return false;
        }

        if (!(TextUtils.isEmpty(mEtComplexNumber.getText()))) {
            if (TextUtils.isEmpty(mEtComplexName.getText())) {
                showMessage(getString(R.string.txt_error_enter_apartment_complex_name));
                return false;
            }
        }

        if (!(TextUtils.isEmpty(mEtComplexName.getText()))) {
            if (TextUtils.isEmpty(mEtComplexNumber.getText())) {
                showMessage(getString(R.string.txt_error_enter_apartment_complex_name));
                return false;
            }
        }

        if (TextUtils.isEmpty(mEtStreetNumber.getText())) {
            showMessage(getString(R.string.txt_error_street_number));
            return false;
        }

        if (TextUtils.isEmpty(mEtStreetName.getText())) {
            showMessage(getString(R.string.txt_error_street));
            return false;
        }

        if (TextUtils.isEmpty(mEtSuburb.getText())) {
            showMessage(getString(R.string.txt_error_suburb));
            return false;
        }

        if (TextUtils.isEmpty(mEtCity.getText())) {
            showMessage(getString(R.string.txt_error_city));
            return false;
        }

        if (TextUtils.isEmpty(mEtPostalCode.getText())) {
            showMessage(getString(R.string.txt_error_emptypostal_code));
            return false;
        }


        if (mEtPostalCode.length() < Constants.Common.MAX_POSTAL_CODE_LENGTH) {
            showMessage(getString(R.string.txt_error_postal_code));
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(RegistrationPhaseTwoActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    public int getGenderId() {
        if (mRadioGroupGender.getCheckedRadioButtonId() == R.id.radio_male) {
            return 1;
        } else if (mRadioGroupGender.getCheckedRadioButtonId() == R.id.radio_female) {
            return 0;
        }
        return -1;
    }

    @Override
    public void registrationSuccessful() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void setLocation(FullRegistrationRequest mLocationInfo) {
        mEtStreetNumber.setText(mLocationInfo.getStreetNumber());
        mEtStreetName.setText(mLocationInfo.getStreet());
        mEtSuburb.setText(mLocationInfo.getArea());
        mEtCity.setText(mLocationInfo.getCity());
        mEtPostalCode.setText(mLocationInfo.getPinCode());
    }


    @Override
    public void populateUserInformation(FullRegistrationRequest fullRegistrationRequest) {

        mRadioGroupGender.check(fullRegistrationRequest.getGenderId() == 0 ? R.id.radio_female : R.id.radio_male);

        mEtComplexNumber.setText(TextUtils.isEmpty(fullRegistrationRequest.getComplexApartmentNumber()) ? "" : fullRegistrationRequest.getComplexApartmentNumber());
        mEtComplexName.setText(TextUtils.isEmpty(fullRegistrationRequest.getComplexApartmentName()) ? "" : fullRegistrationRequest.getComplexApartmentName());
        mEtStreetNumber.setText(TextUtils.isEmpty(fullRegistrationRequest.getStreetNumber()) ? "" : fullRegistrationRequest.getStreetNumber());
        mEtStreetName.setText(TextUtils.isEmpty(fullRegistrationRequest.getLocation()) ? "" : fullRegistrationRequest.getLocation());
        mEtSuburb.setText(TextUtils.isEmpty(fullRegistrationRequest.getArea()) ? "" : fullRegistrationRequest.getArea());
        mEtCity.setText(TextUtils.isEmpty(fullRegistrationRequest.getCity()) ? "" : fullRegistrationRequest.getCity());
        mEtPostalCode.setText(TextUtils.isEmpty(fullRegistrationRequest.getPinCode()) ? "" : fullRegistrationRequest.getPinCode());
    }

    @Override
    public void navigateToPhaseThree(Bundle bundle) {
        Intent intent = new Intent(this, RegistrationPhaseThreeActivity.class);
        if (bundle != null) intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_REGISTRATION_PHASE_THREE);
    }

    @Override
    public void postResult(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    /***/
    private void showLocationPicker() {
        try {
            PlaceAutocomplete.IntentBuilder intentBuilder = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY);
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry(Constants.Common.DEFAULT_ISO_COUNTRY_CODE)
                    .build();
            intentBuilder.setFilter(typeFilter);
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            showMessage(message);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iRegistrationPhaseTwoPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
