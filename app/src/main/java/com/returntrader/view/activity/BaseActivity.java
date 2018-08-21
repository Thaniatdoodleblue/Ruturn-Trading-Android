package com.returntrader.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.ExceptionTracker;
import com.returntrader.library.CustomException;
import com.returntrader.presenter.ipresenter.IPresenter;
import com.returntrader.sync.SyncAccountDetailService;
import com.returntrader.utils.CodeSnippet;
import com.returntrader.view.iview.IView;
import com.returntrader.view.widget.CustomProgressbar;


public abstract class BaseActivity extends AppCompatActivity implements IView {

    public String TAG = getClass().getSimpleName();
    private CustomProgressbar mCustomProgressbar;
    private CodeSnippet mCodeSnippet;
    private boolean doubleBackToExitPressedOnce = false;
    public String samplePublic;
    protected String sampleProtected;
    protected View mParentView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        LocaleHelper.setLocale(getActivity(), "en_US");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        mParentView = getWindow().getDecorView().findViewById(android.R.id.content);
        return super.onCreateView(name, context, attrs);
    }

    private CodeSnippet getCodeSnippetInstance() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(this);
        }
        return mCodeSnippet;
    }

    @Override
    public void showMessage(String message) {
        if (!(TextUtils.isEmpty(message))) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    protected ViewGroup getParentView() {
        return (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showMessage(CustomException e) {
        if (e != null) {
            showMessage(e.getException());
        }
    }

    @Override
    public void showProgressbar() {
        // TODO have to menu_view_question_preference the custom progressbar
       // if (getActivity() != null && !isFinishing()) {
            getProgressBar().show();
      //  }
    }

    @Override
    public void dismissProgressbar() {
// TODO dismiss the progressbar
        runOnUiThread(() -> {
            try {
               // if (getActivity() != null && !isFinishing()) {
                    getProgressBar().dismissProgress();
               // }
            } catch (Exception e) {
                ExceptionTracker.track(e);
            }
        });
    }

    @Override
    public FragmentActivity getActivity() {
        return BaseActivity.this;
    }

    @Override
    public void showSnackBar(String message) {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showNetworkMessage() {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, "No Network found!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.setAction(R.string.action_settings, view -> getCodeSnippet().showNetworkSettings());
            snackbar.show();
        }
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        return getCodeSnippetInstance();
    }

    @Override
    public void bindPresenter(IPresenter iPresenter) {

    }

    //protected abstract int getLayoutId();

    private CustomProgressbar getProgressBar() {
        if (mCustomProgressbar == null) {
            mCustomProgressbar = new CustomProgressbar(this);
        }
        return mCustomProgressbar;
    }

    protected void doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            getCodeSnippet().hideKeyboard(BaseActivity.this);
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showMessage("tap again to exit");
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    synchronized public void syncAccount(int syncType) {
        Intent updatePriceService = new Intent(getActivity(), SyncAccountDetailService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.AccountSync.BUNDLE_SYNC_TYPE, syncType);
        updatePriceService.putExtras(bundle);
        startService(updatePriceService);
    }

    /***/
    protected void showMarketAvailabilityDialog(int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
//        builder.setTitle(R.string.alert_title_suggestions);
        builder.setMessage(R.string.txt_alert_market_closed);
       /* builder.setPositiveButton(R.string.txt_procced, (dialog, which) -> {
            dialog.dismiss();
            navigateToAskAuthentication(requestCode);
        });*/
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }


    /***/
    private void navigateToAskAuthentication(int requestCode) {
        Intent intent = new Intent(getActivity(), AskAuthenticationActivity.class);
        startActivityForResult(intent, requestCode);
    }


    private boolean keyboardListenersAttached = false;

    protected void onShowKeyboard() {
    }

    protected void onHideKeyboard() {
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }
        getParentView().getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        keyboardListenersAttached = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboardListenersAttached) {
            getParentView().getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = () -> {
        Rect measureRect = new Rect();
        getParentView().getWindowVisibleDisplayFrame(measureRect);
        int keypadHeight = getParentView().getRootView().getHeight() - measureRect.bottom;
        if (keypadHeight > 0) {
            onShowKeyboard();
        } else {
            onHideKeyboard();
        }
    };
}


