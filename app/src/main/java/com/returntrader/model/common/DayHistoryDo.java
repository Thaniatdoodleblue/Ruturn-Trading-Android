package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.library.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by moorthy on 10/5/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class DayHistoryDo {

    @JsonField(name = "isinCode")
    private String isinCode;

    @JsonField(name = "close")
    private float close;

    @JsonField(name = "bid")
    private float bid;

    @JsonField(name = "offer")
    private float offer;

    @JsonField(name = "last")
    private float last;

    @JsonField(name = "updated_at")
    private long updatedAt;


    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getOffer() {
        return offer;
    }

    public void setOffer(float offer) {
        this.offer = offer;
    }

    public float getLast() {
        return last;
    }

    public void setLast(float last) {
        this.last = last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Date getFormatterDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        calendar.clear();
        calendar.setTimeInMillis(updatedAt);
        return calendar.getTime();
    }
}
