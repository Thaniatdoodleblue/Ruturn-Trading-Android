package com.returntrader.presenter.ipresenter;

import android.net.Uri;

import com.returntrader.adapter.NewsAdapter;
import com.returntrader.view.iview.IView;

/**
 * Created by Karthikeyan on 17-07-2017
 */

public interface NewsDataView extends IView {


    void setNewsHomeAdapter(NewsAdapter newsAdapter);

    void navigateToLink(Uri parse);
}
