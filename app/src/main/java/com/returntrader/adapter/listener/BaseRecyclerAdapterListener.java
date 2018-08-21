package com.returntrader.adapter.listener;

/**
 * Created by Karthikeyan on 04-07-2017
 */



public interface BaseRecyclerAdapterListener<T> {

    void onClickItem(T data, int position);


}