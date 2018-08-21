package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.view.iview.INewsDescriptionActivityView;

public class NewsDescriptionActivity extends BaseActivity implements View.OnClickListener, INewsDescriptionActivityView {

    private Toolbar toolbar;
    private TextView toolText;
    private ImageView iShare;
    private LinearLayout descLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        findByViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeSnippet().hideKeyboard(NewsDescriptionActivity.this);
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        String headlines = bundle.getString("NowHeading");
        toolText.setText(headlines);

    }

    protected int getLayoutId() {
        return R.layout.activity_news_description;
    }


    @Override
    public void onClick(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void findByViews() {
        toolbar = (Toolbar) findViewById(R.id.newsDescToolbar);
        toolText = (TextView) findViewById(R.id.headlineText);
        iShare = (ImageView) findViewById(R.id.shareImg);
        descLayout = (LinearLayout) findViewById(R.id.newsDescriptionLayout);
        iShare.setOnClickListener(this);
        descLayout.setOnClickListener(this);
    }
}
