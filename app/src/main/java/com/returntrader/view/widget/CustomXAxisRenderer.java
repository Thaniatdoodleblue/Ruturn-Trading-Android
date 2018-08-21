package com.returntrader.view.widget;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;

import java.util.List;

public class CustomXAxisRenderer extends XAxisRenderer {

    private String TAG = getClass().getSimpleName();
    private List<String> mLabelList;
    private int mFilterType = -1;


    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis,
                               Transformer trans, List<String> mLabelList, int mFilterType) {
        super(viewPortHandler, xAxis, trans);
        this.mLabelList = mLabelList;
        this.mFilterType = mFilterType;
      /*  for (int i = 0; i < mLabelList.size(); i++) {
            Log.d(TAG,"label i = " + i + ": "+mLabelList.get(i));
        }*/
    }


    private int getDayScaleFactor() {
        int labelCount = mLabelList.size();
        if (labelCount <= 5) {
            return 1;
        } else if (labelCount <= 9) {
            return 2;
        } else if (labelCount <= 17) {
            return 4;
        } else {
            return 8;
        }

    }

    private int getYearScaleFactor() {
        return 1;
    }

    private int getThreeYearScaleFactor() {
        return 1;
    }


    @Override
    public void renderAxisLabels(Canvas c) {
        //super.renderAxisLabels(c);
        if (!mXAxis.isEnabled() || !mXAxis.isDrawLabelsEnabled())
            return;

        float yoffset = mXAxis.getYOffset();

        mAxisLabelPaint.setTypeface(mXAxis.getTypeface());
        mAxisLabelPaint.setTextSize(mXAxis.getTextSize());
        mAxisLabelPaint.setColor(mXAxis.getTextColor());

        MPPointF pointF = MPPointF.getInstance(0, 0);
        if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            pointF.x = 0.5f;
            pointF.y = 1.0f;
            drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF);

        } else if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            pointF.x = 0.5f;
            pointF.y = 1.0f;
            drawLabels(c, mViewPortHandler.contentTop() + yoffset + mXAxis.mLabelRotatedHeight, pointF);

        } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            pointF.x = 0.5f;
            pointF.y = 0.0f;
            drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF);

        } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            pointF.x = 0.5f;
            pointF.y = 0.0f;
            drawLabels(c, mViewPortHandler.contentBottom() - yoffset - mXAxis.mLabelRotatedHeight, pointF);

        } else { // BOTH SIDED
            pointF.x = 0.5f;
            pointF.y = 1.0f;
            drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF);
            pointF.x = 0.5f;
            pointF.y = 0.0f;
            drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF);
        }
        MPPointF.recycleInstance(pointF);
    }


    @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {

        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        boolean centeringEnabled = mXAxis.isCenterAxisLabelsEnabled();

      /*  for (int i = 0; i < mXAxis.mEntries.length; i++) {
            Log.d(TAG,"entry data :  i = " + i +  "data = "  + mXAxis.mEntries[i]);
        }*/

        //Log.d(TAG,"mXAxis.mEntryCount : "  +mXAxis.mEntryCount);
        float[] positions = new float[mXAxis.mEntryCount * 2];
        //  Log.d(TAG,"positions : "  + positions.length);
        for (int i = 0; i < positions.length; i += 2) {
           // Log.d(TAG, "1st iteration i : " + i);
            // only fill x values
            if (centeringEnabled) {
                positions[i] = mXAxis.mCenteredEntries[i / 2];
            } else {
                positions[i] = mXAxis.mEntries[i / 2];
            }
        }

        // Log.d(TAG,"positions length before to Pixel: "  + positions.length);

        mTrans.pointValuesToPixel(positions);

        // Log.d(TAG,"positions length after to Pixel: : "  + positions.length);

        for (int i = 0; i < positions.length; i += 2) {

            float x = positions[i];

            //   Log.d(TAG,"nd iteration i : "  + i + " with value : " +x);

            if (mViewPortHandler.isInBoundsX(x)) {

                String label = mXAxis.getValueFormatter().getFormattedValue(mXAxis.mEntries[i / 2], mXAxis);

                if (mXAxis.isAvoidFirstLastClippingEnabled()) {

                    // avoid clipping of the last
                    if (i == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);

                        if (width > mViewPortHandler.offsetRight() * 2
                                && x + width > mViewPortHandler.getChartWidth())
                            x -= width / 2;

                        // avoid clipping of the first
                    } else if (i == 0) {

                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                        x += width / 2;
                    }
                }

                drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees);
            }
        }
    }

    protected void computeAxisValues(float min, float max) {

        float yMin = min;
        float yMax = max;

        int labelCount = mAxis.getLabelCount();
        // android.util.Log.d(TAG,"labelCount : " + labelCount);
        // android.util.Log.d(TAG,"yMax : " + yMax);
        //   android.util.Log.d(TAG,"yMin : " + labelCount);

        double range = Math.abs(yMax - yMin);

        if (labelCount == 0 || range <= 0 || Double.isInfinite(range)) {
            mAxis.mEntries = new float[]{};
            mAxis.mCenteredEntries = new float[]{};
            mAxis.mEntryCount = 0;
            return;
        }

        // android.util.Log.d(TAG,"range : " + range);


        // Find out how much spacing (in y value space) between axis values
        double rawInterval = range / labelCount;
        double interval = Utils.roundToNextSignificant(rawInterval);
        Log.d(TAG, "interval : " + interval);
        Log.d(TAG, "mFilterType : " + mFilterType);
        switch (mFilterType) {
            case Constants.GraphFilter.FILTER_TYPE_ONE_YEAR:
                interval = getYearScaleFactor();
                break;
            case Constants.GraphFilter.FILTER_TYPE_TODAY:
                interval = getDayScaleFactor();
                break;
            case Constants.GraphFilter.FILTER_TYPE_THREE_YEARS:
                interval = getThreeYearScaleFactor();
                break;
        }


        //double interval = Utils.roundToNextSignificant(rawInterval);

        //android.util.Log.d(TAG,"rawInterval : " + rawInterval);
        // android.util.Log.d(TAG,"interval : " + interval);

        // If granularity is enabled, then do not allow the interval to go below specified granularity.
        // This is used to avoid repeated values when rounding values for display.
        if (mAxis.isGranularityEnabled())
            interval = interval < mAxis.getGranularity() ? mAxis.getGranularity() : interval;

        // Normalize interval
        double intervalMagnitude = Utils.roundToNextSignificant(Math.pow(10, (int) Math.log10(interval)));
        int intervalSigDigit = (int) (interval / intervalMagnitude);

        /*if (mFilterType == Constants.GraphFilter.FILTER_TYPE_ONE_YEAR){

        }
        if (intervalSigDigit > 5) {
            // Use one order of magnitude higher, to avoid intervals like 0.9 or
            // 90
             interval = Math.floor(10 * intervalMagnitude);
        }
*/
        Log.d(TAG, "final interval : " + interval);
        int n = mAxis.isCenterAxisLabelsEnabled() ? 1 : 0;

        // force label count
        if (mAxis.isForceLabelsEnabled()) {

            interval = (float) range / (float) (labelCount - 1);
            mAxis.mEntryCount = labelCount;

            if (mAxis.mEntries.length < labelCount) {
                // Ensure stops contains at least numStops elements.
                mAxis.mEntries = new float[labelCount];
            }

            float v = min;

            for (int i = 0; i < labelCount; i++) {
                mAxis.mEntries[i] = v;
                v += interval;
            }

            n = labelCount;

            // no forced count
        } else {

            double first = interval == 0.0 ? 0.0 : Math.ceil(yMin / interval) * interval;
            if (mAxis.isCenterAxisLabelsEnabled()) {
                first -= interval;
            }

            double last = interval == 0.0 ? 0.0 : Utils.nextUp(Math.floor(yMax / interval) * interval);

            double f;
            int i;

            if (interval != 0.0) {
                for (f = first; f <= last; f += interval) {
                    ++n;
                }
            }

            mAxis.mEntryCount = n;

            if (mAxis.mEntries.length < n) {
                // Ensure stops contains at least numStops elements.
                mAxis.mEntries = new float[n];
            }

            for (f = first, i = 0; i < n; f += interval, ++i) {

                if (f == 0.0) // Fix for negative zero case (Where value == -0.0, and 0.0 == -0.0)
                    f = 0.0;

                mAxis.mEntries[i] = (float) f;
            }
        }

        // set decimals
        if (interval < 1) {
            mAxis.mDecimals = (int) Math.ceil(-Math.log10(interval));
        } else {
            mAxis.mDecimals = 0;
        }

        if (mAxis.isCenterAxisLabelsEnabled()) {

            if (mAxis.mCenteredEntries.length < n) {
                mAxis.mCenteredEntries = new float[n];
            }

            float offset = (float) interval / 2f;

            for (int i = 0; i < n; i++) {
                mAxis.mCenteredEntries[i] = mAxis.mEntries[i] + offset;
            }
        }
    }
}
