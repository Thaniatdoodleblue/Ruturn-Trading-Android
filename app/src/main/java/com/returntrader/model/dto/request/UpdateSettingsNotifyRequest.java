package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by nirmal on 3/22/2018.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class UpdateSettingsNotifyRequest extends BaseRequest {
    @JsonField(name = "bn")
    private boolean notifyBreakingNews;

    @JsonField(name = "jse")
    private boolean notifyJSE;

    @JsonField(name = "cn")
    private boolean notifyCN;

    public UpdateSettingsNotifyRequest() {

    }


    public boolean isNotifyBreakingNews() {
        return notifyBreakingNews;
    }

    public void setNotifyBreakingNews(boolean notifyBreakingNews) {
        this.notifyBreakingNews = notifyBreakingNews;
    }

    public boolean isNotifyJSE() {
        return notifyJSE;
    }

    public void setNotifyJSE(boolean notifyJSE) {
        this.notifyJSE = notifyJSE;
    }

    public boolean isNotifyCN() {
        return notifyCN;
    }

    public void setNotifyCN(boolean notifyCN) {
        this.notifyCN = notifyCN;
    }
}
