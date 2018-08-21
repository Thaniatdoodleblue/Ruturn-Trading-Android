package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.model.common.ContactItem;
import com.returntrader.utils.CodeSnippet;

/**
 * Created by moorthy on 8/10/2017.
 */

public class ContactViewHolder extends BaseViewHolder<ContactItem> implements View.OnClickListener {


    private TextView mTvContactName;
    private TextView mTvContactNumber;
    private TextView mTvInvite;
    private ImageView mImageAppIcon;
    private BaseRecyclerAdapterListener<ContactItem> adapterListener;
    private CodeSnippet mCodeSnippet;

    public ContactViewHolder(View itemView) {
        super(itemView);
        bindHolder();
    }

    public ContactViewHolder(View itemView, BaseRecyclerAdapterListener<ContactItem> adapterListener, CodeSnippet mCodeSnippet) {
        super(itemView);
        this.adapterListener = adapterListener;
        this.mCodeSnippet = mCodeSnippet;
        bindHolder();
    }

    public ContactViewHolder(View itemView, BaseRecyclerAdapterListener<ContactItem> adapterListener) {
        super(itemView);
        this.adapterListener = adapterListener;
        bindHolder();

    }

    private void bindHolder() {
        mTvContactName = itemView.findViewById(R.id.tv_contact_name);
        mTvContactNumber = itemView.findViewById(R.id.tv_contact_number);
        mTvInvite = itemView.findViewById(R.id.tv_invite);
        mImageAppIcon = itemView.findViewById(R.id.image_app_icon);
        mTvInvite.setOnClickListener(this);
    }


    @Override
    void populateData(ContactItem data) {

        String contactName = mCodeSnippet.getContactName(data.getPhoneNumber());

        if (TextUtils.isEmpty(contactName)) {
            mTvContactName.setVisibility(View.GONE);
        } else {
            mTvContactName.setVisibility(View.VISIBLE);
            mTvContactName.setText(contactName);
        }

        if (!(TextUtils.isEmpty(data.getPhoneNumber()))) {
            mTvContactNumber.setText(data.getPhoneNumber());
        }

        if (data.getIsAppUser() == 0) {
            mImageAppIcon.setVisibility(View.INVISIBLE);
            mTvInvite.setVisibility(View.VISIBLE);
        } else {
            mImageAppIcon.setVisibility(View.VISIBLE);
            mTvInvite.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invite:
                adapterListener.onClickItem(data, getAdapterPosition());
                break;
        }
    }
}
