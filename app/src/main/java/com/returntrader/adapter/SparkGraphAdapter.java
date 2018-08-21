package com.returntrader.adapter;

import com.returntrader.common.TraderApplication;
import com.robinhood.spark.SparkAdapter;

import java.util.List;

public class SparkGraphAdapter extends SparkAdapter {

    private String TAG = getClass().getSimpleName();
    private List<Float> yData = null;
  /*  private float[] yData;
    private final Random random;*/

    public SparkGraphAdapter(List<Float> yData) {
        this.yData = yData;
      //  Log.d(TAG,"yData size :" + yData.size());
        //   random = new Random();
        //this.yData = yData;

    }

    /*public SparkGraphAdapter() {
        random = new Random();
        yData = new float[8];
        randomize();
    }*/

    /*public void randomize() {
        for (int i = 0, count = yData.length; i < count; i++) {
            yData[i] = random.nextFloat();
        }
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return yData.size();
    }

    @Override
    public Object getItem(int index) {
        return TraderApplication.getInstance().convertPriceCentToRand(yData.get(index));
    }

    @Override
    public float getY(int index) {
        return yData.get(index);
    }


}