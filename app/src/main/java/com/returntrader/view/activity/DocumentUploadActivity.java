package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.utils.DocInfoBottomsheetFrag;

public class DocumentUploadActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolBar;
    private Button vBtnSubmit;
    private TextView vTxtSkip, vTxtinfoAddress, vTxtinfoBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        toolBar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        vBtnSubmit =  findViewById(R.id.vBtnSubmit);
        vBtnSubmit.setOnClickListener(this);
        vTxtSkip =  findViewById(R.id.vTxtSkip);
        vTxtSkip.setOnClickListener(this);
        vTxtinfoAddress =  findViewById(R.id.vTxtinfoAddress);
        vTxtinfoBank =  findViewById(R.id.vTxtinfoBank);
        vTxtinfoAddress.setOnClickListener(this);
        vTxtinfoBank.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(DocumentUploadActivity.this);
                onBackPressed();
                break;
        }

        return true;
    }


    protected int getLayoutId() {
        return R.layout.activity_document_upload;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.vBtnSubmit:
                Intent mIntent = new Intent(DocumentUploadActivity.this, HomeActivity.class);
                startActivity(mIntent);
                finish();
                break;

            case R.id.vTxtSkip:
                Intent mIntentNew = new Intent(DocumentUploadActivity.this, HomeActivity.class);
                startActivity(mIntentNew);
                finish();

                break;

            case R.id.vTxtinfoBank:
                infoBottomsheet();
                break;
            case R.id.vTxtinfoAddress:
                infoBottomsheet();
                break;
        }


    }


    public void infoBottomsheet() {

        DocInfoBottomsheetFrag mBottomsheetFrag = new DocInfoBottomsheetFrag();
        mBottomsheetFrag.show(getSupportFragmentManager(), mBottomsheetFrag.getTag());


    }
}
