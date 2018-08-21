package com.returntrader.view.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.returntrader.common.Constants;

public class MyYAxisValueFormatter implements IAxisValueFormatter {

    public MyYAxisValueFormatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return Constants.Common.DECIMAL_FORMAT.format(value);
    }
}