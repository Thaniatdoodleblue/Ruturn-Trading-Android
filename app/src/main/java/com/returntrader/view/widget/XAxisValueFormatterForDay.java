package com.returntrader.view.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.returntrader.library.Log;

import java.util.Collection;

public class XAxisValueFormatterForDay implements IAxisValueFormatter {
    private String TAG = getClass().getSimpleName();
    private String[] mValues = new String[]{};
    //private int mLabelCount = 0;

    public XAxisValueFormatterForDay(Collection<String> values) {
        if (values != null)
            setValues(values.toArray(new String[values.size()]));
    }


    public String[] getValues() {
        return mValues;
    }


    private void setValues(String[] values) {
        if (values == null)
            values = new String[]{};
        this.mValues = values;
        //this.mLabelCount = values.length;
       /* Log.d(TAG,"mLabelCount : " + mLabelCount);
        if (mLabelCount <= 4) {
            mScaleFactor = 1;
        } else if (mLabelCount <= 8) {
            mScaleFactor = 4;
        } else if (mLabelCount < 16) {
            mScaleFactor = 4;
        } else if (mLabelCount < 21) {
            mScaleFactor = 4;
        }else {
            mScaleFactor = 8;
        }


        for (int i = 0; i < mValues.length; i++) {
            Log.d(TAG, "x label i = " + i + "value : " + mValues[i]);
        }

        Log.d(TAG,"mScaleFactor : "  + mScaleFactor);*/

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int) value];
    }


}
