package com.returntrader.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.returntrader.view.fragment.BannerFragment;

/**
 * Created by moorthy on 8/26/2017.
 */

public class BannerItem implements Parcelable {

    private int bannerType = -1;


    public BannerItem(int bannerType) {
        this.bannerType = bannerType;
    }

    protected BannerItem(Parcel in) {
        bannerType = in.readInt();
    }

    public static final Creator<BannerItem> CREATOR = new Creator<BannerItem>() {
        @Override
        public BannerItem createFromParcel(Parcel in) {
            return new BannerItem(in);
        }

        @Override
        public BannerItem[] newArray(int size) {
            return new BannerItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bannerType);
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }


}
