package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.returntrader.R;
import com.returntrader.presenter.ShakeMakeSuccessPresenter;
import com.returntrader.presenter.ipresenter.IShakeMakeSuccessPresenter;
import com.returntrader.view.iview.IShakeMakeSuccessView;

public class ShakeMakeSuccessActivity extends BaseActivity implements IShakeMakeSuccessView, View.OnClickListener {

    private ImageView vImgShakeMakeCompanyLogo, vImgGif;
    private TextView vTvClose, vTvPaymentStatus;
    private Button vBtnPlayAgain;
    private RelativeLayout vRlBgCompanyLayout;
    private IShakeMakeSuccessPresenter iShakeMakeSuccessPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_make_success);
        bindActivity();
        iShakeMakeSuccessPresenter = new ShakeMakeSuccessPresenter(this);
        iShakeMakeSuccessPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        vTvPaymentStatus = findViewById(R.id.tv_order_status);
        vRlBgCompanyLayout = findViewById(R.id.rv_circle_company_bg);
        vImgGif = findViewById(R.id.image_gif);
        vTvClose = findViewById(R.id.tv_close);
        vBtnPlayAgain = findViewById(R.id.btn_next_shake_make_play_again);
        vImgShakeMakeCompanyLogo = findViewById(R.id.img_circle_company_logo);

        vTvClose.setOnClickListener(this);
        vBtnPlayAgain.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next_shake_make_play_again:
                Intent intent = new Intent(getActivity(), ShakeMakeGroupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.tv_close:
                close();
                break;
        }

    }

    private void close() {
        Intent intentExit = new Intent(getActivity(), HomeActivity.class);
        intentExit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentExit);
        finish();
    }


    @Override
    public void onBackPressed() {
        close();
    }

    /***/
    @Override
    public void setCompanyLogoSize() {
        ViewTreeObserver vto = vImgGif.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                vImgShakeMakeCompanyLogo.setVisibility(View.VISIBLE);
                if (vImgGif.getMeasuredWidth() != 0 || vImgGif.getMeasuredHeight() != 0) {
                    Log.d(TAG, "onGlobalLayout: MeasuredHeight ");
                    setCompanyLogoPrefectSqaure(vImgGif.getMeasuredWidth(), vImgGif.getMeasuredHeight());
                } else {
                    Log.d(TAG, "onGlobalLayout: Height ");
                    setCompanyLogoPrefectSqaure(vImgGif.getWidth(), vImgGif.getHeight());
                }
                Log.d(TAG, "onGlobalLayout: vImgGif.getMeasuredWidth() =>" + vImgGif.getMeasuredWidth() + "  vImgGif.getHeight() =>" + vImgGif.getMeasuredHeight());
                Log.d(TAG, "onGlobalLayout: vImgGif.getWidth() =>" + vImgGif.getWidth() + "  vImgGif.getHeight() =>" + vImgGif.getHeight());
                vImgGif.getViewTreeObserver().removeOnGlobalLayoutListener(this);


            }
        });


    }

    private void setCompanyLogoPrefectSqaure(int measuredWidth, int measuredHeight) {
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
        // vImgShakeMakeCompanyLogo.getLayoutParams().width = width;
        vRlBgCompanyLayout.getLayoutParams().height = height;
        vRlBgCompanyLayout.setVisibility(View.VISIBLE);
        //vImgShakeMakeCompanyLogo.getLayoutParams().height = height;
        loadImage();

    }

    private void loadImage() {
        if (vImgShakeMakeCompanyLogo != null)
            if (iShakeMakeSuccessPresenter.getShakeMakeCompanyData() != null) {
                if (iShakeMakeSuccessPresenter.getShakeMakeCompanyData().getLogo() != null && !TextUtils.isEmpty(iShakeMakeSuccessPresenter.getShakeMakeCompanyData().getLogo())) {
                    loadSquareImage(iShakeMakeSuccessPresenter.getShakeMakeCompanyData().getLogo(), vImgShakeMakeCompanyLogo, null);

              /*  Glide.with(getActivity())
                        .load(iShakeMakeSuccessPresenter.getShakeMakeCompanyData().getLogo())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .error(R.drawable.ic_background_circle_shakemake_pink)
                        .animate(R.anim.zoom_in_center)
                        .into(new BitmapImageViewTarget(vImgShakeMakeCompanyLogo) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                circularBitmapDrawable.setTargetDensity(resource.getDensity());
                                view.setImageDrawable(circularBitmapDrawable);
                            }

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                super.onResourceReady(resource, glideAnimation);
                            }
                        });*/
                }
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

    @Override
    public void paymentStatus(String statusMsg) {
        if (!TextUtils.isEmpty(statusMsg))
            vTvPaymentStatus.setText(statusMsg);
    }
}
