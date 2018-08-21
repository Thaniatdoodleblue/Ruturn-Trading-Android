package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.returntrader.R;
import com.returntrader.presenter.ReportProblemPresenter;
import com.returntrader.presenter.UpdateRequestPresenter;
import com.returntrader.presenter.ipresenter.IUpdateRequestPresenter;
import com.returntrader.view.iview.IUpdateRequestView;

/**
 * Created by moorthy on 7/26/2017.
 */

public class UpdateRequestActivity extends BaseActivity implements View.OnClickListener, IUpdateRequestView {


    private IUpdateRequestPresenter iUpdateRequestPresenter;
    private EditText mEtContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request);
        bindActivity();
        iUpdateRequestPresenter = new UpdateRequestPresenter(this);
        iUpdateRequestPresenter.onCreatePresenter(getIntent().getExtras());

    }

    private void bindActivity() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEtContent = findViewById(R.id.tv_message);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(UpdateRequestActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (mEtContent.length() > 0) {
                    iUpdateRequestPresenter.onClickSubmit(mEtContent.getText().toString());
                } else {
                    showMessage(R.string.txt_report_problem_hint);
                }
                break;

        }
    }

}
