package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.DelayPrice;

import java.util.List;

/**
 * Created by moorthy on 9/6/2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class DelayPriceResponse extends BaseResponse {

    @JsonField(name = "accountType")
    private String accountType;

    @JsonField(name = "is_market_time")
    private int marketAvailability;

    @JsonField(name = "delay")
    private List<DelayPrice> delayPrices;


    public List<DelayPrice> getDelayPrices() {
        return delayPrices;
    }

    public void setDelayPrices(List<DelayPrice> delayPrices) {
        this.delayPrices = delayPrices;
    }

    public int getMarketAvailability() {
        return marketAvailability;
    }

    public void setMarketAvailability(int marketAvailability) {
        this.marketAvailability = marketAvailability;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
