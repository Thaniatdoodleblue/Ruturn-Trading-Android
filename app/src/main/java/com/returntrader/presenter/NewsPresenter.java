package com.returntrader.presenter;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.adapter.NewsAdapter;
import com.returntrader.adapter.listener.INewsAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.NewsRequest;
import com.returntrader.model.dto.response.NewsResponse;
import com.returntrader.model.imodel.Stories;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.NewsModel;
import com.returntrader.presenter.ipresenter.INewsPresenter;
import com.returntrader.presenter.ipresenter.NewsDataView;

import java.util.List;


public class NewsPresenter extends BasePresenter implements INewsPresenter {

    private String TAG = getClass().getSimpleName();
    private NewsDataView iNewsView;
    private NewsAdapter mNewsAdapter;
    private CompanyItemInfo mHomeSearchData;
    private NewsModel mNewsModel;


    public NewsPresenter(NewsDataView newsDataView) {
        super(newsDataView);
        this.iNewsView = newsDataView;
        this.mNewsModel = new NewsModel(newsResponseIBaseModelListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mHomeSearchData = bundle.getParcelable(Constants.BundleKey.BUNDLE_COMPANY_ITEM);
            if (mHomeSearchData != null) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        iNewsView.showProgressbar();
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        getNewsFromApi();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        iNewsView.dismissProgressbar();
                    }
                }.execute();
            }
        }
    }


    /**
     * @param mArrStoriesList
     */
    private void setAdapter(List<Stories> mArrStoriesList) {
        if (mNewsAdapter == null) {
            mNewsAdapter = new NewsAdapter(mArrStoriesList, iNewsView.getCodeSnippet(), iNewsAdapterListener);
            iNewsView.setNewsHomeAdapter(mNewsAdapter);
        } else {
            mNewsAdapter.resetItems(mArrStoriesList);
        }
    }

    private String getCompanyNameSubString() {
        String code = mHomeSearchData.getContractCode();

        if (!(TextUtils.isEmpty(code))) {
            return code.substring(code.lastIndexOf(".") + 1);
        }
        return null;
    }

    private String getNewsApiUrl() {
//        /*https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=bdeac786e26e40e48ee453334c5bd4df*/
        return "stories?text=" + getCompanyNameSubString() + "+" + mHomeSearchData.getInstrumentName() + "&published_at.start=NOW-15DAYS%2FDAY&published_at.end=NOW&language=en&sort_by=relevance";
    }

    private void getNewsFromApi() {
        if (iNewsView.getCodeSnippet().hasNetworkConnection()) {
            NewsRequest newsRequest = new NewsRequest();
            newsRequest.setSource("bloomberg");
            newsRequest.setApiKey(Constants.Common.NEWS_API_APP_KEY);
            mNewsModel.getNews(0, newsRequest);
        } else {
            iNewsView.showNetworkMessage();
        }
    }


    /***/
    private IBaseModelListener<NewsResponse> newsResponseIBaseModelListener = new IBaseModelListener<NewsResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, NewsResponse response) {
            iNewsView.dismissProgressbar();
            if (response.getStories().isEmpty() || response.getStories() == null) {
                return;
            }

            setAdapter(response.getStories());
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iNewsView.dismissProgressbar();
            // iNewsView.showMessage(e.getMessage());
        }
    };


    /***/
    private INewsAdapterListener iNewsAdapterListener = new INewsAdapterListener() {
        @Override
        public void onLoadLink(Stories data, int position) {
            if (!TextUtils.isEmpty(data.getUrl())) {
                iNewsView.navigateToLink(Uri.parse(data.getUrl().contains("http://") ? data.getUrl() : "https://" + data.getUrl()));
            } else {
                iNewsView.showMessage("Link is not available");
            }
        }

        @Override
        public void onClickItem(int itemPosition) {
        }
    };

}

