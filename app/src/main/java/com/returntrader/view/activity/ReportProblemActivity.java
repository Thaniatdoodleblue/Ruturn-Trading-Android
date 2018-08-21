package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.returntrader.R;
import com.returntrader.model.dto.request.ReportProblemRequest;
import com.returntrader.presenter.ReportProblemPresenter;
import com.returntrader.presenter.ipresenter.IReportProblemPresenter;
import com.returntrader.view.iview.IReportProblemView;

/**
 * Created by moorthy on 7/26/2017.
 */

public class ReportProblemActivity extends BaseActivity implements View.OnClickListener, IReportProblemView {
    private IReportProblemPresenter iReportProblemPresenter;
    private EditText mEtContent;
    private AppCompatCheckBox acbUpdateFICA;
    private AppCompatCheckBox acbUpdateBank;
    private LinearLayout llUpdateReportContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);
        bindActivity();
        iReportProblemPresenter = new ReportProblemPresenter(this);
        iReportProblemPresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEtContent = findViewById(R.id.tv_message);
        acbUpdateFICA = findViewById(R.id.acb_updateFICA);
        acbUpdateBank = findViewById(R.id.acb_updateBank);
        llUpdateReportContainer = findViewById(R.id.llUpdateReportContainer);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(ReportProblemActivity.this);
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
                    boolean updateFica = acbUpdateFICA.isChecked();
                    boolean updateBank = acbUpdateBank.isChecked();
                    ReportProblemRequest reportProblemRequest = new ReportProblemRequest();
                    String contentTitle = "";
                    if (updateFica && updateBank) {
                        contentTitle = "Upload/Update FICA & Bank details \n";
                    } else if (updateFica) {
                        contentTitle = "Upload/Update FICA documents \n";
                    } else if (updateBank) {
                        contentTitle = "Upload/Update Bank details \n";
                    }
                    reportProblemRequest.setContent(contentTitle + mEtContent.getText().toString());
                    reportProblemRequest.setUpdateFICA(updateFica ? "1" : "0");
                    reportProblemRequest.setUploadBank(updateBank ? "1" : "0");
                    iReportProblemPresenter.onClickSubmit(reportProblemRequest);
                } else {
                    showMessage(R.string.txt_report_problem_hint);
                }
                break;
            case R.id.tv_view_reports:
                iReportProblemPresenter.navigateToViewReports();
                break;
        }
    }

    @Override
    public void navigateToViewReports(Bundle bundle) {
        Intent intent = new Intent(this, ViewReportActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void canViewUpdates(int canShow) {
        llUpdateReportContainer.setVisibility(canShow);
    }

    @Override
    public void clearText() {
        mEtContent.getText().clear();
    }

    @Override
    public void canViewReport(int canSee) {
        findViewById(R.id.tv_view_reports).setVisibility(canSee);
    }
}
