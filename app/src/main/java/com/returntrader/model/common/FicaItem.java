package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 11/7/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class FicaItem {

    private int type;

    private boolean isUploaded;

    @JsonField(name = "front")
    private String frontThumbnail;

    @JsonField(name = "back")
    private String backThumbnail;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getFrontThumbnail() {
        return frontThumbnail;
    }

    public void setFrontThumbnail(String frontThumbnail) {
        this.frontThumbnail = frontThumbnail;
    }

    public String getBackThumbnail() {
        return backThumbnail;
    }

    public void setBackThumbnail(String backThumbnail) {
        this.backThumbnail = backThumbnail;
    }
}
