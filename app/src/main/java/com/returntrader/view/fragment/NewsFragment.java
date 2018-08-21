package com.returntrader.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.returntrader.R;
import com.returntrader.adapter.NewsAdapter;
import com.returntrader.library.Log;
import com.returntrader.presenter.NewsPresenter;
import com.returntrader.presenter.ipresenter.INewsPresenter;
import com.returntrader.presenter.ipresenter.NewsDataView;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

public class NewsFragment extends BaseFragment implements NewsDataView {

    private RecyclerView recyclerView;
    private INewsPresenter iNewsPresenter;
    private Boolean _areLecturesLoaded = false;
    private StickyHeaderDecoration mStickyHeaderDecoration;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        findByViews(rootView);
        iNewsPresenter = new NewsPresenter(this);
        iNewsPresenter.onCreatePresenter(getArguments());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        iNewsPresenter.onStartPresenter();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded) {
            _areLecturesLoaded = true;
        }
    }

    public void findByViews(View rootView) {
        recyclerView = rootView.findViewById(R.id.MyRecyclerView);
        mProgressBar = rootView.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void setNewsHomeAdapter(NewsAdapter newsAdapter) {
        recyclerView.setAdapter(newsAdapter);
        mStickyHeaderDecoration = new StickyHeaderDecoration(newsAdapter);
        recyclerView.addItemDecoration(mStickyHeaderDecoration);
    }

    @Override
    public void navigateToLink(Uri data) {
        Log.d("Data***", "" + data);
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(data);
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            showMessage(getString(R.string.txt_no_application));
            e.printStackTrace();
        }
    }


    @Override
    public void showProgressbar() {
        //super.showProgressbar();
        //mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressbar() {
        //super.dismissProgressbar();
       // mProgressBar.setVisibility(View.GONE);
    }
}
