package com.returntrader.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.ShakeMakePresenter;
import com.returntrader.presenter.ipresenter.IShakeMakePresenter;
import com.returntrader.view.iview.IShakeMakeView;
import com.returntrader.view.widget.shakeDetector.ShakeDetector;

public class ShakeMakeActivity extends BaseActivity implements IShakeMakeView {

    private ImageView vImgCounterGif, vImgShakeMake;
    private IShakeMakePresenter iShakeMakePresenter;
    private boolean shakeMakeStatus = false;

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private SeekBar vseekBarShakeMake;
    private Handler seekHandler;
    private int currentProgress = 0;
    private MediaPlayer mp1, mp;
    private boolean shakeMakeSenorSoundFlag = false;
    private ImageView vImgBackNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_make);
        bindActivity();
        mp = MediaPlayer.create(this, R.raw.shake_sound);

        mp.setOnPreparedListener(mp -> {
            this.mp.start();
        });

        mp.setOnErrorListener((mp, what, extra) -> {
            Log.d(TAG, " MediaPlayer onError: mp");
            return false;
        });
        mp.setOnCompletionListener(mp -> {
            if (mp != null) {
                this.mp.release();
                this.mp = null;
            }
        });

        iShakeMakePresenter = new ShakeMakePresenter(this);
        iShakeMakePresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        shakeMakeSenorSoundFlag = false;
        vImgCounterGif = findViewById(R.id.gif);
        vImgShakeMake = findViewById(R.id.image_shake_make);
        vseekBarShakeMake = findViewById(R.id.seekBar);
        vImgBackNavigation = findViewById(R.id.img_back_shake_make);
        vImgBackNavigation.setOnClickListener(v -> {onBackPressed();});
        currentProgress = 0;
        vseekBarShakeMake.setProgress(currentProgress);
        startCounter();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        mShakeDetector.setOnShakeListener(count -> {
            if (!shakeMakeSenorSoundFlag) {
                mp1 = new MediaPlayer();
                mp1 = MediaPlayer.create(this, R.raw.shake_money_selection);
                mp1.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mp1.setOnPreparedListener(mp -> {
                    this.mp1.start();
                });
                mp1.setOnErrorListener((mp, what, extra) -> {
                    Log.d(TAG, " MediaPlayer : Error mp1" + what);
                    return true;
                });
                mp1.setOnCompletionListener(mp -> {
                    if (mp1 != null) {
                        this.mp1.release();
                        this.mp1 = null;
                    }
                });
                shakeMakeSenorSoundFlag = true;
            }
            seekHandler.post(seekRunnable);
        });

    }

    private void startCounter() {
        Glide.with(this)
                .load(R.drawable.ic_shake_make_timer)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(vImgCounterGif);
        Handler mHandler = new Handler();
        Runnable mRunnable = this::hideCounterView;
        mHandler.postDelayed(mRunnable, 2300);
    }

    private void hideCounterView() {
        if (mp != null) {
            mp.release();
        }
        shakeMakeStatus = true;
        vImgCounterGif.setVisibility(View.GONE);
        vImgShakeMake.setVisibility(View.VISIBLE);
        vseekBarShakeMake.setVisibility(View.VISIBLE);
        seekHandler = new Handler();
        vseekBarShakeMake.setOnTouchListener((view, motionEvent) -> true);
        setShakeDetector();
    }

    private Runnable seekRunnable = new Runnable() {
        @Override
        public void run() {
            currentProgress += 4;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                vseekBarShakeMake.setProgress(currentProgress, true);
            } else {
                vseekBarShakeMake.setProgress(currentProgress);
            }
            if (currentProgress != 100) {
                seekHandler.postDelayed(seekRunnable, 50);
                Log.d(TAG, "run: if");
            } else {
                seekHandler.removeCallbacks(seekRunnable);
                if (mp1 != null) {
                    mp1.release();
                }
                iShakeMakePresenter.getCompanyDetailsApi();
                Log.d(TAG, "run: else");
            }

        }
    };




    @Override
    public void navigateToShakeMakeConfirm(Bundle bundle) {
        Intent intent = new Intent(this, ShakeMakeConfirmActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.SHAKE_MAKE_SHAKE_AGAIN_REFRESH);

    }

    private void setShakeDetector() {
        // ShakeDetector initialization
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

    }


    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        if (shakeMakeStatus && mAccelerometer != null)
            mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        /*if (mp != null) {
            mp.reset();
        }
        if (mp1 != null) {
            mp1.reset();
        }*/
    }



    @Override
    public void onPause() {
        super.onPause();
        // Add the following line to unregister the Sensor Manager onPause
        if (mp != null) {
            mp.release();
        }
        if (mp1 != null) {
            mp1.release();
        }
        unregisterShakeListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
        }
        if (mp1 != null) {
            mp1.release();
        }
    }


    @Override
    public void unregisterShakeListener() {
        if (shakeMakeStatus)
            mSensorManager.unregisterListener(mShakeDetector);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.SHAKE_MAKE_SHAKE_AGAIN_REFRESH:
                if (resultCode == Activity.RESULT_OK) {
                    bindActivity();
                } else {
                    getActivity().onBackPressed();
                    finish();
                }
                break;
            default:
                getActivity().onBackPressed();
                finish();
                break;
        }
    }
}
