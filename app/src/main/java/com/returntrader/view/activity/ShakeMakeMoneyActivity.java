package com.returntrader.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.ShakeMakeMoneyAdapter;
import com.returntrader.adapter.ShakeMakeMoneyDescAdapter;
import com.returntrader.adapter.itemDecorator.SpacesItemDecoration;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.common.ShakeMakeMoneyData;
import com.returntrader.presenter.ShakeMakeMoneyPresenter;
import com.returntrader.presenter.ipresenter.IShakeMakeMoneyPresenter;
import com.returntrader.view.iview.IShakeMakeMoneyView;

import java.util.List;

public class ShakeMakeMoneyActivity extends BaseActivity implements IShakeMakeMoneyView, View.OnClickListener {

    private IShakeMakeMoneyPresenter iShakeMakeMoneyPresenter;
    private RecyclerView vRvMoney;
    private CardView vCardNext;
    private Button vBtnNext;
    private TextView vTvCancel;
    private ImageView vImgBackNavigation;
    private TextView vTvInfo;

    private MediaPlayer mp;

    private boolean isItemSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_make_money);
        bindActivity();
        iShakeMakeMoneyPresenter = new ShakeMakeMoneyPresenter(this);
        iShakeMakeMoneyPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        vTvInfo = findViewById(R.id.tv_info);
        vRvMoney = findViewById(R.id.recycler_shake_make_money);
        vCardNext = findViewById(R.id.card_next_shake_make_money);
        vBtnNext = findViewById(R.id.btn_next_shake_make_money);
        vTvCancel = findViewById(R.id.btn_cancel_shake_make_money);
        vImgBackNavigation = findViewById(R.id.img_back_shake_make_money);
        vImgBackNavigation.setOnClickListener(this);
        vTvInfo.setOnClickListener(this);
        vBtnNext.setOnClickListener(this);
        vTvCancel.setOnClickListener(this);
        mp = MediaPlayer.create(this, R.raw.shake_count);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);


        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp16);
        vRvMoney.addItemDecoration(new SpacesItemDecoration(2, spacingInPixels, false));
        vRvMoney.setLayoutManager(manager);

    }

    @Override
    public void setShakeMakeMoneyAdapter(ShakeMakeMoneyAdapter shakeMakeGroupAdapter) {
        vRvMoney.setAdapter(shakeMakeGroupAdapter);
    }

    @Override
    public void enableNextButton() {
        // mp.stop();
        isItemSelected = true;
        vBtnNext.setEnabled(true);
        vCardNext.setCardBackgroundColor(getResources().getColor(R.color.shakeMakePink));
    }

    @Override
    public void navigateToGetReadyShake() {
        startActivity(new Intent(this, ShakeMakeActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_shake_make_money:
                String transactionTaxCost = TraderApplication.getInstance().getTransactionTaxCost(iShakeMakeMoneyPresenter.getShakeMakeMoneyData().getAmount(), Constants.TransactionType.TRANSACTION_BUY);
                if (isItemSelected) {
                    if (getCodeSnippet().isGreaterZero(iShakeMakeMoneyPresenter.getLastKnownBalance())) {
                        if (getCodeSnippet().isGreaterThanBalance(iShakeMakeMoneyPresenter.getLastKnownBalance(), iShakeMakeMoneyPresenter.getShakeMakeMoneyData().getAmount())
                                && getCodeSnippet().isGreaterThanBalance(iShakeMakeMoneyPresenter.getLastKnownBalance(), transactionTaxCost)) {
                            iShakeMakeMoneyPresenter.onClickNext();
                        } else {
                            showMessage(getString(R.string.txt_error_funds_low));
                        }
                    } else {
                        showMessage(getString(R.string.txt_error_funds_low));
                    }
                } else
                    showMessage("Choose an amount");


                break;
            case R.id.btn_cancel_shake_make_money:
                onBackPressed();
                break;
            case R.id.img_back_shake_make_money:
                onBackPressed();
                break;
            case R.id.tv_info:
                iShakeMakeMoneyPresenter.showInfoAlert();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void setDialogMoneyDesc(List<ShakeMakeMoneyData> shakeMakeMoneyDataList) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_shake_make_money);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.getAttributes().windowAnimations = R.style.animationCentreZoomInZoomOut;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView rvDesc = dialog.findViewById(R.id.rv_money);
        TextView close = dialog.findViewById(R.id.tv_close);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDesc.setLayoutManager(manager);
        ShakeMakeMoneyDescAdapter shakeMakeMoneyDescAdapter = new ShakeMakeMoneyDescAdapter(shakeMakeMoneyDataList);
        rvDesc.setAdapter(shakeMakeMoneyDescAdapter);
        close.setOnClickListener(view -> {
            dialog.dismiss();
        });


    }

    @Override
    public void setSound() {
        if (mp != null) {
            this.mp.start();
        }
       /* mp.setOnPreparedListener(mp -> {
            this.mp.start();
        });
        mp.setOnErrorListener((mp, what, extra) -> {
            Log.d(TAG, " MediaPlayer : Error mp1" + what);
            return true;
        });
        mp.setOnCompletionListener(mp -> {
            if (mp != null) {
                this.mp.release();
                this.mp = null;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.shake_count);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mp != null) {
            mp.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
    }

}
