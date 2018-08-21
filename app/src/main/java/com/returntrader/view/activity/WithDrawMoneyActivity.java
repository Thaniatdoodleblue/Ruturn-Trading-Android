package com.returntrader.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.common.WithDrawableBalanceData;
import com.returntrader.model.dto.request.WithDrawalRequest;
import com.returntrader.presenter.WithDrawPresenter;
import com.returntrader.presenter.ipresenter.IWithDrawPresenter;
import com.returntrader.view.iview.IWithDrawView;
import com.returntrader.view.widget.MoneyValueFilter;



/**
 * Created by moorthy on 8/2/2017.
 */

public class WithDrawMoneyActivity extends BaseActivity implements View.OnClickListener, IWithDrawView {
    private EditText mEtWithDrawMoneyInput;
    private EditText mEtReason;
    private TextView mTvTotalMoney,mTvUnsettledMoney,mTvNetSettledMoney,mTvWithDrawableMoney;
    private IWithDrawPresenter iWithDrawPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money);
        bindActivity();
        iWithDrawPresenter = new WithDrawPresenter(this);
        iWithDrawPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        attachKeyboardListeners();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_with_draw);


        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvTotalMoney = findViewById(R.id.tv_money);
        mTvUnsettledMoney = findViewById(R.id.tv_unsettled_money);
        mTvNetSettledMoney = findViewById(R.id.tv_netsettled_money);
        mTvWithDrawableMoney = findViewById(R.id.tv_withdrawable_money);

        mEtWithDrawMoneyInput = findViewById(R.id.tv_money_withdraw);
        mEtReason = findViewById(R.id.tv_reason);


        mEtReason.setSingleLine();
        mEtWithDrawMoneyInput.setFilters(new InputFilter[]{new MoneyValueFilter(2)});
      //  mEtWithDrawMoneyInput.addTextChangedListener(amountTextWatcher);

        mEtWithDrawMoneyInput.setSingleLine();
        mEtWithDrawMoneyInput.requestFocus();
        mEtWithDrawMoneyInput.setFilters(new InputFilter[]{new MoneyValueFilter(2)});

        bindReceiver();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_withdraw_all:
                //showNoticeDialog();
                if (getCodeSnippet().isGreaterZero(iWithDrawPresenter.getLastKnownBalance())) {
                    mEtWithDrawMoneyInput.setText(iWithDrawPresenter.getLastKnownBalance());
                } else {
                    showMessage(getString(R.string.txt_error_insufficient_funds));
                }
                break;
            case R.id.tv_submit:
                if (mEtWithDrawMoneyInput.length() > 0) {
                    if (getCodeSnippet().isGreaterZero(mEtWithDrawMoneyInput.getText().toString().trim())) {
                        if (getCodeSnippet().isGreaterThanBalance(iWithDrawPresenter.getLastKnownBalance(),mEtWithDrawMoneyInput.getText().toString())) {
                            iWithDrawPresenter.doAuthentication();
                        } else {
                            showMessage(getString(R.string.txt_error_insufficient_funds));
                        }
                    } else {
                        showMessage(getString(R.string.txt_error_withdraw));
                    }
                } else {
                    showMessage(getString(R.string.txt_error_withdraw));
                }
                break;
        }
    }

    @Override
    public void doAuthentication() {
        Intent intent = new Intent(this, AskAuthenticationActivity.class);
        startActivityForResult(intent, Constants.RequestCodes.WITHDRAWMONEY_AUTHREQUEST);
    }

    @Override
    public void showNoticeDialog() {
        mEtWithDrawMoneyInput.getText().clear();
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_withdraw_notice);
        dialog.findViewById(R.id.tv_aler_ok).setOnClickListener(view -> {
            dialog.dismiss();
            finish();
        });
        dialog.show();
    }

    /***/
    private void doRequestWithdrawal() {
        float i = Float.parseFloat(getCodeSnippet().getDecimalPoint(mEtWithDrawMoneyInput.getText().toString()));
        double j = Double.parseDouble(getCodeSnippet().getDecimalPoint(mEtWithDrawMoneyInput.getText().toString()));
        WithDrawalRequest request = new WithDrawalRequest();
        request.setAmount(String.valueOf(i * 100));
        request.setReason(mEtReason.getText().toString());
        iWithDrawPresenter.doRequestWithdrawal(request);
    }


    public void sendDocuments() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:" + getString(R.string.txt_email_report_bug));
            intent.setData(uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, "FICA Documents");
            startActivity(Intent.createChooser(intent, "Send via"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(WithDrawMoneyActivity.this);
                iWithDrawPresenter.setAuthPinRequired(false);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setAccountInfo(WithDrawableBalanceData balanceData) {
        mTvTotalMoney.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(balanceData.getAvailableCash())));
        mTvNetSettledMoney.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(balanceData.getNetSettledCash())));
        mTvUnsettledMoney.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(balanceData.getUnsettledCash())));
        mTvWithDrawableMoney.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(balanceData.getWithdrawableCash())));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCodes.WITHDRAWMONEY_AUTHREQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                doRequestWithdrawal();
            }
        }
    }

   /* private TextWatcher amountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int cursorPosition = mEtWithDrawMoneyInput.getSelectionEnd();
            String originalStr = mEtWithDrawMoneyInput.getText().toString();
            //To restrict only two digits after decimal place
            mEtWithDrawMoneyInput.setFilters(new InputFilter[]{new MoneyValueFilter(2)});

            try {
                mEtWithDrawMoneyInput.removeTextChangedListener(this);
                String value = mEtWithDrawMoneyInput.getText().toString();

                if (value != null && !value.equals("")) {
                    if (value.startsWith(".")) {
                        mEtWithDrawMoneyInput.setText("0.");
                    }
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        mEtWithDrawMoneyInput.setText("");
                    }


                    String str = mEtWithDrawMoneyInput.getText().toString().replaceAll(",", "");

                    if (!value.equals(""))
                        mEtWithDrawMoneyInput.setText(getDecimalFormattedString(str));

                    int diff = mEtWithDrawMoneyInput.getText().toString().length() - originalStr.length();

                    mEtWithDrawMoneyInput.setSelection(cursorPosition + diff);
                }
                mEtWithDrawMoneyInput.addTextChangedListener(this);
            } catch (Exception ex) {
                ex.printStackTrace();
                mEtWithDrawMoneyInput.addTextChangedListener(this);
            }
        }

    };*/


    @Override
    protected void onHideKeyboard() {
        super.onHideKeyboard();
        String value = mEtWithDrawMoneyInput.getText().toString();
        if (!TextUtils.isEmpty(value)) {
            float v = Float.parseFloat(value);
            mEtWithDrawMoneyInput.setText(getCodeSnippet().getDecimalValueFromString(v));
            Log.d(TAG, "onHideKeyboard: " + getCodeSnippet().getDecimalValueFromString(v));
        }
    }

    @Override
    protected void onShowKeyboard() {
        super.onShowKeyboard();
        Log.d(TAG, "onShowKeyboard: ");
        /*String value = mEtWithDrawMoneyInput.getText().toString();
        if (!TextUtils.isEmpty(value)) {
            float v = Float.parseFloat(value);
            mEtWithDrawMoneyInput.setText(getCodeSnippet().getDecimalValueFromString(v));
            Log.d(TAG, "onHideKeyboard: " + getCodeSnippet().getDecimalValueFromString(v));
        }*/
    }


    /***/
    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBalanceUpdateReceiver, intentFilter);

        IntentFilter holdingUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        holdingUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverUpdateHoldingInfo, holdingUpdateIntentFilter);
    }

    /***/
    private final BroadcastReceiver mBalanceUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            com.returntrader.library.Log.e(TAG, "Balance onReceive");
            switch (intent.getAction()) {
                case Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED:
                    iWithDrawPresenter.callWithDrawableBalanceApi();
                    break;
            }
        }
    };

    /***
     *
     */
    private final BroadcastReceiver bReceiverUpdateHoldingInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED)) {
                iWithDrawPresenter.doDelayPriceUpdate();
            }
        }
    };

    /***/
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBalanceUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void doUpdateBalance() {
        syncAccount(Constants.AccountSync.SYNC_BALANCE);
    }
}
