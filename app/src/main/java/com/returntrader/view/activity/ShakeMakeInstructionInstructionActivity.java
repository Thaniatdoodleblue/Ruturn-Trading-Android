package com.returntrader.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.ShakeMakeInstructionPresenter;
import com.returntrader.presenter.ipresenter.IShakeMakeInstructionPresenter;
import com.returntrader.view.iview.IShakeMakeInstructionView;

/**
 * Created by azeem on 05-Jul-18
 */
public class ShakeMakeInstructionInstructionActivity extends BaseActivity implements IShakeMakeInstructionView, View.OnClickListener {

    private IShakeMakeInstructionPresenter iShakeMakeInstructionPresenter;
    private AppCompatCheckBox vCheckBoxAgree;
    private Button vBtnTry;
    private TextView vTvCancel;
    private TextView vTvTeamsAndConditions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shakemake_instruction);
        bindActivity();
        iShakeMakeInstructionPresenter = new ShakeMakeInstructionPresenter(this);
        iShakeMakeInstructionPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        vCheckBoxAgree = findViewById(R.id.checkbox_agree_shake_me);
        vTvTeamsAndConditions = findViewById(R.id.tv_terms_conditions_link);
        vBtnTry = findViewById(R.id.btn_try_shake_me);
        vTvCancel = findViewById(R.id.btn_cancel_shake_me);
        vBtnTry.setOnClickListener(this);


        getCodeSnippet().makeLinks(vTvTeamsAndConditions, new String[]{"Terms and Conditions"}, new ClickableSpan[]{
                onClickTermsConditions
        });

        vTvCancel.setOnClickListener(this);
    }

    ClickableSpan onClickTermsConditions = new ClickableSpan() {
        @Override
        public void onClick(View textView) {
            navigateToTermsCondition(getString(R.string.txt_menu_terms_conditions), getString(R.string.url_terms_condition));
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(getCodeSnippet().getColor(R.color.darkGreen));
        }
    };


        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_try_shake_me:
                onTryClick();
                break;
            case R.id.btn_cancel_shake_me:
                onBackPressed();
                break;
        }
    }

    public void navigateToTermsCondition(String title, String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, url);
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, title);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void onTryClick() {
        if(vCheckBoxAgree.isChecked()){
            startActivity(new Intent(this,ShakeMakeGroupActivity.class));
            finish();

        }else {
            showMessage(getString(R.string.txt_error_terms_condition_shake_me));
        }
    }
}
