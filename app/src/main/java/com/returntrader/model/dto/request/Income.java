package com.returntrader.model.dto.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 12/6/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Income implements Parcelable{

    @JsonField(name = "currentEarningsStatus")
    private int currentEarningsStatus = 0;

    @JsonField(name = "incomeBand")
    private String incomeBandId;

    public Income() {
    }

    protected Income(Parcel in) {
        currentEarningsStatus = in.readInt();
        incomeBandId = in.readString();
    }


    public static final Creator<Income> CREATOR = new Creator<Income>() {
        @Override
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }

        @Override
        public Income[] newArray(int size) {
            return new Income[size];
        }
    };

    public int getCurrentEarningsStatus() {
        return currentEarningsStatus;
    }

    public void setCurrentEarningsStatus(int currentEarningsStatus) {
        this.currentEarningsStatus = currentEarningsStatus;
    }

    public String getIncomeBandId() {
        return incomeBandId;
    }

    public void setIncomeBandId(String incomeBandId) {
        this.incomeBandId = incomeBandId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentEarningsStatus);
        dest.writeString(incomeBandId);
    }
}
