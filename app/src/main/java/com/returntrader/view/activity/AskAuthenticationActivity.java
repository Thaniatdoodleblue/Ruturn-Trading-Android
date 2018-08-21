package com.returntrader.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.AskAuthenticationPresenter;
import com.returntrader.presenter.ipresenter.IAskAuthenticationPresenter;
import com.returntrader.view.fragment.PinNumberFragment;
import com.returntrader.view.iview.IAskAuthenticationView;

/**
 * Created by moorthy on 9/20/2017.
 */

public class AskAuthenticationActivity extends BaseActivity implements IAskAuthenticationView, PinNumberFragment.AuthenticationListener {

    private FragmentManager mFragmentManager = null;
    private IAskAuthenticationPresenter iAskAuthenticationPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        bindActivity();
        iAskAuthenticationPresenter = new AskAuthenticationPresenter(this);
        iAskAuthenticationPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
//        toolbarTitle.setText("Enter your PIN to proceed");
        toolbarTitle.setText("Authenticate to proceed");
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                iAskAuthenticationPresenter.setAuthPinRequired(false);
                getCodeSnippet().hideKeyboard(AskAuthenticationActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFirstEnteredPin(String pinValue) {
        //// TODO: 9/20/2017 no need to do any functional code here
    }

    @Override
    public void loadFragment(int fragmentType) {

        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }

        switch (fragmentType) {
            case Constants.AuthenticationType.LOAD_AUTHENTICATE_PIN:
                mFragmentManager
                        .beginTransaction()
                        .replace(R.id.container_authentication, iAskAuthenticationPresenter.getAuthPinEntryFragment())
                        .commit();
                getCodeSnippet().showKeyboard(AskAuthenticationActivity.this);
                break;
            case Constants.AuthenticationType.LOAD_AUTHENTICATE_SUCCESS:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

}
