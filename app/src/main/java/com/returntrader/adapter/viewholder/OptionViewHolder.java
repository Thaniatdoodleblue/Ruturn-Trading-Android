package com.returntrader.adapter.viewholder;


import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.ISettingsAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.model.common.Option;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by aksha on 23-05-2017.
 */

public class OptionViewHolder extends ChildViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private TextView tvOptionView;
    private Switch switchSettingsView;
    private ImageView ivRightGrayArrow;
    private ISettingsAdapterListener iSettingsAdapterListener;
    private Option mOption;


    public OptionViewHolder(View view, ISettingsAdapterListener iSettingsAdapterListener) {
        super(view);
        this.iSettingsAdapterListener = iSettingsAdapterListener;
        bindViewHolder();
    }

    private void bindViewHolder() {
        tvOptionView = itemView.findViewById(R.id.tvOptionId);
        switchSettingsView = itemView.findViewById(R.id.switchId);
        ivRightGrayArrow = itemView.findViewById(R.id.ivRightArrowGray);
        //  vOption = (View) itemView.findViewById(R.id.viewOptions);

        switchSettingsView.setOnCheckedChangeListener(this);
        itemView.setOnClickListener(this);
    }

    public void setOptionName(String parentName, Option option) {
        this.mOption = option;
        tvOptionView.setText(option.getName());
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));

        switch (getParentType(parentName)) {
            case 0:
                disableSwitch();
                break;
            case 1:
                disableSwitch();
                break;
            case 2:
                if (option.getName().equalsIgnoreCase(itemView.getResources().getString(R.string.log_out))) {
                    disableArrowAndSwitch();
                } else {
                    disableArrow();
                }
                break;
        }

        int itemType = mOption.getItemType();
        if (itemType == Constants.Settings.LANGUAGE || itemType == Constants.Settings.SARS ||
                itemType == Constants.Settings.SEND_MONEY || itemType == Constants.Settings.SEND_SHARES ||
                itemType == Constants.Settings.FRIEND || itemType == Constants.Settings.SOCIAL ||
                itemType == Constants.Settings.SOUND) {
            tvOptionView.setTextColor(Color.parseColor("#999999"));
            switchSettingsView.setChecked(false);
            switchSettingsView.setEnabled(false);
        } else {
            if (option.getName().equalsIgnoreCase(itemView.getResources().getString(R.string.log_out))) {
                tvOptionView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.homePage));
            } else {
                tvOptionView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
            }
            switchSettingsView.setEnabled(true);
        }
        switchSettingsView.setChecked(option.isChecked());
    }

    private int getParentType(String parentName) {
        if (TextUtils.isEmpty(parentName)) {
            return -1;
        }
        switch (parentName) {
            case "General":
                return 0;
            case "Social":
                return 1;
            case "Settings":
                return 2;
        }
        return 0;
    }

    private void disableArrow() {
//        tvOptionView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorBlack));
//        itemView.setBackgroundColor(0);
        tvOptionView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        ivRightGrayArrow.setVisibility(View.GONE);
        switchSettingsView.setVisibility(View.VISIBLE);
    }

    private void disableArrowAndSwitch() {
//        tvOptionView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorWhite));
//        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorTextBlue));
        tvOptionView.setGravity(Gravity.CENTER);
        ivRightGrayArrow.setVisibility(View.GONE);
        switchSettingsView.setVisibility(View.GONE);
    }

    private void disableSwitch() {
//        tvOptionView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorBlack));
//        itemView.setBackgroundColor(0);
        tvOptionView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        switchSettingsView.setVisibility(View.GONE);
        ivRightGrayArrow.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChcked) {
        mOption.setChecked(isChcked);
        iSettingsAdapterListener.onClickSettings(mOption, getAdapterPosition());
    }

    @Override
    public void onClick(View view) {
        switch (getParentType(mOption.getName())) {
            case 0:
                iSettingsAdapterListener.onClickGeneral(mOption, getAdapterPosition());
                break;
            case 1:
                iSettingsAdapterListener.onClickGeneral(mOption, getAdapterPosition());
                break;

        }
    }
}

