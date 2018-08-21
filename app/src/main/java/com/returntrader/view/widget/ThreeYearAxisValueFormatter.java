package com.returntrader.view.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;


public class ThreeYearAxisValueFormatter implements IAxisValueFormatter {


    private String TAG = getClass().getSimpleName();
    private List<String> mLabelList;
    private String mLastLabel = "";

    public ThreeYearAxisValueFormatter(List<String> labelList) {
        this.mLabelList = labelList;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index = Math.round(value);
        if (index < 0 || index >= mLabelList.size() || index != (int) value)
            return "";
        String currentLabel = mLabelList.get((int) value);
        if (!(mLastLabel.equalsIgnoreCase(currentLabel))) {
            mLastLabel = currentLabel;
            return mLabelList.get((int) value);
        }
        return "";
    }

}