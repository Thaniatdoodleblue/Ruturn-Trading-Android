package com.returntrader.view.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.ShakeMakeGroupAdapter;
import com.returntrader.adapter.itemDecorator.SpacesItemDecoration;
import com.returntrader.presenter.ShakeMakeGroupPresenter;
import com.returntrader.presenter.ipresenter.IShakeMakeGroupPresenter;
import com.returntrader.view.iview.IShakeMakeGroupView;

public class ShakeMakeGroupActivity extends BaseActivity implements IShakeMakeGroupView, View.OnClickListener {

    private IShakeMakeGroupPresenter iShakeMakeGroupPresenter;
    private RecyclerView vRvGroup;
    private CardView vCardNext;
    private Button vBtnNext;
    private TextView vTvCancel;
    private ImageView vImgBackNavigation;

    private MediaPlayer mp;
    private boolean isItemSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_make_group);
        bindActivity();
        iShakeMakeGroupPresenter = new ShakeMakeGroupPresenter(this);
        iShakeMakeGroupPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        vRvGroup = findViewById(R.id.recycler_shake_make_group);
        vCardNext = findViewById(R.id.card_next_shake_make_group);
        vBtnNext = findViewById(R.id.btn_next_shake_make_group);
        vTvCancel = findViewById(R.id.btn_cancel_shake_make_group);
        vImgBackNavigation = findViewById(R.id.img_back_shake_make_group);
        vImgBackNavigation.setOnClickListener(this);
        vBtnNext.setOnClickListener(this);
        vTvCancel.setOnClickListener(this);
        mp = MediaPlayer.create(this, R.raw.shake_group_selection);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp16);
        vRvGroup.addItemDecoration(new SpacesItemDecoration(2, spacingInPixels, false));
        vRvGroup.setLayoutManager(manager);

    }

    @Override
    public void setGroupAdapter(ShakeMakeGroupAdapter shakeMakeGroupAdapter) {
        vRvGroup.setAdapter(shakeMakeGroupAdapter);
    }

    @Override
    public void enableNextButton() {
        // mp.stop();
        isItemSelected = true;
        vCardNext.setCardBackgroundColor(getResources().getColor(R.color.shakeMakePink));
    }

    @Override
    public void navigateToShakeMakeMoney() {
        startActivity(new Intent(this, ShakeMakeMoneyActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_shake_make_group:
                if (isItemSelected) {
                    iShakeMakeGroupPresenter.onClickNext();
                }
                else {
                    showMessage("Choose a group");
                }
                break;
            case R.id.btn_cancel_shake_make_group:
                onBackPressed();
                break;
            case R.id.img_back_shake_make_group:
                onBackPressed();
                break;
        }
    }

    @Override
    public void setSound() {
        if (mp != null) {
            this.mp.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.shake_group_selection);
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

    @Override
    public void onBackPressed() {
        doubleBackToExit();
    }
}
