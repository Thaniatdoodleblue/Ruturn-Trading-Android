package com.returntrader.adapter.viewholder;


import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by aksha on 23-05-2017.
 */

public class ParentOptionViewHolder extends GroupViewHolder {
    private TextView tvParentOptionView;
    private ImageView ivImageViewExpand, ivimageViewCollapse;

    public ParentOptionViewHolder(View itemView) {
        super(itemView);
        tvParentOptionView = (TextView) itemView.findViewById(R.id.ivParentOptionId);
        ivImageViewExpand = (ImageView) itemView.findViewById(R.id.ivExpand);
        ivimageViewCollapse = (ImageView) itemView.findViewById(R.id.ivCollapse);


    }

    public void setParentOptionName(String name) {
        tvParentOptionView.setText(name);
    }


    @Override
    public void expand() {
        super.expand();

    }

    @Override
    public void collapse() {
        super.collapse();

    }


    private void animateExpand() {
        final Animation myRotation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.rotation);

        ivImageViewExpand.startAnimation(myRotation);
    }

    private void animateCollapse() {
        final Animation myRotationReverse = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.reverse_rotation);

        ivImageViewExpand.setAnimation(myRotationReverse);
    }
}
