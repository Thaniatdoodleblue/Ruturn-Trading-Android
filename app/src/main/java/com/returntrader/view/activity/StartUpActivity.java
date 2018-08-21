package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.returntrader.R;
import com.returntrader.presenter.StartUpPresenter;
import com.returntrader.presenter.ipresenter.IStartUpPresenter;
import com.returntrader.sync.DelayPriceSyncService;
import com.returntrader.view.iview.IStartUpView;

public class StartUpActivity extends BaseActivity implements View.OnClickListener, IStartUpView {


    private IStartUpPresenter iStartUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.activity_startup);
        iStartUpPresenter = new StartUpPresenter(this);
        iStartUpPresenter.onCreatePresenter(getIntent().getExtras());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                navigateToUserLogin();
                break;
            case R.id.tv_register:
                navigateToLauncher(null);
                break;
        }
    }

    @Override
    public void navigateToHome(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void navigateToLauncher(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), GuideActivity.class);
        startActivity(i);
    }

    @Override
    public void startDelayPriceUpdateService() {
        Intent updatePriceService = new Intent(getActivity(), DelayPriceSyncService.class);
        startService(updatePriceService);
    }

    @Override
    public void navigateToUserLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}
