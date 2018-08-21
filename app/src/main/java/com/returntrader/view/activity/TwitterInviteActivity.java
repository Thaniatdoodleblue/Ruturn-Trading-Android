package com.returntrader.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.FollowerListAdapter;
import com.returntrader.presenter.TwitterInvitePresenter;
import com.returntrader.presenter.ipresenter.ITwitterPresenter;
import com.returntrader.view.iview.ITwitterInviteView;

/**
 * Created by moorthy on 8/2/2017.
 */

public class TwitterInviteActivity extends BaseActivity implements ITwitterInviteView {
    private ITwitterPresenter iTwitterPresenter;
    private RecyclerView mRvFollowerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_invite_contact);
        bindActivity();
        iTwitterPresenter = new TwitterInvitePresenter(this);
        iTwitterPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle =  toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_invite_twitter);

        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRvFollowerList =  findViewById(R.id.recycler_contacts);
        mRvFollowerList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(TwitterInviteActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFollowerAdapter(FollowerListAdapter followerAdapter) {
        mRvFollowerList.setAdapter(followerAdapter);
    }
}
