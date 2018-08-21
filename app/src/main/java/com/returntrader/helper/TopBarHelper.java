package com.returntrader.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.TraderApplication;
import com.returntrader.library.Log;
import com.returntrader.model.common.TopBarData;
import com.returntrader.utils.CodeSnippet;

/**
 * Created by moorthy on 10/9/2017.
 */

public class TopBarHelper {

    private String TAG = getClass().getSimpleName();
    private Context mContext;
    private View itemView;
    private AppConfigurationManager mAppConfigurationManager;
    private TextView vTvHeaderCash;
    private LinearLayout vLayoutRoot;
    private ImageView vImageHeaderShowArrow;
    private TextView vTvHeaderInvestedAmount;
    private TextView vTvHeaderInvestedLabel;
    private CodeSnippet mCodeSnippet;


    public TopBarHelper(Context context, View rootView) {
        this.mContext = context;
        this.itemView = rootView;
        this.mAppConfigurationManager = new AppConfigurationManager(context);
        showTopBar();
    }

    /***/
    public void showTopBar() {
        vLayoutRoot = itemView.findViewById(R.id.layout_banner_details);
        vTvHeaderCash = itemView.findViewById(R.id.tv_header_cash);
        vImageHeaderShowArrow = itemView.findViewById(R.id.image_header_invested_indicator);
        vTvHeaderInvestedAmount = itemView.findViewById(R.id.tv_header_invested_amount);
        vTvHeaderInvestedLabel = itemView.findViewById(R.id.invested_label);
        setUpView();
    }

    /***/
    public void setUpView() {
        TopBarData topBarData = new TopBarData();
        topBarData.setLastKnownBalance(mAppConfigurationManager.getLastKnownBalance());
        topBarData.setInvestedAmount(mAppConfigurationManager.getLastKnownInvestedAmount());
        topBarData.setHoldingAmount(mAppConfigurationManager.getLastKnownHoldingAmount());
        setUpTopBar(topBarData);
        showLayout();
    }

    /***/
    private void showLayout() {
        if (!(mAppConfigurationManager.isPinAuthenticationRequired())) {
            show();
        } else {
            hide();
        }
    }

    /***/
    private void show() {
        vLayoutRoot.setVisibility(View.VISIBLE);
    }

    /***/
    private void hide() {
        vLayoutRoot.setVisibility(View.GONE);
    }

    /***/
    private CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(mContext);
        }
        return mCodeSnippet;
    }

    /***/
    private void setUpTopBar(TopBarData topBarData) {
        String lastKnowBalance = TextUtils.isEmpty(topBarData.getLastKnownBalance()) ? "0.0" : topBarData.getLastKnownBalance();
        String holdingAmnt = topBarData.getHoldingAmount();
        String investedAmount = TextUtils.isEmpty(topBarData.getInvestedAmount()) ? "0.0" : topBarData.getInvestedAmount();

        boolean isHigh = TraderApplication.getInstance().isInvestedHigh(holdingAmnt, investedAmount);

        vTvHeaderCash.setText(mContext.getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(lastKnowBalance)));

        if (isHigh) {
            // Show up arrow green
            vImageHeaderShowArrow.setImageResource(R.drawable.ic_arrow_drop_up_green);
            vTvHeaderInvestedAmount.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen));
            vTvHeaderInvestedLabel.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen));
        } else {
            // Shown down arrow red
            vImageHeaderShowArrow.setImageResource(R.drawable.ic_arrow_drop_down_red);
            vTvHeaderInvestedAmount.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            vTvHeaderInvestedLabel.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
        }
        vTvHeaderInvestedAmount.setText(mContext.getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(investedAmount)));
    }
}
