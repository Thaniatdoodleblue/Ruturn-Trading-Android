package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.request.BaseRequest;

/**
 * Created by moorthy on 12/6/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class IncomeBand {

    @JsonField(name = "incomeBandId")
    private String incomeBandId;

    @JsonField(name = "name")
    private String name;

    @JsonField(name = "minimum")
    private String minimum;

    @JsonField(name = "maximum")
    private String maximum;


    public String getIncomeBandId() {
        return incomeBandId;
    }

    public void setIncomeBandId(String incomeBandId) {
        this.incomeBandId = incomeBandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }
}
