package com.returntrader.view.widget;

/**
 * Created by azeem on 30-Oct-17
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.library.Log;

import java.math.BigDecimal;

public class NumberView extends ViewGroup implements OnClickListener {

    private static final String TAG = "NumberView";
    /**
     * The current numbers that the user has input
     */
    private String mNumber = "";

    private TextView[] mButtons;

    /**
     * The default is left for back, right for OK
     */
    // private boolean mSwapActionButtons = false;

    // private Button mAddButton;
    // private Button mClearButton;

    private int mRows = 3;
    private int mCols = 3;

    // Workaround for button incorrect centering
    private int mPaddingLeft = 0;
    private int mPaddingTop = 0;
    private int mPaddingRight = 0;
    private int mPaddingBottom = 0;

    private int mHorizontalSpacing;
    private int mVerticalSpacing;
    private int mChildWidth;
    private int mChildHeight;

    private int mMaxHeight;
    private int mMaxWidth;
    private int mHeight;
    private int mWidth;


    /**
     * How many times may the view be taller than wide?
     */
    private float mMaxVScale = 1.2f;
    /**
     * How many times may the view be wider than tall?
     */
    private float mMaxHScale = 1.2f;

    private boolean mStarted;

    /**
     * If this is true, all children will be square, so the height of this view
     * will be {@link #mChildHeight} * {@link #mRows} and the width will be
     * {@link #mChildWidth} * {@link #mCols}<br>
     * This invalidates {@link #mMaxHScale} and {@link #mMaxVScale} <br>
     * This only works when {@link #mVerticalSpacing} and
     * {@link #mHorizontalSpacing} are the same.
     */


    private OnNumberListener mListener;


    // private TextView mTextView;

    public void setListener(OnNumberListener listener) {
        this.mListener = listener;
    }

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PasswordView);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(
                    R.styleable.PasswordView_horizontalSpacing, 0);
            mVerticalSpacing = a.getDimensionPixelSize(
                    R.styleable.PasswordView_verticalSpacing, 0);
            mRows = a.getInteger(R.styleable.PasswordView_rows, mRows);
            mCols = a.getInteger(R.styleable.PasswordView_cols, mCols);

            // Avoid Arithmetic Exceptions
            if (mRows <= 0)
                mRows = 1;
            if (mCols <= 0)
                mCols = 1;

            mMaxWidth = a.getDimensionPixelSize(
                    R.styleable.PasswordView_maxWidth, 0);
            mMaxHeight = a.getDimensionPixelSize(
                    R.styleable.PasswordView_maxHeight, 0);
            mMaxHScale = a.getFloat(R.styleable.PasswordView_maxHScale, 1F);
            mMaxVScale = a.getFloat(R.styleable.PasswordView_maxVScale, 1F);


        } finally {
            a.recycle();
        }

        // This prevents a bug with children not being measured correctly
        mPaddingLeft = getPaddingLeft();
        mPaddingRight = getPaddingRight();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();
        setPadding(0, 0, 0, 0);

        /*if (mSquareChildren && mHorizontalSpacing == mVerticalSpacing) {
            mMaxHScale = (float) mCols / mRows;
            mMaxVScale = (float) mRows / mCols;
        } else if (mSquareChildren) {
            Log.w(TAG,
                    "To get square children, horizontal and vertical spacing should be set to equal!");
        }*/
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mButtons = new TextView[]{(TextView) findViewById(R.id.num_b0),
                (TextView) findViewById(R.id.num_b1),
                (TextView) findViewById(R.id.num_b2),
                (TextView) findViewById(R.id.num_b3),
                (TextView) findViewById(R.id.num_b4),
                (TextView) findViewById(R.id.num_b5),
                (TextView) findViewById(R.id.num_b6),
                (TextView) findViewById(R.id.num_b7),
                (TextView) findViewById(R.id.num_b8),
                (TextView) findViewById(R.id.num_b9)};
        for (TextView b : mButtons)
            b.setOnClickListener(this);

        findViewById(R.id.button_clear).setOnClickListener(this);

    }

    public interface OnNumberListener {

        void onStart();

        void onNumberButton(String newNumber);

        void onClearButton();
    }


    @Override
    public void onClick(View v) {
        if (!mStarted) {
            mListener.onStart();
            mStarted = true;
        }

        switch (v.getId()) {
            case R.id.button_clear:
                if (mListener != null) {
                    mListener.onClearButton();
                }
                break;
            default:
                onNumberButtonImpl(v);
                break;
        }

    }

    /**
     * @return The distance in inches that the finger has swiped over the
     * pattern<br>
     * This is calculated as the distance between the pattern circles,
     * not the real distance of the finger
     */
    public float getFingerDistance() {
        // TODO Pixel to inch
        float xppi = getResources().getDisplayMetrics().xdpi;
        float yppi = getResources().getDisplayMetrics().ydpi;
        float ppi = (xppi + yppi) / 2;
        float inchesPerDot = (mChildWidth + mChildHeight) / 2 / ppi;
        return inchesPerDot * mNumber.length();
    }

    public void clearPinNumber() {
        mNumber = "";
    }

    /**
     * What happens when a number button is pressed<br>
     *
     * @param v The view that has been clicked
     */
    private void onNumberButtonImpl(View v) {
        TextView b = (TextView) v;

        Log.d(TAG, "oldNumber :" + mNumber);

        if (mNumber.length() >= 4) {
            return;
        }

        String newNumber = new StringBuilder().append(mNumber)
                .append(b.getText()).toString();

        Log.d(TAG, "newNumber :" + newNumber);

        if (newNumber.length() > 0) {
            newNumber = String.valueOf(newNumber);
        }
        setNumber(newNumber);
        final String finalNewNumber = newNumber;
        post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onNumberButton(finalNewNumber);
                }
            }
        });
    }

    public String getCurrentNumbers() {
        return mNumber;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = resolveSize(MeasureSpec.getSize(widthMeasureSpec),
                widthMeasureSpec);
        mHeight = resolveSize(MeasureSpec.getSize(heightMeasureSpec),
                heightMeasureSpec);

        correctViewSize(mWidth, mHeight, mMaxWidth, mMaxHeight, mMaxHScale,
                mMaxVScale);

        // Reset width and height because some loose pixels at the end:
        int childMSW = MeasureSpec.makeMeasureSpec(mChildWidth,
                MeasureSpec.EXACTLY);
        int childMSH = MeasureSpec.makeMeasureSpec(mChildHeight,
                MeasureSpec.EXACTLY);

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, childMSW, childMSH);
        }

        setMeasuredDimension(mWidth, mHeight);
    }


    private void correctViewSize(int width, int height, int maxWidth,
                                 int maxHeight, float maxHScale, float maxVScale) {
        if (maxWidth != 0)
            width = Math.min(width, maxWidth);
        if (maxHeight != 0)
            height = Math.min(height, maxHeight);
        float hScale = (float) width / height;
        float vScale = (float) height / width;
        // Vertical stretch
        if (hScale <= maxHScale) {
            int desiredHeight = (int) ((float) width * maxVScale);
            height = Math.min(height, desiredHeight);
        }
        // Horizontal stretch
        else if (vScale <= maxVScale) {
            int desiredWidth = (int) ((float) height * maxHScale);
            width = Math.min(width, desiredWidth);
        }

        int horizontalSpacing = mHorizontalSpacing * (mCols - 1);
        int verticalSpacing = mVerticalSpacing * (mRows - 1);

        mChildWidth = (width - mPaddingLeft - mPaddingRight - horizontalSpacing)
                / mCols;
        mChildHeight = (height - mPaddingTop - mPaddingBottom - verticalSpacing)
                / mRows;

        // Set the correct values
        mWidth = mPaddingLeft + mPaddingRight + (mChildWidth * mCols)
                + (mHorizontalSpacing * (mCols - 1));
        mHeight = mPaddingTop + mPaddingBottom + (mChildHeight * mRows)
                + (mVerticalSpacing * (mRows - 1));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int childL, childT;
        childL = childT = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            childL = mPaddingLeft
                    + ((mHorizontalSpacing + mChildWidth) * (i % mCols));
            childT = mPaddingTop
                    + ((mVerticalSpacing + mChildHeight) * (i / mCols));

            child.layout(childL, childT, childL + mChildWidth, childT
                    + mChildHeight);
        }
    }

    /**
     * Sets the horizontal spacing in pixels
     *
     * @param horizontalSpacing
     */
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.mHorizontalSpacing = horizontalSpacing;
    }

    /**
     * Sets the vertical spacing in pixels
     *
     * @param verticalSpacing
     */
    public void setVerticalSpacing(int verticalSpacing) {
        this.mVerticalSpacing = verticalSpacing;
    }

    /**
     * Update the internal password this {@link NumberView} is working with.
     * If password is null then it will be cleared.
     *
     * @param number
     */


    public void setNumber(String number) {
        this.mNumber = (number != null) ? number : "";
    }

    public void clearNumber() {
        setNumber(null);
    }

    /**
     * Get the current password entered by the user. Never null.
     *
     * @return
     */
    public String getNumber() {
        return mNumber;
    }

    public int getUnpaddedWidth() {
        return getWidth() - mPaddingLeft - mPaddingRight;
    }


}
