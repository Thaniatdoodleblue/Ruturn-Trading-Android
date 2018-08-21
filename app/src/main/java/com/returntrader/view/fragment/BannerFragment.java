package com.returntrader.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.common.BannerItem;

/**
 * Created by moorthy on 8/26/2017
 */

public class BannerFragment extends BaseFragment implements View.OnClickListener {

    private BannerItem mBannerItem = null;
    private ImageView mImageClose;
    private ImageView mImageBanner;

    /*public static BannerFragment newInstance(Bundle bundle) {
        BannerFragment mBannerFragment = new BannerFragment();
        mBannerFragment.setArguments(bundle);
        return mBannerFragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rowItem = inflater.inflate(R.layout.fragment_banner, container, false);
        bindFragment(rowItem);
        if (getArguments() != null) {
            mBannerItem = getArguments().getParcelable(Constants.BundleKey.BUNDLE_BANNER_ITEM);
            if (mBannerItem != null) {
                switch (mBannerItem.getBannerType()) {
                    case Constants.BannerType.BANK_DETAILS:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_bank,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_banner_bank);
                        break;
                    case Constants.BannerType.ADD_DEPOSIT:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_deposit,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_banner_deposit);
                        break;
                    case Constants.BannerType.FICA:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_fica,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_banner_fica);
                        break;
                    case Constants.BannerType.FAQ:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_faq,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_banner_faq);
                        break;
                    case Constants.BannerType.FULL_REGISTRATION:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_faq,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_view_registration);
                        break;
                    case Constants.BannerType.ADDRESS:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_faq,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_banner_address);
                        break;
                    case Constants.BannerType.SHAKE_MAKE:
                        //TraderApplication.getInstance().loadImage(R.drawable.ic_banner_faq,(ImageView) rowItem.findViewById(R.id.image_banner));
                        ((ImageView) rowItem.findViewById(R.id.image_banner)).setImageResource(R.drawable.ic_banner_shakemake);
                        break;
                }
            }
        }
        return rowItem;
    }

    private void bindFragment(View rowItem) {
        mImageClose = rowItem.findViewById(R.id.image_close);
        mImageBanner = rowItem.findViewById(R.id.image_banner);

        mImageClose.setOnClickListener(this);
        mImageBanner.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        HomeSearchFragment parentFragment = (HomeSearchFragment) BannerFragment.this.getParentFragment();
        switch (view.getId()) {
            case R.id.image_banner:
                if (mBannerItem != null && parentFragment != null) {
                    parentFragment.onClickBanner(mBannerItem.getBannerType());
                }
                break;
            case R.id.image_close:
                if (mBannerItem != null && parentFragment != null) {
                    parentFragment.removeBanner(mBannerItem.getBannerType());
                }
                break;
        }
    }


    public interface BannerClickListener {

        void removeBanner(int itemType);

        void onClickBanner(int itemType);
    }
}
