package com.returntrader.view.widget;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.returntrader.library.Log;

import java.util.List;


public class YearAxisValueFormatter implements IAxisValueFormatter {


    private String TAG = getClass().getSimpleName();
    private List<String> mLabelList;
    private String mLastLabel = "";
    private String mFirstPositionLabel;
    int temp = 0;

    public YearAxisValueFormatter(List<String> labelList) {
        this.mLabelList = labelList;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String currentLabel = mLabelList.get((int) value);
        //Log.d(TAG, "currentLabel : " + currentLabel + "  mLastLabel : " + mLastLabel);
        if (!(mLastLabel.equalsIgnoreCase(currentLabel))) {
            mLastLabel = currentLabel;
            //Log.d(TAG,"temp : " + temp);
            if (temp > 12) {
                //Log.d(TAG,"label resetting");
                temp = 0;
                mLastLabel = "";
                return "";
            }

            if (temp <= 12) {
                if ((temp % 3) == 0) {
                    /*int increment = temp +1;
                    if (increment == 13) {
                        temp = 0;
                    } else {
                        temp = increment;
                    }*/
                    temp++;
                    if (temp >= 12){
                        temp = 0;
                    }
                    //Log.d(TAG, "label changed to : " + mLabelList.get((int) value) + " temp : " + temp);
                    String label = mLabelList.get((int) value).replaceAll("\\d*$", "");;
                    return label;
                } else {
                    temp++;
                }
            }
        }
        return "";
    }

}