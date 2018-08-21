package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.TraderApplication;
import com.returntrader.utils.CodeSnippet;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by moorthy on 8/10/2017.
 */

public class FollowerViewHolder extends BaseViewHolder<User> implements View.OnClickListener {


    private TextView mTvContactName;
    private ImageView mUserImage;
    private TextView mTvInvite;
    private BaseRecyclerAdapterListener<User> adapterListener;
    private CodeSnippet mCodeSnippet;

    public FollowerViewHolder(View itemView) {
        super(itemView);
        bindHolder();
    }

    public FollowerViewHolder(View itemView, BaseRecyclerAdapterListener<User> adapterListener, CodeSnippet mCodeSnippet) {
        super(itemView);
        this.adapterListener = adapterListener;
        this.mCodeSnippet = mCodeSnippet;
        bindHolder();
    }

    public FollowerViewHolder(View itemView, BaseRecyclerAdapterListener<User> adapterListener) {
        super(itemView);
        this.adapterListener = adapterListener;
        bindHolder();

    }

    private void bindHolder() {
        mTvContactName = (TextView) itemView.findViewById(R.id.tv_contact_name);
        mUserImage = (ImageView) itemView.findViewById(R.id.image_user_picture);
        mTvInvite = (TextView) itemView.findViewById(R.id.tv_invite);
        mTvInvite.setOnClickListener(this);
    }


    @Override
    void populateData(User data) {
        mTvContactName.setText(data.name);
        TraderApplication.getInstance().loadImage(data.profileImageUrlHttps,mUserImage,true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_invite:
                adapterListener.onClickItem(data,getAdapterPosition());
                break;
        }
    }
}
