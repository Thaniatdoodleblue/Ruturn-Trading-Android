package com.returntrader.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.common.ShakeMakeCompanyData;
import com.returntrader.presenter.ShakeMakeConfirmPresenter;
import com.returntrader.presenter.ipresenter.IShakeMakeConfirmPresenter;
import com.returntrader.view.iview.IShakeMakeConfirmView;
import com.returntrader.view.widget.confitte.ParticleSystem;

public class ShakeMakeConfirmActivity extends BaseActivity implements IShakeMakeConfirmView, View.OnClickListener {


    private IShakeMakeConfirmPresenter iShakeMakeConfirmPresenter;
    private Button vBtnShakeMakeConfirmBuy;
    private TextView vTvShakeAgain, vTvShakeMakeCompanyName, vTvShakeMakeMoney, vTvExit, vTvReset;
    private RelativeLayout vRlBgCompanyLayout, vRlGifContainer;
    private ShakeMakeCompanyData shakeMakeCompanyData;
    private ImageView vImgShakeMakeCompanyLogo, vImgGif;
    private MediaPlayer mp;
    private ViewTreeObserver vto;

    private boolean isConfiteeShowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_make_confirm);
        isConfiteeShowed = true;
        bindActivity();
        iShakeMakeConfirmPresenter = new ShakeMakeConfirmPresenter(this);
        iShakeMakeConfirmPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        vRlBgCompanyLayout = findViewById(R.id.rv_circle_company_bg);
        // vRlGifContainer = findViewById(R.id.cl_shake_make_container);
        vTvShakeAgain = findViewById(R.id.tv_shake_again);
        vBtnShakeMakeConfirmBuy = findViewById(R.id.btn_next_shake_make_confirm_buy);
        vImgGif = findViewById(R.id.image_gif);
        vTvExit = findViewById(R.id.txt_exit);
        vTvReset = findViewById(R.id.tv_reset);
        vto = vImgGif.getViewTreeObserver();
        vTvShakeMakeCompanyName = findViewById(R.id.tv_company_name);
        vImgShakeMakeCompanyLogo = findViewById(R.id.img_circle_company_logo);
        vTvShakeMakeMoney = findViewById(R.id.tv_money);
        vBtnShakeMakeConfirmBuy.setOnClickListener(this);
        vTvShakeAgain.setOnClickListener(this);
        vTvExit.setOnClickListener(this);
        vTvReset.setOnClickListener(this);
        /*mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);*/
        bindReceiver();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isConfiteeShowed){
            isConfiteeShowed = false;
            setConfitee();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: ");
    }

    private  ParticleSystem ps1, ps2, ps3, ps4, ps5, ps6, ps7, ps8, ps9, ps10, ps11, ps12, ps13;

    private void setConfitee() {
        Handler mHandler = new Handler();
        Runnable mRunnable = () -> {
            mp = new MediaPlayer();
            mp = MediaPlayer.create(this, R.raw.shake_make_confitee_sound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(mp -> {
                this.mp.start();
            });


            mp.setOnErrorListener((mp, what, extra) -> {
                Log.d(TAG, " MediaPlayer : Error" + what);
                return true;
            });

            mp.setOnCompletionListener(mp -> {
                this.mp.reset();
                this.mp.release();
                this.mp = null;
            });
            // mediaPlayer(Constants.MediaPlayer.START);
            // setConfiteeCentre();
            setConfiteeTop();
        };
        mHandler.postDelayed(mRunnable, 250);
    }


    private void setConfiteeCentre() {
        ps1 = new ParticleSystem(this, 100, R.drawable.ic_confitee_four, 3000);
        ps1.setScaleRange(0.7f, 1.3f);
        ps1.setSpeedRange(0.1f, 0.25f);
        ps1.setRotationSpeedRange(90, 180);
        ps1.setFadeOut(200, new AccelerateInterpolator());
        ps1.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps2 = new ParticleSystem(this, 100, R.drawable.ic_confitee_one, 3000);
        ps2.setScaleRange(0.7f, 1.3f);
        ps2.setSpeedRange(0.1f, 0.25f);
        ps2.setRotationSpeedRange(90, 180);
        ps2.setFadeOut(200, new AccelerateInterpolator());
        ps2.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps3 = new ParticleSystem(this, 100, R.drawable.ic_confitee_five, 3000);
        ps3.setScaleRange(0.7f, 1.3f);
        ps3.setSpeedRange(0.1f, 0.25f);
        ps3.setRotationSpeedRange(90, 180);
        ps3.setFadeOut(200, new AccelerateInterpolator());
        ps3.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps4 = new ParticleSystem(this, 100, R.drawable.ic_confitee_nine, 3000);
        ps4.setScaleRange(0.7f, 1.3f);
        ps4.setSpeedRange(0.1f, 0.25f);
        ps4.setRotationSpeedRange(90, 180);
        ps4.setFadeOut(200, new AccelerateInterpolator());
        ps4.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps5 = new ParticleSystem(this, 100, R.drawable.ic_confitee_eight, 3000);
        ps5.setScaleRange(0.7f, 1.3f);
        ps5.setSpeedRange(0.1f, 0.25f);
        ps5.setRotationSpeedRange(90, 180);
        ps5.setFadeOut(200, new AccelerateInterpolator());
        ps5.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps6 = new ParticleSystem(this, 100, R.drawable.ic_confitee_two, 3000);
        ps6.setScaleRange(0.7f, 1.3f);
        ps6.setSpeedRange(0.1f, 0.25f);
        ps6.setRotationSpeedRange(90, 180);
        ps6.setFadeOut(200, new AccelerateInterpolator());
        ps6.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps7 = new ParticleSystem(this, 100, R.drawable.ic_confitee_seven, 3000);
        ps7.setScaleRange(0.7f, 1.3f);
        ps7.setSpeedRange(0.1f, 0.25f);
        ps7.setRotationSpeedRange(90, 180);
        ps7.setFadeOut(200, new AccelerateInterpolator());
        ps7.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps8 = new ParticleSystem(this, 100, R.drawable.ic_confitee_three, 3000);
        ps8.setScaleRange(0.7f, 1.3f);
        ps8.setSpeedRange(0.1f, 0.25f);
        ps8.setRotationSpeedRange(90, 180);
        ps8.setFadeOut(200, new AccelerateInterpolator());
        ps8.oneShot(findViewById(R.id.emiter_top_left), 20);

        ps9 = new ParticleSystem(this, 100, R.drawable.ic_confitee_six, 3000);
        ps9.setScaleRange(0.7f, 1.3f);
        ps9.setSpeedRange(0.1f, 0.25f);
        ps9.setRotationSpeedRange(90, 180);
        ps9.setFadeOut(200, new AccelerateInterpolator());
        ps9.oneShot(findViewById(R.id.emiter_top_left), 20);
    }

    private void setConfiteeTop() {
        ps1 = new ParticleSystem(this, 100, R.drawable.ic_confitee_four, 3000);
        ps1.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);


        ps2 = new ParticleSystem(this, 100, R.drawable.ic_confitee_one, 3000);
        ps2.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps3 = new ParticleSystem(this, 100, R.drawable.ic_confitee_five, 3000);
        ps3.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps5 = new ParticleSystem(this, 100, R.drawable.ic_confitee_nine, 3000);
        ps5.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps6 = new ParticleSystem(this, 100, R.drawable.ic_confitee_eight, 3000);
        ps6.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps4 = new ParticleSystem(this, 100, R.drawable.ic_confitee_two, 3000);
        ps4.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps7 = new ParticleSystem(this, 100, R.drawable.ic_confitee_seven, 3000);
        ps7.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps8 = new ParticleSystem(this, 100, R.drawable.ic_confitee_six, 3000);
        ps8.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps9 = new ParticleSystem(this, 100, R.drawable.ic_confitee_three, 3000);
        ps9.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps10 = new ParticleSystem(this, 100, R.drawable.ic_confitee_ten, 3000);
        ps10.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps11 = new ParticleSystem(this, 100, R.drawable.ic_confitee_thirteen, 3000);
        ps11.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps12 = new ParticleSystem(this, 100, R.drawable.ic_confitee_eleven, 3000);
        ps12.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);

        ps13 = new ParticleSystem(this, 100, R.drawable.ic_confitee_twelve, 3000);
        ps13.setAcceleration(0.00013f, 90)
                .setScaleRange(0.7f, 1.3f)
                .setRotationSpeed(144)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(200, new AccelerateInterpolator())
                .emitWithGravity(findViewById(R.id.emiter_top_left), Gravity.BOTTOM, 7);
        setStopConfitte();
    }

    private void setStopConfitte() {
        Handler mHandler = new Handler();
        Runnable mRunnable = () -> {
            mediaPlayer(Constants.MediaPlayer.STOP);
            ps1.stopEmitting();
            ps2.stopEmitting();
            ps3.stopEmitting();
            ps4.stopEmitting();
            ps5.stopEmitting();
            ps6.stopEmitting();
            ps7.stopEmitting();
            ps8.stopEmitting();
            ps9.stopEmitting();
            ps10.stopEmitting();
            ps11.stopEmitting();
            ps12.stopEmitting();
            ps13.stopEmitting();
        };
        mHandler.postDelayed(mRunnable, 3000);
    }

    private void mediaPlayer(int value) {
        switch (value) {
            case Constants.MediaPlayer.CREATE:
                break;
            case Constants.MediaPlayer.START:

                break;
            case Constants.MediaPlayer.STOP:
                if (mp != null) {
                    mp.release();
                }
                break;
            case Constants.MediaPlayer.RESET:
                if (mp != null) {
                    mp.reset();
                } else {
                    Log.d(TAG, "mediaPlayer: Reset Error");
                }
                break;
            case Constants.MediaPlayer.DESTROY:
                if (mp != null) {
                    mp.stop();
                    mp.release();
                }
                break;


        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_shake_make_confirm_buy:
                getCodeSnippet().hideKeyboard(this);
                if (iShakeMakeConfirmPresenter.hasTransactionAccess()) {
                    if (getCodeSnippet().isGreaterZero(iShakeMakeConfirmPresenter.getLastKnownBalance())) {
                        if (getCodeSnippet().isGreaterThanBalance(iShakeMakeConfirmPresenter.getLastKnownBalance(), iShakeMakeConfirmPresenter.getShakeMakeMoneyData().getAmount())) {
                            /*14/8/16
                             *client asked to hide this conditions,
                             * he asked us to do the process even if the market is closed also.
                             * For that purpose dev has been hide this conditions.
                             * 21/8/16
                             * Again he asked to show the validation
                             * */
                            if (iShakeMakeConfirmPresenter.isMarketAvailable()) {
                                navigateToAskAuthentication();
                            } else {
                                iShakeMakeConfirmPresenter.setNavigatingToShakeMakeSuccess(getActivity().getString(R.string.txt_process_failed) );
                               // showMarketAvailabilityDialog(Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
                            }
                        } else {
                            showMessage(getString(R.string.txt_error_funds_low));
                        }
                    } else {
                        showMessage(getString(R.string.txt_error_funds_low));
                    }
                } else {
                    showMessage(getString(R.string.txt_doc_pending));
                }

              /*  Bundle bundle =  new Bundle();
                bundle.putParcelable(Constants.BundleKey.BUNDLE_SHAKE_MAKE_COMPANY_DATA,shakeMakeCompanyData);
                bundle.putString(Constants.BundleKey.BUNDLE_SHAKE_MAKE_PAYMENT_STATUS,getString(R.string.txt_process_failed));
                navigateToShakeMakeSuccess(bundle);*/

                break;
            case R.id.tv_shake_again:
                Log.d(TAG, "onClick: tv_shake_again");
                setResult(Activity.RESULT_OK, new Intent());
                finish();
                break;

            case R.id.tv_reset:
                Intent intent = new Intent(getActivity(), ShakeMakeGroupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.txt_exit:
                Intent intentExit = new Intent(getActivity(), HomeActivity.class);
                intentExit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentExit);
                finish();
                break;
        }
    }

    private void navigateToAskAuthentication() {
        iShakeMakeConfirmPresenter.setAuthNeeded();
        Intent intent = new Intent(getActivity(), AskAuthenticationActivity.class);
        startActivityForResult(intent, Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
    }

    @Override
    public void navigateToShakeMakeSuccess(Bundle bundle) {
        Intent intent = new Intent(getActivity(), ShakeMakeSuccessActivity.class);
        intent.putExtras(bundle);
        /*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);*/
        startActivity(intent);
        finish();
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
                    //  setUpTopBar();
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
                iShakeMakeConfirmPresenter.doDelayPriceUpdate();
            }
        }
    };

    @Override
    public void setShakeMakeCompanyData(ShakeMakeCompanyData shakeMakeCompanyData) {
        if (shakeMakeCompanyData != null) {
            setCompanyLogoSize();
            this.shakeMakeCompanyData = shakeMakeCompanyData;

            if (!TextUtils.isEmpty(shakeMakeCompanyData.getCompanyName())) {
                vTvShakeMakeCompanyName.setText(shakeMakeCompanyData.getCompanyName());
            }

            if (!TextUtils.isEmpty(iShakeMakeConfirmPresenter.getShakeMakeMoneyData().getAmount())) {
                vTvShakeMakeMoney.setText("R" + iShakeMakeConfirmPresenter.getShakeMakeMoneyData().getAmount());
            }


        }
    }

    /***/
    private void setCompanyLogoSize() {
        int[] shakeMakeImages = {R.drawable.shake_make_toy_one, R.drawable.shake_make_toy_two,
                R.drawable.shake_make_toy_three, R.drawable.shake_make_toy_four, R.drawable.shake_make_toy_five,
                R.drawable.shake_make_toy_six, R.drawable.shake_make_toy_seven, R.drawable.shake_make_toy_eight};

        AnimationDrawable anim = new AnimationDrawable();
        for (int shakeMakeImage : shakeMakeImages) {
            anim.addFrame(getResources().getDrawable(shakeMakeImage), 100);
        }
        //set ImageView to AnimatedDrawable
        vImgGif.setImageDrawable(anim);
        //if you want the animation to loop, set false
        anim.setOneShot(true);
        anim.start();
        vto.addOnGlobalLayoutListener(onGlobalLayoutListener);


    }

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (vto != null) {
                vRlBgCompanyLayout.setVisibility(View.VISIBLE);
                /*this for vImgGif */
                if (vImgGif.getMeasuredWidth() != 0 || vImgGif.getMeasuredHeight() != 0) {
                    Log.d(TAG, "onGlobalLayout: MeasuredHeight ");
                    setCompanyLogoPrefectSqaure(vImgGif.getMeasuredWidth(), vImgGif.getMeasuredHeight(), vImgGif);
                } else {
                    Log.d(TAG, "onGlobalLayout: Height ");
                    setCompanyLogoPrefectSqaure(vImgGif.getWidth(), vImgGif.getHeight(), vImgGif);
                }
                Log.d(TAG, "onGlobalLayout: vImgGif.getMeasuredWidth() =>" + vImgGif.getMeasuredWidth() + "  vImgGif.getHeight() =>" + vImgGif.getMeasuredHeight());
                Log.d(TAG, "onGlobalLayout: vImgGif.getWidth() =>" + vImgGif.getWidth() + "  vImgGif.getHeight() =>" + vImgGif.getHeight());
                vImgGif.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                Log.d(TAG, "onGlobalLayout: null");
            }
        }


    };

    private void setCompanyLogoPrefectSqaure(int measuredWidth, int measuredHeight, ImageView vImgGif) {
        int width = measuredWidth / 4;
        int height = measuredHeight / 4;
        int square;
        int marginBottom;
        if (height > width) {
            square = height;
            marginBottom = measuredHeight;
        } else {
            marginBottom = measuredWidth;
            square = width;

        }
        vRlBgCompanyLayout.getLayoutParams().width = width;
        vRlBgCompanyLayout.getLayoutParams().height = height;
        if (!TextUtils.isEmpty(shakeMakeCompanyData.getLogo())) {
            loadSquareImage(shakeMakeCompanyData.getLogo(),vImgShakeMakeCompanyLogo,vRlBgCompanyLayout);
            //  TraderApplication.getInstance().loadCircularImage(shakeMakeCompanyData.getLogo(), vImgShakeMakeCompanyLogo, vRlBgCompanyLayout);
        }
    }

    private void loadSquareImage(String logo, ImageView vImgShakeMakeCompanyLogo, RelativeLayout vRlBgCompanyLayout) {
        Glide.with(this)
                .load(logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .animate(R.anim.zoom_in_center)
                .into(vImgShakeMakeCompanyLogo);
    }

    private int getPixelValueFromDimension(int value) {
        Resources resources = this.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) value, getResources().getDisplayMetrics());
        return px;

    }


    private void showToyImage() {
        Glide.with(this)
                .load(R.drawable.shake_make_toy)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(vImgGif);
    }

    /***/
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBalanceUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer(Constants.MediaPlayer.STOP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mediaPlayer(Constants.MediaPlayer.RESET);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer(Constants.MediaPlayer.STOP);
        unregisterReceiver();
    }

    @Override
    public void doUpdateBalance() {
        syncAccount(Constants.AccountSync.SYNC_BALANCE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCodeSnippet().hideKeyboard(ShakeMakeConfirmActivity.this);
        iShakeMakeConfirmPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }


    public void getScreenSize() {
         /*int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        String toastMsgFroScreen;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsgFroScreen = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsgFroScreen = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsgFroScreen = "Small screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                toastMsgFroScreen = "Extra Large screen";
                break;
            default:
                toastMsgFroScreen = "Screen size is neither large, normal or small";
        }
        Toast.makeText(this, toastMsgFroScreen, Toast.LENGTH_LONG).show();*/

        String toastMsgForDensity;
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                toastMsgForDensity = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                toastMsgForDensity = "MDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                toastMsgForDensity = "HDPI";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                toastMsgForDensity = "XHDPI";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                toastMsgForDensity = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                toastMsgForDensity = "XXXHDPI";
                break;
            default:
                toastMsgForDensity = "Screen size is neither LDPI, MDPI, HDPI or XHDPI";
        }
        Toast.makeText(this, toastMsgForDensity, Toast.LENGTH_LONG).show();
    }
}