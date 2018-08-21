package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.StockHold;

import java.util.List;

/**
 * Created by moorthy on 9/15/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class StockHoldResponse extends BaseResponse {

    @JsonField(name = "holdings")
    private List<StockHold> stockHoldList;

    public List<StockHold> getStockHoldList() {
        return stockHoldList;
    }

    public void setStockHoldList(List<StockHold> stockHoldList) {
        this.stockHoldList = stockHoldList;
    }
}
