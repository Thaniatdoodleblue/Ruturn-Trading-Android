package com.returntrader.model.imodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Karthikeyan on 11-07-2017
 */



@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class HistoricPriceLogs implements Parcelable {


    private String TAG = getClass().getSimpleName();

    @SuppressWarnings("unused")
    public static final Creator<HistoricPriceLogs> CREATOR = new Creator<HistoricPriceLogs>() {
        @Override
        public HistoricPriceLogs createFromParcel(Parcel in) {
            return new HistoricPriceLogs(in);
        }

        @Override
        public HistoricPriceLogs[] newArray(int size) {
            return new HistoricPriceLogs[size];
        }
    };

    @JsonField(name = "id")
    private String id;

    @JsonField(name = "updated_at")
    private String updatedAt;

    @JsonField(name = "price")
    private String price;

    @JsonField(name = "contarct_code")
    private String contractCode;

    @JsonField(name = "status")
    private String status;

    @JsonField(name = "created_at")
    private String created_at;

    @JsonField(name = "closing_date")
    private String closingDate;

    @JsonField(name = "isin_code")
    private String isinCode;

    public HistoricPriceLogs() {
    }

    protected HistoricPriceLogs(Parcel in) {
        id = in.readString();
        updatedAt = in.readString();
        price = in.readString();
        contractCode = in.readString();
        status = in.readString();
        created_at = in.readString();
        closingDate = in.readString();
        isinCode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(updatedAt);
        dest.writeString(price);
        dest.writeString(contractCode);
        dest.writeString(status);
        dest.writeString(created_at);
        dest.writeString(closingDate);
        dest.writeString(isinCode);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public Date getFormatterDate(){
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(closingDate);
            //Log.d(TAG,"converted date : " + date);
            return  date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }
}
