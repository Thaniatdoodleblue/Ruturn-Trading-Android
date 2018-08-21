package com.returntrader.adapter.listener;

import com.returntrader.model.imodel.Stories;

/**
 * Created by Karthikeyan on 19-07-2017
 */

public interface INewsAdapterListener {
    void onLoadLink(Stories data, int position);

    void onClickItem(int itemPosition);
}
