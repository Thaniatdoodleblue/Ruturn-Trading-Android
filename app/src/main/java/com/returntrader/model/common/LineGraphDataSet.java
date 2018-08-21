package com.returntrader.model.common;

import com.github.mikephil.charting.data.LineData;
import com.returntrader.model.imodel.HistoricPriceLogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karthikeyan on 13-07-2017
 */

public class LineGraphDataSet {

    private int filterType =-1;

    private List<String> labelList;

    private LineData lineData;

    private List<HistoricPriceLogs> graphSourceData;

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public LineData getLineData() {
        return lineData;
    }

    public void setLineData(LineData lineData) {
        this.lineData = lineData;
    }


    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }


    public List<HistoricPriceLogs> getGraphSourceData() {
        return graphSourceData;
    }

    public void setGraphSourceData(List<HistoricPriceLogs> graphSourceData) {
        this.graphSourceData = graphSourceData;
    }
}
