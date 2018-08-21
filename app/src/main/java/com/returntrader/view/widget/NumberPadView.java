package com.returntrader.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;


/**
 * Created by moorthy on 9/30/2016.
 */
public class NumberPadView extends LinearLayout {


    public NumberView mNumberView;
    private View mRootView;
    private NumberView.OnNumberListener mOnNumberListener;

    private Button mAddButton;
    private Button mClearButton;
    private FrameLayout mCartLayout;
    private TextView mCartImage;


    public NumberPadView(Context context) {
        super(context);
        inflateNumberView();
    }

    public NumberPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateNumberView();
    }

    public NumberPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateNumberView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumberPadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateNumberView();
    }

    private void inflateNumberView() {
        mRootView = LayoutInflater.from(getContext()).inflate(
                R.layout.inflate_number_pad_view, this);
        mNumberView = (NumberView) mRootView.findViewById(R.id.NumberView);
        //((Button) mRootView.findViewById(R.id.button_clear_all)).setOnClickListener(new MyOnClickListener());
        //  ((Button) mRootView.findViewById(R.id.button_add)).setOnClickListener(new MyOnClickListener());
        //  mClearButton = (Button) mRootView.findViewById(R.id.button_clear);
        //   mCartLayout = (FrameLayout) mRootView.findViewById(R.id.layout_pay);
        //  mCartImage.setOnClickListener(new MyOnClickListener());
    }


    public void setOnNumberListener(NumberView.OnNumberListener mOnNumberListener) {
        this.mOnNumberListener = mOnNumberListener;
        mNumberView.setListener(mOnNumberListener);

    }

    public void clearNumber() {
        mNumberView.clearNumber();
    }

    public void clearPinNumber(){
        mNumberView.clearPinNumber();
    }

    public void setCartButtonText(String buttonText) {
        mCartImage.setText(buttonText);
    }

   /* public void setCartImageResource(int resourceId) {
        if (mCartImage != null)
            mCartImage.setImageResource(resourceId);
    }*/

    public String getCurrentNumber() {
        return mNumberView.getCurrentNumbers();
    }

    public void setNumber(String newNumber) {
        mNumberView.setNumber(newNumber);
    }

}
