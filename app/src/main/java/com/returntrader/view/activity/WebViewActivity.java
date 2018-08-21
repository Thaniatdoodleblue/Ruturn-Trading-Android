package com.returntrader.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.returntrader.R;
import com.returntrader.common.Constants;

/**
 * Created by moorthy on 7/28/2017.
 */

public class WebViewActivity extends BaseActivity {

    private WebView vWebView;
    private TextView vTvToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        bindActivity();
    }

    private void bindActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        vTvToolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        vWebView = (WebView) findViewById(R.id.webView);
        vWebView.getSettings().setJavaScriptEnabled(true);
        vWebView.setWebViewClient(new WebViewClient());
        vWebView.setWebChromeClient(new WebChromeClient());
        setWebViewData();
    }

    private void setWebViewData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        String title = bundle.getString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE);
        String url = bundle.getString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL);
        vTvToolbarTitle.setText(title);
        vWebView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(WebViewActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
