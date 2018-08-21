package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.NotificationMeta;
import com.returntrader.model.common.Report;

/**
 * Created by moorthy on 7/26/2017.
 */

public class NotificationViewHolder extends BaseViewHolder<NotificationMeta> implements View.OnClickListener {


    private TextView mTvContent;
    private TextView mTvTitle;
    private TextView mTvWebLink;
    private ImageView mImageBanner;
    private BaseRecyclerAdapterListener<NotificationMeta> iNotificationOnClickListener;


    public NotificationViewHolder(View itemView, BaseRecyclerAdapterListener<NotificationMeta> iNotificationOnClickListener) {
        super(itemView);
        this.iNotificationOnClickListener = iNotificationOnClickListener;
        bindHolder();

    }

    private void bindHolder() {
        mImageBanner = itemView.findViewById(R.id.image_banner);
        mTvContent = itemView.findViewById(R.id.tv_content);
        mTvTitle = itemView.findViewById(R.id.tv_title);
        mTvWebLink = itemView.findViewById(R.id.tv_link);
        itemView.setOnClickListener(this);
    }

    @Override
    void populateData(NotificationMeta data) {
        if (!(TextUtils.isEmpty(data.getContent()))) {
            mTvContent.setText(data.getContent());
        }

        if (!(TextUtils.isEmpty(data.getTitle()))) {
            mTvTitle.setText(data.getTitle());
        }

        if (!(TextUtils.isEmpty(data.getWebLink()))) {
            mTvWebLink.setVisibility(View.VISIBLE);
            mTvWebLink.setText("click here: " + data.getWebLink());
        } else {
            mTvWebLink.setVisibility(View.GONE);
        }

        if (!(TextUtils.isEmpty(data.getImageLink()))) {
            mImageBanner.setVisibility(View.VISIBLE);
            TraderApplication.getInstance().loadImage(data.getImageLink(), mImageBanner);
        } else {
            mImageBanner.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_link) {
            if (iNotificationOnClickListener != null) {
                iNotificationOnClickListener.onClickItem(data, getAdapterPosition());
            }
        }
    }
}
