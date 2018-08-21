package com.returntrader.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.model.common.ShakeMakeMoneyData;

public class ShakeMakeMoneyViewHolder extends BaseViewHolder<ShakeMakeMoneyData> implements View.OnClickListener {


    private TextView vTvShakeMakeMoneyContainer,vTvShakeMakeMoneyTopPick;
    private BaseRecyclerAdapterListener<ShakeMakeMoneyData> listener;


    public ShakeMakeMoneyViewHolder(View itemView, BaseRecyclerAdapterListener<ShakeMakeMoneyData> listener) {
        super(itemView);
        this.listener = listener;
        bindView();
    }

    private void bindView() {
        vTvShakeMakeMoneyContainer = itemView.findViewById(R.id.tv_shake_make_money_container);
        vTvShakeMakeMoneyTopPick = itemView.findViewById(R.id.tv_shake_make_money_top_pick);
        itemView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        listener.onClickItem(data, getAdapterPosition());
    }

    @Override
    void populateData(ShakeMakeMoneyData data) {

        vTvShakeMakeMoneyTopPick.setVisibility(data.getIsTopPick().equalsIgnoreCase("1")? View.VISIBLE: View.GONE);

        if(!TextUtils.isEmpty(data.getAmount())) {
            vTvShakeMakeMoneyContainer.setText("R"+data.getAmount());
        }

        if (data.isSelected()) {
            vTvShakeMakeMoneyContainer.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_background_filled_shakemake_pink) );
            vTvShakeMakeMoneyContainer.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
        } else {
            vTvShakeMakeMoneyContainer.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_background_line_shakemake_pink) );
            vTvShakeMakeMoneyContainer.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
        }
    }
}
