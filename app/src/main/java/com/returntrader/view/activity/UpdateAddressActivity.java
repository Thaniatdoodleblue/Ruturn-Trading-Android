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
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.AddressRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.UpdateAddressPresenter;
import com.returntrader.presenter.ipresenter.IUpdateAddressPresenter;
import com.returntrader.view.iview.IUpdateAddressView;

public class UpdateAddressActivity extends BaseActivity implements View.OnClickListener, IUpdateAddressView {

    private EditText mEtComplexNumber;
    private EditText mEtComplexName;
    private EditText mEtStreetNumber;
    private EditText mEtStreetName;
    private EditText mEtSuburb;
    private EditText mEtCity;
    private EditText mEtPostalCode;
    private ImageButton ibGooglePlaces;

    private IUpdateAddressPresenter iUpdateAddressPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        bindActivity();
        iUpdateAddressPresenter = new UpdateAddressPresenter(this);
        iUpdateAddressPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_update_address);

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


    private void onClickSubmit() {
        if (validateFields()) {
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setComplexApartmentNumberOne(mEtComplexNumber.getText().toString().trim());
            addressRequest.setComplexApartmentNumberTwo(mEtComplexName.getText().toString().trim());
            addressRequest.setStreetNumber(mEtStreetNumber.getText().toString().trim());
            addressRequest.setStreet(mEtStreetName.getText().toString().trim());//Street Name
            addressRequest.setLocation(mEtStreetName.getText().toString().trim());//Street Name
            addressRequest.setArea(mEtSuburb.getText().toString());
            addressRequest.setCity(mEtCity.getText().toString().trim());
            addressRequest.setPinCode(mEtPostalCode.getText().toString().trim());
            iUpdateAddressPresenter.onClickSubmit(addressRequest);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                onClickSubmit();
                break;
            case R.id.ibGooglePlaces:
                showLocationPicker();
                break;
        }
    }

    /***/
    private boolean validateFields() {
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
                getCodeSnippet().hideKeyboard(UpdateAddressActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void addressUpdatedSuccessfully() {
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
        mEtComplexNumber.setText(TextUtils.isEmpty(fullRegistrationRequest.getComplexApartmentNumber()) ? "" : fullRegistrationRequest.getComplexApartmentNumber());
        mEtComplexName.setText(TextUtils.isEmpty(fullRegistrationRequest.getComplexApartmentName()) ? "" : fullRegistrationRequest.getComplexApartmentName());
        mEtStreetNumber.setText(TextUtils.isEmpty(fullRegistrationRequest.getStreetNumber()) ? "" : fullRegistrationRequest.getStreetNumber());
        mEtStreetName.setText(TextUtils.isEmpty(fullRegistrationRequest.getLocation()) ? "" : fullRegistrationRequest.getLocation());
        mEtSuburb.setText(TextUtils.isEmpty(fullRegistrationRequest.getArea()) ? "" : fullRegistrationRequest.getArea());
        mEtCity.setText(TextUtils.isEmpty(fullRegistrationRequest.getCity()) ? "" : fullRegistrationRequest.getCity());
        mEtPostalCode.setText(TextUtils.isEmpty(fullRegistrationRequest.getPinCode()) ? "" : fullRegistrationRequest.getPinCode());
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
        iUpdateAddressPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
