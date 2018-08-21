package com.returntrader.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.WindowManager;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.library.Log;
import com.returntrader.presenter.SplashPresenter;
import com.returntrader.presenter.ipresenter.ISplashPresenter;
import com.returntrader.sync.DelayPriceSyncService;
import com.returntrader.sync.SyncAccountDetailService;
import com.returntrader.view.iview.ISplashView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.branch.referral.Branch;

/**
 * Created by moorthy on 11/14/2017.
 */

public class SplashActivity extends BaseActivity implements ISplashView {

    private ISplashPresenter iSplashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setAttributes(attributes);
        iSplashPresenter = new SplashPresenter(this);
        iSplashPresenter.onCreatePresenter(getIntent().getExtras());
        printHashKey();
    }


    /**
     * Method that prints hash key.
     */
    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    TraderApplication.getInstance().getPackageName(),
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();
        branch.initSession((branchUniversalObject, linkProperties, error) -> {
            if (branchUniversalObject != null) {
                JSONObject dataSet = branchUniversalObject.convertToJson();
                if (dataSet != null) {
                    if (dataSet.has("+clicked_branch_link")) {
                        try {
                            boolean isFromDeepLinking = dataSet.getBoolean("+clicked_branch_link");
                            if (isFromDeepLinking) {
                                if (iSplashPresenter.isPartialAuthorizedUser()) {
                                    iSplashPresenter.setDeepLinkingEnabled(true);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, this.getIntent().getData(), this);
    }


    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }


    @Override
    public void navigateToNexScreen(long milliSeconds) {
        new Handler().postDelayed(() -> {
            Intent intent = iSplashPresenter.getNextScreenActivity();
            startActivity(intent);
            finishAffinity();
        }, milliSeconds);
    }

    @Override
    public void startStatusCheckService(int flag) {
        Intent updatePriceService = new Intent(getActivity(), SyncAccountDetailService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.AccountSync.BUNDLE_SYNC_TYPE, flag);
        updatePriceService.putExtras(bundle);
        startService(updatePriceService);
    }

    @Override
    public void startDelayPriceUpdateService() {
        Intent updatePriceService = new Intent(getActivity(), DelayPriceSyncService.class);
        startService(updatePriceService);
    }

}
