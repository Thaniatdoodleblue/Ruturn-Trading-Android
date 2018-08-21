package com.returntrader.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.common.ShakeMakeGroupData;

public class ShakeMakeGroupViewHolder extends BaseViewHolder<ShakeMakeGroupData> implements View.OnClickListener {


    private ImageView vImgViewShakeMakeGroup;
    private BaseRecyclerAdapterListener<ShakeMakeGroupData> listener;


    public ShakeMakeGroupViewHolder(View itemView, BaseRecyclerAdapterListener<ShakeMakeGroupData> listener) {
        super(itemView);
        this.listener = listener;
        bindView();
    }

    private void bindView() {
        vImgViewShakeMakeGroup = itemView.findViewById(R.id.image_shake_make_group);
        vImgViewShakeMakeGroup.setOnClickListener(this);

    }

    @Override
    void populateData(ShakeMakeGroupData data) {
        if (data.isSelected()) {
            TraderApplication.getInstance().loadImage(data.getImageSelected(), vImgViewShakeMakeGroup);
        } else {
            TraderApplication.getInstance().loadImage(data.getImageUnselected(), vImgViewShakeMakeGroup);
        }

        /*if(getAdapterPosition() % 2 == 0){
            vImgViewShakeMakeGroup.setPadding(0,0,32,16);
            Log.d(TAG, "populateData 0: " +data.getGroupName());
        }else {
            vImgViewShakeMakeGroup.setPadding(0,0,0,16);
            Log.d(TAG, "populateData !0: " +data.getGroupName());
        }*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_shake_make_group:
                listener.onClickItem(data,getAdapterPosition());
                break;
        }
    }
}
