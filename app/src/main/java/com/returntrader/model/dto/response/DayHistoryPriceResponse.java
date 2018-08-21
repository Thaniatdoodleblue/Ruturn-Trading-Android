package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.DayHistoryDo;
import com.returntrader.model.imodel.HistoricPriceLogs;

import java.util.List;

/**
 * Created by moorthy on 10/5/2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class DayHistoryPriceResponse extends BaseResponse {


    @JsonField(name = "per_day")
    private List<DayHistoryDo> dayHistoryList;


    public List<DayHistoryDo> getDayHistoryList() {
        return dayHistoryList;
    }

    public void setDayHistoryList(List<DayHistoryDo> dayHistoryList) {
        this.dayHistoryList = dayHistoryList;
    }
}
